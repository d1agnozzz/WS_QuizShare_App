package com.insanedev.quizshare.common

sealed class AuthResult {
    data class Ok(val email:String) : AuthResult()
    object Err : AuthResult()
}