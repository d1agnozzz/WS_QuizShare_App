package com.insanedev.quizshare.ui.screens.login.models

enum class LoginSubState {
    SignIn, SignUp
}

data class LoginViewState(
    val loginSubState: LoginSubState = LoginSubState.SignIn,

    val firstNameValue: String = "",
    val firstNameHelperText: String = "",
    val secondNameValue: String = "",
    val secondNameHelperText: String = "",
    val patronymicNameValue: String = "",

    val emailValue: String = "",
    val emailHelperText: String = "",

    val passwordValue: String = "",
    val passwordHelperText: String = "",
    val passwordRepeatValue: String = "",
    val passwordRepeatHelperText: String = "",

    val isPerforming: Boolean = false,
    val isLoginPassed: Boolean = false,
)
