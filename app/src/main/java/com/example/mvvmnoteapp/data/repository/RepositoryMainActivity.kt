package com.example.mvvmnoteapp.data.repository

import com.example.mvvmnoteapp.data.db.NoteDao
import com.example.mvvmnoteapp.data.model.NoteEntity
import javax.inject.Inject

class RepositoryMainActivity @Inject constructor(private val dao: NoteDao) {

    fun getAllNote() = dao.getAllNote()
    fun searchNote(str: String) = dao.searchNote(str)
    fun filterNote(str:String) = dao.filterNote(str)
    suspend fun deleteNote(noteEntity: NoteEntity) = dao.delete(noteEntity)

}