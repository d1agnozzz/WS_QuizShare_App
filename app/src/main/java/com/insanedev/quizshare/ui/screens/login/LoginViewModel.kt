package com.insanedev.quizshare.ui.screens.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.insanedev.quizshare.R
import com.insanedev.quizshare.common.EventHandler
import com.insanedev.quizshare.common.validateEmail
import com.insanedev.quizshare.network.ApiService
import com.insanedev.quizshare.ui.screens.login.models.LoginEvent
import com.insanedev.quizshare.ui.screens.login.models.LoginSubState
import com.insanedev.quizshare.ui.screens.login.models.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(application: Application) : AndroidViewModel(application),
    EventHandler<LoginEvent> {
    private val _viewState = MutableLiveData(LoginViewState())
    val viewState: LiveData<LoginViewState> = _viewState

    val context = getApplication<Application>()

    override fun obtainEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.ActionClicked -> switchActionState()
            is LoginEvent.FirstNameChanged -> firstNameChanged(event.value)
            is LoginEvent.SecondNameChanged -> secondNameChanged(event.value)
            is LoginEvent.PatronymicNameChanged -> patronymicNameChanged(event.value)
            is LoginEvent.EmailChanged -> emailChanged(event.value)
            is LoginEvent.PasswordChanged -> passwordChanged(event.value)
            is LoginEvent.PasswordRepeatChanged -> passwordRepeatChanged(event.value)
            is LoginEvent.ForgetClicked -> forgetClicked()
            is LoginEvent.LoginClicked -> loginClicked()
            is LoginEvent.RegistrationClicked -> registrationClicked()
        }
    }

    private val apiService by lazy {
        ApiService.create()
    }


    private fun registrationClicked() {
        when (_viewState.value?.loginSubState) {
            LoginSubState.SignIn -> _viewState.postValue(_viewState.value?.copy(loginSubState = LoginSubState.SignUp))
            LoginSubState.SignUp -> performRegister()
            else -> {}
        }
    }

    private fun performRegister() {
        var validated = true

        var firstNameMessage = ""
        var secondNameMessage = ""
        var emailMessage = ""
        var passwordMessage = ""
        var passwordRepeatMessage = ""

        // First name validation
        when (_viewState.value?.firstNameValue) {
            "" -> {
                firstNameMessage = context.getString(R.string.requiered_field)
                validated = false
            }
            else -> firstNameMessage = ""

        }
        // Second name validation
        when (_viewState.value?.secondNameValue) {
            "" -> {
                secondNameMessage = context.getString(R.string.requiered_field)
                validated = false
            }
            else -> firstNameMessage = ""

        }
        // Email validation
        when {
            _viewState.value?.emailValue!!.isEmpty() -> {
                emailMessage = context.getString(R.string.requiered_field)
                validated = false
            }
            !validateEmail(_viewState.value!!.emailValue) -> {
                emailMessage = context.getString(R.string.email_not_valid)
                validated = false

            }
            else -> emailMessage = ""
        }
        // Password validation
        when {
            _viewState.value?.passwordValue!!.isEmpty() -> {
                passwordMessage = context.getString(R.string.requiered_field)
                validated = false
            }
            _viewState.value?.passwordValue!!.length < 4 -> {
                passwordMessage = context.getString(R.string.password_too_short)
                validated = false

            }
            else -> passwordMessage = ""
        }
        // Repeat password validation
        when {
            _viewState.value?.passwordRepeatValue!!.isEmpty() -> {
                passwordRepeatMessage = context.getString(R.string.requiered_field)
                validated = false
            }
            _viewState.value?.passwordRepeatValue != _viewState.value?.passwordValue -> {
                passwordRepeatMessage = context.getString(R.string.different_passwords)
                validated = false

            }
            else -> passwordRepeatMessage = ""
        }

        _viewState.postValue(_viewState.value?.copy(
            firstNameHelperText = firstNameMessage,
            secondNameHelperText = secondNameMessage,
            emailHelperText = emailMessage,
            passwordHelperText = passwordMessage,
            passwordRepeatHelperText = passwordRepeatMessage
        ))

        // Quit
        if (!validated)
            return


        viewModelScope.launch {
            apiService.tryRegister(
                email = _viewState.value!!.emailValue,
                password = _viewState.value!!.passwordValue,
                name = _viewState.value!!.firstNameValue,
                secondName = _viewState.value!!.secondNameValue,
                patronymicName = _viewState.value!!.patronymicNameValue
            )
        }
    }

    private fun loginClicked() {
        when (_viewState.value?.loginSubState) {
            LoginSubState.SignUp -> _viewState.postValue(_viewState.value?.copy(loginSubState = LoginSubState.SignIn))
            LoginSubState.SignIn -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _viewState.postValue(_viewState.value?.copy(isPerforming = true))
                    delay(1500)
                    _viewState.postValue(
                        _viewState.value?.copy(
                            isPerforming = false,
                            isLoginPassed = true
                        )
                    )
                }
            }
            else -> {}
        }
    }

    // TODO:
    private fun forgetClicked() {}

    private fun switchActionState() {

        when (_viewState.value?.loginSubState) {
            LoginSubState.SignIn -> Unit
            LoginSubState.SignUp -> Unit
            else -> {}
        }
    }

    private fun firstNameChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(firstNameValue = value))
    }

    private fun secondNameChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(secondNameValue = value))
    }

    private fun patronymicNameChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(patronymicNameValue = value))
    }

    private fun emailChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(emailValue = value))
        Log.d("LogInViewModel", "Email Posted")
    }

    private fun passwordChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(passwordValue = value))
    }

    private fun passwordRepeatChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(passwordRepeatValue = value))
    }
}