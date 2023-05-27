package com.example.mvvmnoteapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.utils.DataBase_Version


@Database(entities = [NoteEntity::class], version = DataBase_Version, exportSchema = false)
abstract class NoteDataBase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}