package com.example.lucyideamemo.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.lucyideamemo.bean.Note
import com.example.lucyideamemo.bean.NoteShowBean
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Note>)

    @Delete
    fun detele(note: Note)

    @Update
    fun update(note: Note)

    @Transaction
    @Query("SELECT * FROM Note order by update_time desc")
    fun queryAll(): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM Note order by update_time desc")
    fun queryAllData(): List<Note>

    @Query("SELECT * FROM Note where note_id = :id")
    fun queryById(id: Int): Note

    @Query("SELECT count(*) from Note")
    fun getCount(): Int

    @Query("DELETE from Note")
    fun deleteAll()

    @Transaction
    @Query("SELECT DISTINCT location_info FROM Note WHERE location_info IS NOT NULL AND location_info != ''")
    fun getAllLocationInfo(): Flow<List<String>>

    @Transaction
    @Query("SELECT * FROM Note WHERE location_info = :targetInfo")
    fun getNotesByLocationInfo(targetInfo: String): Flow<List<NoteShowBean>>

    @Transaction
    @Query("SELECT * FROM Note WHERE strftime('%Y-%M', create_time/1000) = :yearMonth")
    fun queryAllsByYearMonth(yearMonth: String): Flow<List<Note>>

    @Query("Update Note SET location_info = NULL WHERE location_info = :locationInfo")
    fun clearLocationInfo(locationInfo: String)
}