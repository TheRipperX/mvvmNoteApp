package com.example.mvvmnoteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.data.repository.RepositoryNoteFragments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFragment @Inject constructor(private val repositoryNoteFragments: RepositoryNoteFragments): ViewModel() {




    fun saveNote(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        repositoryNoteFragments.saveNote(noteEntity)
    }
}