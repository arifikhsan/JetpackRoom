package com.arifikhsan.jetpackroom.database

import androidx.paging.DataSource
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.sqlite.db.SupportSQLiteQuery
import com.arifikhsan.jetpackroom.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun getAllNotes(): DataSource.Factory<Int, Note>

    @Insert(onConflict = IGNORE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Insert(onConflict = IGNORE)
    fun insertAll(list: List<Note>)

    @RawQuery(observedEntities = [Note::class])
    fun getAllNotes(query: SupportSQLiteQuery): DataSource.Factory<Int, Note>
}