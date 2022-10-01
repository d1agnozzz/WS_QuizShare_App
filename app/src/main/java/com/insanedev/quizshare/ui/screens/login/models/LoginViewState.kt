package com.insanedev.quizshare.ui.screens.login.models

enum class LoginSubState {
    SignIn, SignUp
}

sealed class LoginAction{
    object None : LoginAction()
    data class OpenMainScreen(val email: String) : LoginAction()
}

data class LoginViewState(
    val loginSubState: LoginSubState = LoginSubState.SignIn,
    val loginAction: LoginAction = LoginAction.None,

    val firstNameValue: String = "",
    val firstNameHelperTextId: Int? = null,
    val secondNameValue: String = "",
    val secondNameHelperTextId: Int? = null,
    val patronymicNameValue: String = "",

    val emailValue: String = "",
    val emailHelperTextId: Int? = null,

    val passwordValue: String = "",
    val passwordHelperTextId: Int? = null,
    val passwordRepeatValue: String = "",
    val passwordRepeatHelperTextId: Int? = null,

    val isPerforming: Boolean = false,
    val isLoginPassed: Boolean = false,
)
