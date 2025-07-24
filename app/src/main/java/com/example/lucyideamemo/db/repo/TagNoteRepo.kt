package com.example.lucyideamemo.db.repo

import androidx.room.Transaction
import com.example.lucyideamemo.bean.Note
import com.example.lucyideamemo.bean.NoteShowBean
import com.example.lucyideamemo.db.dao.NoteDao
import com.example.lucyideamemo.db.dao.TagNoteDao
import com.example.lucyideamemo.ui.page.SortTime
import kotlinx.coroutines.flow.Flow

class TagNoteRepo(
    private val noteDao: NoteDao,
    private val tagNoteDao: TagNoteDao
) {

    fun queryAllNotes(): List<Note> {
        return noteDao.queryAllData()
    }

    fun queryAllMemosFLow(sortTime: String): Flow<List<NoteShowBean>> {
        return when (sortTime) {
            SortTime.CREATE_TIME_DESC.name -> {
                tagNoteDao.getAll("create_time", "desc")
            }

            SortTime.CREATE_TIME_ASC.name -> {
                tagNoteDao.getAll("create_time", "asc")
            }

            SortTime.UPDATE_TIME_DESC.name -> {
                tagNoteDao.getAll("update_time", "desc")
            }

            SortTime.UPDATE_TIME_ASC.name -> {
                tagNoteDao.getAll("update", "asc")
            }

            else -> {
                tagNoteDao.getAll("update_time", "desc")
            }
        }
    }

    @Transaction
    fun insertOrUpdate(note: Note) {
        noteDao.insert(note)
    }
}
