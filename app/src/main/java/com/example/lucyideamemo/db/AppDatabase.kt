package com.example.lucyideamemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lucyideamemo.bean.*
import com.example.lucyideamemo.db.dao.NoteDao
import com.example.lucyideamemo.db.dao.TagNoteDao

@Database(
    entities = [
        Note::class,
        Tag::class,
        NoteTagCrossRef::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    abstract fun getTagNoteDao(): TagNoteDao
}