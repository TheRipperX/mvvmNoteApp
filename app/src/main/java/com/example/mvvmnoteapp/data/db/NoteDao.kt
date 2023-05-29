package com.example.mvvmnoteapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mvvmnoteapp.data.model.NoteEntity
import com.example.mvvmnoteapp.utils.NOTE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity)

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY id DESC")
    fun getAllNote(): Flow<MutableList<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE id = :id")
    fun findNoteId(id: Int): Flow<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE title LIKE '%' || :str || '%' ")
    fun searchNote(str: String): Flow<MutableList<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE priority = :priority ")
    fun filterNote(priority: String): Flow<MutableList<NoteEntity>>
}