package com.example.notepad2.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad2.adapter.AdapterNote.NoteViewHolder
import com.example.notepad2.databinding.TestModelBinding
import com.example.notepad2.model.datamodel.Note

class AdapterNote (private val context: Context ): RecyclerView.Adapter<NoteViewHolder>(){

    private var noteData = emptyList<Note>()

    //create view holder using inner class
    inner class NoteViewHolder(val binding: TestModelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        //populate the view layout
        val layout = TestModelBinding.inflate(LayoutInflater.from(context), parent, false)

        return NoteViewHolder(layout)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = noteData[position]
       // Log.d("DATA", current.image)

        //holder.binding.noteImage.setImageURI(Uri.parse(current.image))
        holder.binding.tittle.text = current.title
        holder.binding.textTv.smartTruncate(current.text, 10)
        holder.binding.hourCount.text =  current.count.toString()

    }

    /*tells recycler how many times the item has been input*/
    override fun getItemCount() = noteData.size

   /* override fun getItemCount(): Int {

        return  noteData.size

    }*/

    /*internal fun sets data. notifyDataSetChanged()
    tells onBindViewHolder that theres a change in the list*/
    internal fun setData(note: List<Note>){

        this.noteData = note
        notifyDataSetChanged()
    }

    //Extension fxn to trim text length
    private fun TextView.smartTruncate(text: String, length: Int){

        var out = ""
        val textLength = 10

        if (text.length > textLength){

            for(i in 0..textLength){
                out+= text[i]
            }
            out+= "..."
            this.text =  out

        } else {

            this.text = text
        }
    }
}