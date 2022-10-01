package com.insanedev.quizshare.ui.screens.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.insanedev.quizshare.R
import com.insanedev.quizshare.common.EventHandler
import com.insanedev.quizshare.common.LoginResult
import com.insanedev.quizshare.common.RegisterResult
import com.insanedev.quizshare.data.Token
import com.insanedev.quizshare.data.tokenStore
import com.insanedev.quizshare.network.ApiService
import com.insanedev.quizshare.ui.screens.login.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(application: Application) : AndroidViewModel(application),
    EventHandler<LoginEvent> {
    private val _viewState = MutableLiveData(LoginViewState())
    val viewState: LiveData<LoginViewState> = _viewState

    val context = application.applicationContext

    val tokenFlow: Flow<Token> = context.tokenStore.data.catch { exception ->
        if (exception is IOException) {
            Log.e("E", "Error reading sort order preferences.", exception)
            emit(Token.getDefaultInstance())
        } else {
            throw exception
        }
    }

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

    private suspend fun updateToken(token: String) {
        context.tokenStore.updateData { currentToken ->
            currentToken.toBuilder().setToken(token).build()
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

    private fun updateHelperText() {
        _viewState.value = _viewState.value!!.copy(
            firstNameHelperTextId = RegisterValidator.getNameErrorIdOrNull(_viewState.value!!.firstNameValue),
            secondNameHelperTextId = RegisterValidator.getNameErrorIdOrNull(_viewState.value!!.secondNameValue),
            emailHelperTextId = RegisterValidator.getNameErrorIdOrNull(_viewState.value!!.emailValue),
            passwordHelperTextId = RegisterValidator.getPasswordErrorIdOrNull(_viewState.value!!.passwordValue),
            passwordRepeatHelperTextId = RegisterValidator.getPasswordRepeatErrorIdOrNull(
                _viewState.value!!.passwordRepeatValue,
                _viewState.value!!.passwordValue
            )
        )
    }

    private fun validateLoginFields(): Boolean {
        val validateEmail = null ==
                RegisterValidator.getEmailErrorIdOrNull(_viewState.value!!.emailValue)
        val validatePassword = null ==
                RegisterValidator.getPasswordErrorIdOrNull(_viewState.value!!.passwordValue)
        return validateEmail && validatePassword
    }

    private fun validateRegisterFields(): Boolean {
        val validateFirstName = null ==
                RegisterValidator.getNameErrorIdOrNull(_viewState.value!!.firstNameValue)
        val validateSecondName = null ==
                RegisterValidator.getNameErrorIdOrNull(_viewState.value!!.secondNameValue)
        val validateEmail = null ==
                RegisterValidator.getNameErrorIdOrNull(_viewState.value!!.emailValue)
        val validatePassword = null ==
                RegisterValidator.getPasswordErrorIdOrNull(_viewState.value!!.passwordValue)
        val validateRepeatPassword = null ==
                RegisterValidator.getPasswordRepeatErrorIdOrNull(
                    _viewState.value!!.passwordRepeatValue,
                    _viewState.value!!.passwordValue
                )

        return validateFirstName && validateSecondName && validateEmail && validatePassword && validateRepeatPassword
    }

    private fun performRegister() {

        if (!validateRegisterFields()) {
            // *Changed functions also updates helper texts
//            firstNameChanged(_viewState.value!!.firstNameValue)
//            secondNameChanged(_viewState.value!!.secondNameValue)
//            emailChanged(_viewState.value!!.emailValue)
//            passwordChanged(_viewState.value!!.passwordValue)
//            passwordRepeatChanged(_viewState.value!!.passwordRepeatValue)
            updateHelperText()
        } else {
            Log.d("performRegister", "Fields validated, registration performs")
            viewModelScope.launch() {
                _viewState.postValue(_viewState.value?.copy(isPerforming = true))

                delay(500)
                val registerResult = apiService.tryRegister(
                    email = _viewState.value!!.emailValue,
                    password = _viewState.value!!.passwordValue,
                    name = _viewState.value!!.firstNameValue,
                    secondName = _viewState.value!!.secondNameValue,
                    patronymicName = _viewState.value!!.patronymicNameValue
                )

                when (registerResult) {
                    is RegisterResult.Ok -> {
                        updateToken(registerResult.token)
                        _viewState.postValue(
                            _viewState.value?.copy(
                                loginAction = LoginAction.OpenMainScreen(email = _viewState.value!!.emailValue),
                                isLoginPassed = true
                            )
                        )
                    }
                    is RegisterResult.EmailAlreadyExists -> {
                        _viewState.postValue(
                            _viewState.value?.copy(
                                emailHelperTextId = R.string.email_already_exists,
                                isPerforming = false
                            )
                        )
                    }
                    is RegisterResult.EmailIsNotValid -> {
                        _viewState.postValue(
                            _viewState.value?.copy(
                                emailHelperTextId = R.string.email_not_valid,
                                isPerforming = false
                            )
                        )
                    }
                    is RegisterResult.SomethingWentWrong -> {
                        updateHelperText()
                        _viewState.value = _viewState.value?.copy(
                            isPerforming = true,
                            loginAction = LoginAction.None,
                            isLoginPassed = false
                        )

                    }
                }
            }
        }
    }

    private fun loginClicked() {
        when (_viewState.value?.loginSubState) {
            LoginSubState.SignUp -> _viewState.postValue(_viewState.value?.copy(loginSubState = LoginSubState.SignIn))
            LoginSubState.SignIn -> {
                performLogin()
            }
            else -> {}
        }
    }

    private fun performLogin() {
        if (!validateLoginFields()) {
            updateHelperText()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                _viewState.postValue(_viewState.value?.copy(isPerforming = true))
                val loginResult = apiService.tryLogin(
                    email = _viewState.value!!.emailValue,
                    password = _viewState.value!!.passwordValue
                )
                when (loginResult) {
                    is LoginResult.Ok -> {
                        updateToken(loginResult.token)
                        _viewState.postValue(
                            _viewState.value?.copy(
                                isLoginPassed = true,
                                loginAction = LoginAction.OpenMainScreen(email = _viewState.value!!.emailValue)
                            )
                        )
                    }
                    is LoginResult.SomethingWentWrong -> {
                        updateHelperText()
                        _viewState.value = _viewState.value?.copy(
                            isPerforming = false,
                            loginAction = LoginAction.None,
                            isLoginPassed = false
                        )
                    }
                    is LoginResult.IncorrectCredentials -> {
                        _viewState.postValue(
                            _viewState.value?.copy(
                                emailHelperTextId = R.string.incorrect_credentials,
                                isPerforming = false
                            )
                        )
                    }
                }
            }
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
        _viewState.value = _viewState.value?.copy(
            firstNameValue = value,
            firstNameHelperTextId = errorId

        )
    }

    private fun secondNameChanged(value: String) {
        val errorId = RegisterValidator.getNameErrorIdOrNull(value)
        _viewState.value = _viewState.value?.copy(
            secondNameValue = value,
            secondNameHelperTextId = errorId

        )
    }

    private fun patronymicNameChanged(value: String) {
        _viewState.value = _viewState.value?.copy(patronymicNameValue = value)
    }

    private fun emailChanged(value: String) {
        val errorId = RegisterValidator.getEmailErrorIdOrNull(value)

        _viewState.value = _viewState.value?.copy(
            emailValue = value,
            emailHelperTextId = errorId
        )
        Log.d("LogInViewModel", "Email Posted")
    }

    private fun passwordChanged(value: String) {
        val errorId = RegisterValidator.getPasswordErrorIdOrNull(value)
        val errorIdRepeat = RegisterValidator.getPasswordRepeatErrorIdOrNull(
            _viewState.value!!.passwordRepeatValue,
            value
        )

        _viewState.value = _viewState.value?.copy(
            passwordValue = value, passwordHelperTextId = errorId,
            passwordRepeatHelperTextId = errorIdRepeat
        )

    }

    private fun passwordRepeatChanged(value: String) {
        val errorId = RegisterValidator.getPasswordRepeatErrorIdOrNull(
            value,
            _viewState.value!!.passwordValue
        )

        _viewState.value = _viewState.value?.copy(
            passwordRepeatValue = value,
            passwordRepeatHelperTextId = errorId
        )

    }
}