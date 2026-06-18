package com.example.android30

object Validator {

    fun isEmailValid(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}