package com.example.mvvmnoteapp.utils.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmnoteapp.data.db.NoteDataBase
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.utils.DataBase_Name
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleDatabase {

    @Provides
    @Singleton
    fun proDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, NoteDataBase::class.java, DataBase_Name
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun proDao(dataBase: NoteDataBase) = dataBase.noteDao()

    @Provides
    @Singleton
    fun proNoteEntity() = NoteEntity()
}