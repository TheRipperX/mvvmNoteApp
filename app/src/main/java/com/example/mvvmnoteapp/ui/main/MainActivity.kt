package com.example.mvvmnoteapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvmnoteapp.R
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.databinding.ActivityMainBinding
import com.example.mvvmnoteapp.ui.main.note.NoteFragment
import com.example.mvvmnoteapp.utils.ALL
import com.example.mvvmnoteapp.utils.BUNDLE
import com.example.mvvmnoteapp.utils.DELETE
import com.example.mvvmnoteapp.utils.EDIT
import com.example.mvvmnoteapp.utils.HIGH
import com.example.mvvmnoteapp.utils.LOW
import com.example.mvvmnoteapp.utils.NORMAL
import com.example.mvvmnoteapp.viewmodel.ViewModelMain
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var itemAlertSelect = 0

    @Inject
    lateinit var noteEntity: NoteEntity

    @Inject
    lateinit var mainAdapter: MainAdapter

    private val viewModelMain: ViewModelMain by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        main()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_toolbar_filter -> {
                openAlertDialogFilter()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openAlertDialogFilter() {

        val listAlert = arrayOf(ALL, HIGH, NORMAL, LOW)

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Filter Note...")
        alertDialog.setSingleChoiceItems(listAlert, itemAlertSelect){dialog,which ->
            itemAlertSelect = which

            when(itemAlertSelect) {
                0 -> {
                    viewModelMain.getAllNotes()
                }
                in 1..listAlert.count() -> {
                    viewModelMain.filterNote(listAlert[itemAlertSelect])
                }
            }
            dialog.dismiss()
        }
        alertDialog.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchMenu = menu.findItem(R.id.menu_toolbar_search)
        val searchView = searchMenu.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModelMain.searchNote(it)
                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun main() {

        binding.apply {

            setSupportActionBar(toolbarMain)

            //click FloatingActionButton
            floatingBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
                itemAlertSelect = 0
            }

            //show all items main
            viewModelMain.getAllNotes()
            //set adapter main
            viewModelMain.notes.observe(this@MainActivity) {
                isEmptyData(it.empty)
                mainAdapter.setData(it.data!!.toMutableList())
                listMain.apply {
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    adapter = mainAdapter
                }
            }

            viewModelMain.noteEdit.observe(this@MainActivity) {
                val bundle = Bundle()
                bundle.putInt(BUNDLE, it)
                val fragmentNote = NoteFragment()
                fragmentNote.arguments = bundle
                fragmentNote.show(supportFragmentManager, fragmentNote.tag)
            }

            //click adapter delete or edit
            mainAdapter.getClick {note, s ->
                when(s) {
                    EDIT -> {
                        viewModelMain.findNoteId(note.id)
                    }
                    DELETE -> {
                        viewModelMain.deleteNote(note)
                    }
                }
            }
        }
    }

    private fun isEmptyData(boolean: Boolean) {
        binding.apply {
            if (boolean) {
                emptyLayout.visibility = View.VISIBLE
                listMain.visibility = View.GONE
                floatingBtn.visibility = View.GONE
            }else {
                emptyLayout.visibility = View.GONE
                listMain.visibility = View.VISIBLE
                floatingBtn.visibility = View.VISIBLE
            }
        }
    }
}