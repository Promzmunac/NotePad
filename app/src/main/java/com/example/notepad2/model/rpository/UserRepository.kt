package com.example.notepad2.model.rpository

import com.example.notepad2.model.datamodel.Note
import com.example.notepad2.model.datamodel.User
import com.example.notepad2.model.db.NoteDao
import com.example.notepad2.model.db.UserDao


class UserRepository(private val userDao: UserDao, private var noteDao: NoteDao){

    var getAllUsers = userDao.getAll()
    var getAllNote  = noteDao.getAllNote()

    //method is called in the repository
    fun insertAll(user: User): Long{
        return userDao.insertAll(user)
    }
    fun delete(user: User) {
        userDao.deleteUser(user)
    }

    fun updateUserAll(id: Long, UserName: String, Email: String, Image: String, Password: String){
        userDao.updateUserAll(id,UserName, Email, Image, Password)
    }

    //repo method for note
    suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note)

    }

   fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
   }

    fun updateNoteWrite(noteId: Long, title: String, text: String, image: String, count: Int) {
        return noteDao.updateNoteWrite(noteId,title, text, image, count)
    }

}


