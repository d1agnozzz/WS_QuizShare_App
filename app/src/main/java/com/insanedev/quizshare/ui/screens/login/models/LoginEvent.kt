package com.insanedev.quizshare.ui.screens.login.models

sealed class LoginEvent {
    object  ActionClicked : LoginEvent()
    object ForgetClicked : LoginEvent()
    object LoginClicked : LoginEvent()
    object RegistrationClicked : LoginEvent()
    data class FirstNameChanged(val value: String) : LoginEvent()
    data class SecondNameChanged(val value: String) : LoginEvent()
    data class PatronymicNameChanged(val value: String) : LoginEvent()
    data class EmailChanged(val value: String) : LoginEvent()
    data class PasswordChanged(val value: String) : LoginEvent()
    data class PasswordRepeatChanged(val value: String) : LoginEvent()
}