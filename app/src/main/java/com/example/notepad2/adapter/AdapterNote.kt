package com.example.notepad2.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notepad2.R
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

        holder.binding.tittle.text = current.title
        holder.binding.textTv.smartTruncate(current.text, 20)
        holder.binding.hourCount.text = current.count

        Glide.with(context)
            .load(current.image)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_email_24)
            .into(holder.binding.noteImage);

        // holder.binding.noteImage.setImageURI(Uri.parse(current.image))

    }

    override fun getItemCount() = noteData.size

    @SuppressLint("NotifyDataSetChanged")
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


    /*  private fun TextView.smartTruncate(text: String, maxLength: Int) {
        val truncatedText = if (text.length > maxLength) {
            "${text.substring(0, maxLength)}..."
        } else {
            text
        }
        this.text = truncatedText
    }*/

}