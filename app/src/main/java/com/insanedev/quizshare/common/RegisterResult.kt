package com.insanedev.quizshare.common

sealed class RegisterResult {
    data class Ok(val token: String) : RegisterResult()
    object EmailAlreadyExists : RegisterResult()
    object EmailIsNotValid : RegisterResult()
    object SomethingWentWrong : RegisterResult()
}
