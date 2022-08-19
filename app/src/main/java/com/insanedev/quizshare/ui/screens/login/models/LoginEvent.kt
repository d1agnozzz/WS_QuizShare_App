package com.insanedev.quizshare.ui.screens.login.models

sealed class LoginEvent {
    object ActionClicked : LoginEvent()
    object ForgetClicked : LoginEvent()
    object LoginClicked : LoginEvent()
    object RegisterClicked : LoginEvent()
    data class EmailChanged(val value: String) : LoginEvent()
    data class PasswordChanged(val value: String) : LoginEvent()
}