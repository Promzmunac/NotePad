package com.example.notepad2.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notepad2.viewmodel.UserViewModel
import com.example.notepad2.R

class MainActivityReg : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_reg)
        Log.d("CREATE", "create")

        supportActionBar?.hide()


        // Initialize the ViewModel using ViewModelProvider
       userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

       // Observe the LiveData for registered users
        userViewModel.allUsers.observe(this){ users ->
            Log.d("KEY", "${users?.size}")
            // Use safe call operator for potential null LiveData

            if (users?.isNotEmpty() == true) {
                // Check if users is not null and not empty & Start the MainActivity if there are users
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}