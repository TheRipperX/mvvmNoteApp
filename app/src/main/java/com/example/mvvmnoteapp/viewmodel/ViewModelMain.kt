package com.example.mvvmnoteapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.data.repository.RepositoryMainActivity
import com.example.mvvmnoteapp.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(private val rep: RepositoryMainActivity): ViewModel(){

    var notes = MutableLiveData<DataStatus<MutableList<NoteEntity>>>()
    var noteEdit = MutableLiveData<Int>()
    fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        rep.getAllNote().collect {
            notes.postValue(DataStatus.success(it, it.isEmpty()))
        }
    }


    fun searchNote(str: String) = viewModelScope.launch(Dispatchers.IO) {
        rep.searchNote(str).collect {
            notes.postValue(DataStatus.success(it,it.isEmpty()))
        }
    }

    fun filterNote(str: String) = viewModelScope.launch(Dispatchers.IO) {
        rep.filterNote(str).collect {
            notes.postValue(DataStatus.success(it, it.isEmpty()))
        }
    }
    fun findNoteId(id: Int)  = viewModelScope.launch(Dispatchers.IO) {
        noteEdit.postValue(id)
    }

    fun deleteNote(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        rep.deleteNote(noteEntity)
    }



}