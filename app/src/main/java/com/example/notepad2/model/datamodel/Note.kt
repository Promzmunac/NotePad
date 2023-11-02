package com.example.notepad2.model.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    var title: String,
    var text: String,
    //var image: String,
    val count: String
){
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0

    companion object{
        fun add(notes: List<Note>): Int {
            val sum = ArrayList<Int>()
            for (i in notes) {
                sum.add(i.count.toInt())
            }
            return sum.sum()
        }
    }

}
