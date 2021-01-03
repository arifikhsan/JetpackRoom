package com.arifikhsan.jetpackroom.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.arifikhsan.jetpackroom.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = IGNORE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
}