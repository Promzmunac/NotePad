package com.example.notepad2.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.notepad2.viewmodel.UserViewModel
import com.example.notepad2.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var  binding: FragmentLoginBinding
    private var data : String? = null
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        //initializing the viewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.allUsers.observe(viewLifecycleOwner, Observer { users ->
            Log.d("KEY", "edit")

            for (UserModel in users){
                Log.d("USER", "${UserModel.id}")
            }
        })
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val passWord = arguments?.getString("PASSWORD")
        binding.editTextTextPersonName3.text = Editable.Factory().newEditable(passWord) //Display the password on this fragment
        Log.d("PASS", "password")

        val userName = arguments?.getString("USERNAME")
        binding.usernameEm.text = Editable.Factory().newEditable(userName)   //Display the userName on this fragment
        Log.d("USER", "username")

        //passing image to this fragment
        data = arguments?.getString("IMAGE")
        Log.d("DATA", "$data")

        if (data != null) {
            Log.d("DEBUG", "$data")
            binding.imageView2.setImageURI(Uri.parse(data))

        }else{

            Toast.makeText(requireContext(), "select a profile image please!!", Toast.LENGTH_LONG).show()
        }

        binding.buttonReg.setOnClickListener{

            if (userName != null && passWord != null){
                Log.d("DEBUG", "$passWord")
                Log.d("DEBUG", "$userName")

                if (userName != binding.usernameEm.text.toString() ||
                    passWord != binding.editTextTextPersonName3.text.toString()   ){
                    Toast.makeText(requireContext(), " wrong User name or password", Toast.LENGTH_LONG).show()
                }
            } else {

                val intent =Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }

        binding.textView3.setOnClickListener {

            Toast.makeText(requireContext(), "This Functionality Has not been Implemented", Toast.LENGTH_LONG).show()
          }
    }
}