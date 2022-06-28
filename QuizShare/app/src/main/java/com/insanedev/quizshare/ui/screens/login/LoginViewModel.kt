package com.insanedev.quizshare.ui.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.insanedev.quizshare.common.EventHandler
import com.insanedev.quizshare.ui.screens.login.models.LoginEvent
import com.insanedev.quizshare.ui.screens.login.models.LoginSubState
import com.insanedev.quizshare.ui.screens.login.models.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(), EventHandler<LoginEvent> {
    private val _viewState = MutableLiveData(LoginViewState())
    val viewState: LiveData<LoginViewState> = _viewState

    override fun obtainEvent(event: LoginEvent) {
        when(event) {
            LoginEvent.ActionClicked -> switchActionState()
            is LoginEvent.EmailChanged -> emailChanged(event.value)
            is LoginEvent.PasswordChanged -> passwordChanged(event.value)
            is LoginEvent.ForgetClicked -> forgetClicked()
            is LoginEvent.LoginClicked -> loginClicked()
            is LoginEvent.RegisterClicked -> registerClicked()
        }
    }

    private fun registerClicked() {}

    private fun loginClicked() {}

    private fun forgetClicked() {}

    private fun switchActionState() {

        when(_viewState.value?.loginSubState) {
            LoginSubState.SignIn -> Unit
            LoginSubState.SignUp -> Unit
        }
    }

    private fun emailChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(emailValue = value))
    }

    private fun passwordChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(passwordValue = value))
    }
}