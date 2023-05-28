package com.example.mvvmnoteapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.activity.viewModels
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

    private fun main() {

        binding.apply {

            //click FloatingActionButton
            floatingBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
            }

            //show all items main
            viewModelMain.getAllNotes()
            //set adapter main
            viewModelMain.notes.observe(this@MainActivity) {
                //Log.d("TAG", "getAllNotes: $it")
                mainAdapter.differ.submitList(it)
                listMain.apply {
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    adapter = mainAdapter
                }
            }
        }
    }
}