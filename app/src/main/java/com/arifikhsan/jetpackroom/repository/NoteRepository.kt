package com.arifikhsan.jetpackroom.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.arifikhsan.jetpackroom.database.NoteDao
import com.arifikhsan.jetpackroom.database.NoteRoomDatabase
import com.arifikhsan.jetpackroom.entity.Note
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private lateinit var mNotesDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAllNotes()

    fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }

    fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }

    fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }
}