package com.example.masterclass.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.masterclass.databinding.ActivityLoginBinding
import com.example.masterclass.utils.toast

class LoginActivity : AppCompatActivity(), AuthListener {

    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.viewModel = viewModel

        viewModel.authListener = this
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStarted() {
        toast("Login Started")
    }

    override fun onSuccess() {
        toast("Login Success")
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}