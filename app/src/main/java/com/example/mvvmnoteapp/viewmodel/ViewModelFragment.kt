package com.example.mvvmnoteapp.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.data.repository.RepositoryNoteFragments
import com.example.mvvmnoteapp.utils.HEALTHY
import com.example.mvvmnoteapp.utils.HIGH
import com.example.mvvmnoteapp.utils.HOME
import com.example.mvvmnoteapp.utils.LEARNING
import com.example.mvvmnoteapp.utils.LOW
import com.example.mvvmnoteapp.utils.NORMAL
import com.example.mvvmnoteapp.utils.WORK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFragment @Inject constructor(private val repositoryNoteFragments: RepositoryNoteFragments): ViewModel() {


    val priorityList = MutableLiveData<MutableList<String>>()
    val categoryList = MutableLiveData<MutableList<String>>()

    var noteIds = MutableLiveData<NoteEntity>()

    fun setPriority() = viewModelScope.launch(Dispatchers.IO) {
        val listPriority = mutableListOf(HIGH, NORMAL, LOW)
        priorityList.postValue(listPriority)
    }

    fun setCategory() = viewModelScope.launch(Dispatchers.IO) {
        val listCategory = mutableListOf(HEALTHY, HOME, WORK, LEARNING)
        categoryList.postValue(listCategory)
    }

    fun findIdNotes(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repositoryNoteFragments.findIdNote(id).collect {
            noteIds.postValue(it)
        }
    }

    fun saveOrEditNote(isSave: Boolean, noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        if (isSave) {
            repositoryNoteFragments.saveNote(noteEntity)
        }else {
            repositoryNoteFragments.editNote(noteEntity)
        }
    }
}