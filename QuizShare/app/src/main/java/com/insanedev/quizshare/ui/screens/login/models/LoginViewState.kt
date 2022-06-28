package com.insanedev.quizshare.ui.screens.login.models

enum class LoginSubState {
    SignIn, SignUp
}

data class LoginViewState (
    val loginSubState: LoginSubState = LoginSubState.SignIn,
    val emailValue: String = "",
    val passwordValue: String = "",
    val fullNameValue: String = "",
)
