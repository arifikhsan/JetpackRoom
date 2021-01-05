package com.arifikhsan.jetpackroom.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.arifikhsan.jetpackroom.R
import com.arifikhsan.jetpackroom.databinding.ActivityMainBinding
import com.arifikhsan.jetpackroom.entity.Note
import com.arifikhsan.jetpackroom.helper.SortUtils
import com.arifikhsan.jetpackroom.helper.ViewModelFactory
import com.arifikhsan.jetpackroom.ui.insert.NoteAddUpdateActivity
import com.arifikhsan.jetpackroom.ui.insert.NoteAddUpdateActivity.Companion.REQUEST_ADD
import com.arifikhsan.jetpackroom.ui.insert.NoteAddUpdateActivity.Companion.REQUEST_UPDATE
import com.arifikhsan.jetpackroom.ui.insert.NoteAddUpdateActivity.Companion.RESULT_ADD
import com.arifikhsan.jetpackroom.ui.insert.NoteAddUpdateActivity.Companion.RESULT_DELETE
import com.arifikhsan.jetpackroom.ui.insert.NoteAddUpdateActivity.Companion.RESULT_UPDATE
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: NotePagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getAllNotes(SortUtils.NEWEST).observe(this, noteObserver)

        adapter = NotePagedListAdapter(this@MainActivity)

        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter

        binding?.fabAdd?.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (requestCode == REQUEST_ADD) {
                if (resultCode == RESULT_ADD) {
                    showSnackbarMessage(getString(R.string.added))
                }
            } else if (requestCode == REQUEST_UPDATE) {
                if (resultCode == RESULT_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed))
                } else if (resultCode == RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted))
                }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding?.root as View, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    private val noteObserver = Observer<PagedList<Note>> { noteList ->
        if (noteList != null) {
            adapter.submitList(noteList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_newest -> sort = SortUtils.NEWEST
            R.id.action_oldest -> sort = SortUtils.OLDEST
            R.id.action_random -> sort = SortUtils.RANDOM
        }

        mainViewModel.getAllNotes(sort).observe(this, noteObserver)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }
}