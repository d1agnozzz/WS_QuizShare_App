package com.insanedev.quizshare.common

val EMAIL_PATTERN = android.util.Patterns.EMAIL_ADDRESS

fun validateEmail(email: String): Boolean  {
    return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}