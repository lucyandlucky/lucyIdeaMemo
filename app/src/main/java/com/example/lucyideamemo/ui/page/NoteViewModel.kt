package com.example.lucyideamemo.ui.page

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lucyideamemo.bean.Note
import com.example.lucyideamemo.bean.NoteShowBean
import com.example.lucyideamemo.db.repo.TagNoteRepo
import com.example.lucyideamemo.state.NoteState
import com.example.lucyideamemo.ui.page.settings.Level
import com.example.lucyideamemo.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

enum class SortTime {
    UPDATE_TIME_DESC, UPDATE_TIME_ASC, CREATE_TIME_DESC, CREATE_TIME_ASC
}

@HiltViewModel
class NoteViewModel @Inject constructor(private val tagNoteRepo: TagNoteRepo) : ViewModel() {

    val sortTime = SharedPreferencesUtils.sortTime

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _notes: StateFlow<List<NoteShowBean>> = sortTime.flatMapLatest { newSortTime ->
        tagNoteRepo.queryAllMemosFLow(newSortTime.name)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private val _state = MutableStateFlow(NoteState())

    val state = combine(_state, _notes) { state, notes ->
        val sortedNote = notes.sortedWith(compareByDescending { it.note.isCollected })
        val filteredNotes = if (state.searchQuery.isBlank()) {
            sortedNote
        } else {
            sortedNote.filter { it.doesMatchSearchQuery(state.searchQuery) }
        }
        getLocalDateMap(notes)
        state.copy(
            notes = filteredNotes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    var levelMemosMap = mutableStateMapOf<LocalDate, Level>()
        private set

    private suspend fun getLocalDateMap(notes: List<NoteShowBean>) = withContext(Dispatchers.IO) {
        val sortTime = SharedPreferencesUtils.sortTime.first()
        val map: MutableMap<LocalDate, Int> = mutableMapOf()
        notes.forEach {
            val showTime =
                if (sortTime == SortTime.CREATE_TIME_DESC || sortTime == SortTime.UPDATE_TIME_ASC) it.note.updateTime else it.note.createTime
            val localDate =
                Instant.ofEpochMilli(showTime).atZone(ZoneId.systemDefault()).toLocalDate()
            map[localDate] = map.getOrElse(localDate) { 0 } + 1
        }
        levelMemosMap.clear()
        levelMemosMap.putAll(convertToLevelMap(map))
    }

    private fun convertToLevelMap(inputMap: Map<LocalDate, Int>): Map<LocalDate, Level> {
        return inputMap.mapValues { (_, value) ->
            when (value) {
                in 0 until 1 -> Level.Zero
                in 1 until 3 -> Level.One
                in 3 until 5 -> Level.Two
                in 5 until 8 -> Level.Three
                else -> Level.Four
            }
        }
    }
}


var LocalNoteViewModel = compositionLocalOf<NoteViewModel> { error("Not Found") }

val LocalMemosState = compositionLocalOf<NoteState> { error("Not Found") }
