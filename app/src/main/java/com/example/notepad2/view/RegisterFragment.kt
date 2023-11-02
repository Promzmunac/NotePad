package com.example.notepad2.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notepad2.R
import com.example.notepad2.databinding.FragmentRegister2Binding
import com.example.notepad2.model.datamodel.User
import com.example.notepad2.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegister2Binding
    private lateinit var  userViewModel: UserViewModel
    private var id: Long? = null
    private var photoUri = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentRegister2Binding.inflate(inflater, container, false)

        // Instantiating the view model
        userViewModel = ViewModelProvider(this, ViewModelProvider
            .AndroidViewModelFactory.getInstance(application = Application()))[UserViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonreg.setOnClickListener{
            login()
        }

        binding.imageView.setOnClickListener {
            toGallery()
        }

        binding.floatingActionButton.setOnClickListener{
            toGallery()
        }

        binding.textViewLog.setOnClickListener{
            startActivity(Intent(requireContext(), MainActivity::class.java))
            findNavController().navigate(R.id.action_registerBlankFragment_to_loginFragment)
        }
    }

    private fun login(){

        val userName = binding.usernameEm.text.toString()
        val password = binding.passwordEm.text.toString()
        var email =    binding.emailEm.text.toString()
        val photo = photoUri

        if (binding.passwordEm.text.toString() == "" ||
            binding.passwordEm2.text.toString() == "" ||
            binding.emailEm.text.toString() == "" ||
            binding.usernameEm.text.toString() == ""||
            photo == "" ){

            Toast.makeText(requireContext(), "check for Details", Toast.LENGTH_LONG).show()

        } else if (binding.passwordEm.text.toString() == binding.passwordEm2.text.toString() &&
            binding.emailEm.text.toString() != "" &&
            binding.usernameEm.text.toString() != ""
        ){

            insertToDatabase()

            findNavController().navigate(R.id.action_registerBlankFragment_to_loginFragment, Bundle().apply {
                putString("IMAGE", photoUri)
                putString("PASSWORD", password)
                putString ("USERNAME", userName)
            })

        } else if (binding.passwordEm.text.toString() != binding.passwordEm2.text.toString()){

            Toast.makeText(requireContext(), "password Incompatible", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("IntentReset")
    private fun toGallery(){

        val gallery =  Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        gallery.type = "image/*"
        startActivityForResult(gallery,  REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK ){
            binding.imageView.setImageURI(data?.data)
            photoUri = data?.data.toString()
            Log.d("MESSAGE", photoUri)
        }
    }

    private fun insertToDatabase(){

        //object from the userModel table
        val userName = binding.usernameEm.text.toString()
        val password = binding.passwordEm.text.toString()
        val email = binding.emailEm.text.toString()
        val photo = photoUri

        if (inputCheck(userName, email, photo, password)){

            //create user object
            val user = User(userName,email,photo,password)

            //Add the Data to dataBase
            CoroutineScope(Dispatchers.IO).launch {
                id = userViewModel.insertAll(user)
                getId(id!!)
            }.invokeOnCompletion {
                getId(id!!)
            }

            Toast.makeText(requireContext(),"you have successfully  Registered ", Toast.LENGTH_LONG).show()

        } else {

            Toast.makeText(requireContext(),"fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(userName:String,email:String, photo:String, password:String): Boolean{

        return !(TextUtils.isEmpty(userName) && TextUtils.isEmpty(email) &&
                TextUtils.isEmpty(password) && TextUtils.isEmpty(photo))
    }

    companion object {
        const val REQUEST_CODE = 100
    }

    private fun getId(id: Long){
        this.id = id
        Log.d("KEY", "${this.id}")
    }
}
