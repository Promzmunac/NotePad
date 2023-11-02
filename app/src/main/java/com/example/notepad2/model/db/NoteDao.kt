package com.example.notepad2.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notepad2.model.datamodel.Note

@Dao
interface NoteDao {
    //DAO for Notes taken
    @Query("SELECT * FROM note ORDER BY noteId DESC")
    fun getAllNote(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE noteId=:id")
    fun getNote(id: Long): Note

    @Delete
    fun deleteNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note): Long

    @Query("UPDATE note SET  title =:Title, text=:Text, count=:Count WHERE noteId=:noteId")
    fun updateNoteWrite(noteId: Long, Title: String, Text: String, Count: String)

}