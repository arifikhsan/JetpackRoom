package com.arifikhsan.jetpackroom.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arifikhsan.jetpackroom.entity.Note
import com.arifikhsan.jetpackroom.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<PagedList<Note>> {
        return LivePagedListBuilder(mNoteRepository.getAllNotes(), 20).build()
    }
}