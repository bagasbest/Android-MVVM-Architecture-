package com.example.masterclass.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.masterclass.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private var _binding : ActivitySignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}