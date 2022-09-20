package com.insanedev.quizshare.ui.screens.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.insanedev.quizshare.common.EventHandler
import com.insanedev.quizshare.network.ApiService
import com.insanedev.quizshare.ui.screens.login.models.LoginEvent
import com.insanedev.quizshare.ui.screens.login.models.LoginSubState
import com.insanedev.quizshare.ui.screens.login.models.LoginViewState
import com.insanedev.quizshare.ui.screens.login.models.RegisterValidator
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

    // val allValid = MediatorLiveData<Boolean>()


    // val context = getApplication<Application>()

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

        with(_viewState.value) {
//            TODO()
            if (this?.firstNameHelperTextId != null && this.secondNameHelperTextId != null &&
                this.emailHelperTextId != null && this.passwordHelperTextId != null &&
                this.passwordRepeatHelperTextId != null
            ) {
                firstNameChanged(_viewState.value!!.firstNameValue)
                secondNameChanged(_viewState.value!!.secondNameValue)
                emailChanged(_viewState.value!!.emailValue)
                passwordChanged(_viewState.value!!.passwordValue)
                passwordRepeatChanged(_viewState.value!!.passwordRepeatValue)
            } else {
                Log.d("performRegister", "Fields validated, registration performs")
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
        val errorId = RegisterValidator.getNameErrorIdOrNull(value)
        _viewState.postValue(
            _viewState.value?.copy(
                firstNameValue = value,
                firstNameHelperTextId = errorId
            )
        )
    }

    private fun secondNameChanged(value: String) {
        val errorId = RegisterValidator.getNameErrorIdOrNull(value)
        _viewState.postValue(
            _viewState.value?.copy(
                secondNameValue = value,
                secondNameHelperTextId = errorId
            )
        )
    }

    private fun patronymicNameChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(patronymicNameValue = value))
    }

    private fun emailChanged(value: String) {
        val errorId = RegisterValidator.getEmailErrorIdOrNull(value)

        _viewState.postValue(
            _viewState.value?.copy(
                emailValue = value,
                emailHelperTextId = errorId
            )
        )
        Log.d("LogInViewModel", "Email Posted")
    }

    private fun passwordChanged(value: String) {
        val errorId = RegisterValidator.getPasswordErrorIdOrNull(value)
        val errorIdRepeat = RegisterValidator.getPasswordRepeatErrorIdOrNull(
            _viewState.value!!.passwordRepeatValue,
            value
        )

        _viewState.postValue(
            _viewState.value?.copy(
                passwordValue = value, passwordHelperTextId = errorId,
                passwordRepeatHelperTextId = errorIdRepeat
            )
        )
    }

    private fun passwordRepeatChanged(value: String) {
        val errorId = RegisterValidator.getPasswordRepeatErrorIdOrNull(
            value,
            _viewState.value!!.passwordValue
        )

        _viewState.postValue(
            _viewState.value?.copy(
                passwordRepeatValue = value,
                passwordRepeatHelperTextId = errorId
            )
        )
    }
}