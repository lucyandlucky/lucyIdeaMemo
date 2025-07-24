package com.example.lucyideamemo.db.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.lucyideamemo.bean.Note
import com.example.lucyideamemo.bean.NoteShowBean
import com.example.lucyideamemo.bean.NoteTagCrossRef
import com.example.lucyideamemo.bean.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagNoteDao {

    @Transaction
    @RawQuery(
        observedEntities = [
            Note::class,
            Tag::class,
            NoteTagCrossRef::class
        ]
    )
    fun rawGetQueryFlow(query: SimpleSQLiteQuery): Flow<List<NoteShowBean>>

    fun getAll(sortTime: String, order: String): Flow<List<NoteShowBean>> {
        return rawGetQueryFlow(
            SimpleSQLiteQuery("SELECT * FROM Note order by $sortTime $order")
        )
    }
}