package com.example.masterclass.ui.auth

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message : String)
}