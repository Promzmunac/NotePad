package com.example.notepad2.model.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserModel")
data class User(
    var userName: String,
    var email: String,
    var photo: String,
    var password: String
    ){
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0
}