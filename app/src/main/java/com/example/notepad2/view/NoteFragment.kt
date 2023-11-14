package com.example.notepad2.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notepad2.R
import com.example.notepad2.databinding.FragmentNoteBinding
import com.example.notepad2.model.datamodel.Note
import com.example.notepad2.view.RegisterFragment.Companion.REQUEST_CODE
import com.example.notepad2.viewmodel.UserViewModel

class NoteFragment : Fragment() {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var  userViewModel: UserViewModel
    private var noteId: Long? = null
    private var title: String? = null
    private var text: String? = null
    private var noteType:  String? = null
    private var position : Int? = null
    private var count : Int? = null
    private var imgData =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //used arguments? to get the passed data to the new fragment.
        title = arguments?.getString("TITLE")
        text = arguments?.getString("TEXT")
        noteType = arguments?.getString("NOTETYPE")
        position = arguments?.getInt("POSITION")
        count = arguments?.getInt("COUNT")
        noteId = arguments?.getLong("ID")
        imgData = arguments?.getString("IMAGE").toString()

        if(position != null){
            setUpEditFields()
        }

        binding.submitKeyboard.setOnClickListener{
            submitData()
        }

        binding.fabImage.setOnClickListener {
            toGallery()
        }
    }

    private fun setUpEditFields(){
        binding.titleTv.text = Editable.Factory().newEditable(title)
        binding.textNote.text = Editable.Factory().newEditable(text)
        binding.hourCount.text = Editable.Factory().newEditable(count.toString())
    }


    @SuppressLint("IntentReset")
    private fun toGallery(){

        val gallery =  Intent( Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        gallery.type = "image/*"
        startActivityForResult(gallery,  REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK ){
            binding.noteImage.setImageURI(data?.data)
            binding.noteImage.visibility = View.VISIBLE
            imgData = data?.data.toString()
            Log.d("MESSAGE", imgData)
        }
    }

    private fun submitData() {


        val title = binding.titleTv.text.toString()
        val text = binding.textNote.text.toString()
        val hourCountText = binding.hourCount.text.toString()

        if (title.isEmpty()|| text.isEmpty() || hourCountText.isEmpty()){

            Toast.makeText(requireContext(), "Fields cant be Null..", Toast.LENGTH_LONG).show()
        }

        if (noteType.equals("Edit")) {
            if (title.isNotEmpty() && text.isNotEmpty() &&  hourCountText.isNotEmpty()) {

                val updatedNote = Note(title, text, imgData, hourCountText)
                updatedNote.noteId = noteId!!

                userViewModel.updateNoteWrite(
                    noteId!!, updatedNote.title, updatedNote.text,updatedNote.image, updatedNote.count.toInt())

                Toast.makeText(requireContext(), "Note Updated..", Toast.LENGTH_LONG).show()
            }
        } else {
            if (title.isNotEmpty() && text.isNotEmpty()  &&  hourCountText.isNotEmpty()) {

                userViewModel.insertNote(Note(title, text, imgData,hourCountText))
                Toast.makeText(requireContext(), "$title Added", Toast.LENGTH_LONG).show()
            }
        }

        findNavController().navigate(R.id.action_noteFragment_to_homeFragment)

    }

}