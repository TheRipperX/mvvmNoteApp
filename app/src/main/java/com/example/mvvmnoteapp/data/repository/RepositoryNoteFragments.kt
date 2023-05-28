package com.example.mvvmnoteapp.data.repository

import com.example.mvvmnoteapp.data.db.NoteDao
import com.example.mvvmnoteapp.data.model.NoteEntity
import javax.inject.Inject

class RepositoryNoteFragments @Inject constructor(private val dao: NoteDao) {

    suspend fun saveNote(noteEntity: NoteEntity) = dao.insert(noteEntity)

}