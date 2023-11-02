package com.example.notepad2.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notepad2.model.datamodel.Note
import com.example.notepad2.model.datamodel.User

@Database(entities = [User::class, Note::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract  fun noteDao(): NoteDao

   companion object{
      @Volatile
      private  var INSTANCE: UserDatabase? = null    // makes sure that only one thread at a time is writing to this dataBase Instance

      fun getDatabase(context: Context): UserDatabase {
          return INSTANCE ?: synchronized(this){
             val instance =   Room.databaseBuilder(
                 context.applicationContext,
                 UserDatabase::class.java,
                 "user_database"
             ).build()
             INSTANCE =instance
             instance
         }
      }
   }
}
