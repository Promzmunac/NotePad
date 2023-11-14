package com.example.notepad2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notepad2.model.datamodel.Note
import com.example.notepad2.model.datamodel.User
import com.example.notepad2.model.db.UserDatabase
import com.example.notepad2.model.rpository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val allUsers: LiveData<List<User>>
    val allNote:  LiveData<List<Note>>

    init {

        val db =  UserDatabase.getDatabase(application)
        val userDao = db.userDao()
        val noteDao = db.noteDao()

        repository = UserRepository(userDao,noteDao)
        allUsers = repository.getAllUsers
        allNote = repository.getAllNote
    }

    //calling and initialising the methods from the repository
    fun insertAll(user: User): Long{
        return repository.insertAll(user)
    }

    fun delete(user: User) {

       return repository.delete(user)
    }

    fun updateUserAll(id: Long, userName: String, email: String, photo: String, password: String)
        = viewModelScope.launch(Dispatchers.IO){
        return@launch repository.updateUserAll(id,userName, email, photo,password)
    }

    //ViewModel  functions for my NoteDb
     fun insertNote(note: Note)= viewModelScope.launch(Dispatchers.IO){
         repository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
         repository.deleteNote(note)
    }

    fun updateNoteWrite(noteId: Long, title: String, text: String,Image: String, Count: Int)
        = viewModelScope.launch(Dispatchers.IO){
        return@launch repository.updateNoteWrite(noteId,title,text,Image,Count)
    }

}
