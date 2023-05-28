package com.example.mvvmnoteapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.data.repository.RepositoryMainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(private val rep: RepositoryMainActivity): ViewModel(){

    var notes = MutableLiveData<MutableList<NoteEntity>>()


    fun getAllNotes() = viewModelScope.launch {
            rep.getAllNote().collect {
                notes.postValue(it)
            }
        }

}