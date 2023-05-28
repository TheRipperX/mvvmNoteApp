package com.example.mvvmnoteapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvmnoteapp.R
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.databinding.ActivityMainBinding
import com.example.mvvmnoteapp.ui.main.note.NoteFragment
import com.example.mvvmnoteapp.viewmodel.ViewModelMain
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchMenu = menu.findItem(R.id.menu_toolbar_search)
        val searchView = searchMenu.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

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
            }

            //show all items main
            viewModelMain.getAllNotes()
            //set adapter main
            viewModelMain.notes.observe(this@MainActivity) {
                //Log.d("TAG", "getAllNotes: $it")
                isEmptyData(it.empty)
                mainAdapter.differ.submitList(it.data)
                listMain.apply {
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    adapter = mainAdapter
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