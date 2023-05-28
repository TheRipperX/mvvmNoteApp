package com.example.mvvmnoteapp.data.repository

import com.example.mvvmnoteapp.data.db.NoteDao
import javax.inject.Inject

class RepositoryMainActivity @Inject constructor(private val dao: NoteDao) {

    fun getAllNote() = dao.getAllNote()
}