package com.insanedev.quizshare.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.insanedev.quizshare.ui.screens.login.models.LoginEvent
import com.insanedev.quizshare.ui.screens.login.models.LoginSubState
import com.insanedev.quizshare.ui.screens.login.models.LoginViewState
import com.insanedev.quizshare.ui.screens.login.views.SignInView
import com.insanedev.quizshare.ui.screens.login.views.SignUpView

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onLoginPassed: () -> Unit
) {
    
    val viewState = loginViewModel.viewState.observeAsState(LoginViewState())

    with(viewState.value) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                when (loginSubState) {
                    LoginSubState.SignIn -> SignInView(
                        this@with,
                        onEmailFieldChange = {
                            loginViewModel.obtainEvent(LoginEvent.EmailChanged(it))
                        },
                        onPasswordFieldChange = {
                            loginViewModel.obtainEvent(LoginEvent.PasswordChanged(it))
                        },
                        onForgetClick = {
                            loginViewModel.obtainEvent(LoginEvent.ForgetClicked)
                        },
                        onLoginClick = {
                            loginViewModel.obtainEvent(LoginEvent.LoginClicked)
                        },
                        onRegistrationClick = {
                            loginViewModel.obtainEvent(LoginEvent.RegistrationClicked)
                        }
                    )
                    LoginSubState.SignUp -> SignUpView(
                        this@with,
                        onFirstNameFieldChange = {
                            loginViewModel.obtainEvent(LoginEvent.FirstNameChanged(it))
                        },
                        onSecondNameFieldChange = {
                            loginViewModel.obtainEvent(LoginEvent.SecondNameChanged(it))
                        },
                        onPatronymicNameFieldChange = {
                            loginViewModel.obtainEvent(LoginEvent.PatronymicNameChanged(it))
                        },
                        onEmailFieldChange = {
                            loginViewModel.obtainEvent(LoginEvent.EmailChanged(it))
                        },
                        onPasswordFieldChange = {
                            loginViewModel.obtainEvent(LoginEvent.PasswordChanged(it))
                        },
                        onPasswordRepeatFieldChange = {
                            loginViewModel.obtainEvent(LoginEvent.PasswordRepeatChanged(it))
                        },
                        onForgetClick = {
                            loginViewModel.obtainEvent(LoginEvent.ForgetClicked)
                        },
                        onLoginClick = {
                            loginViewModel.obtainEvent(LoginEvent.LoginClicked)
                        },
                        onRegistrationClick = {
                            loginViewModel.obtainEvent(LoginEvent.RegistrationClicked)
                        }
                    )

                }
            }
        }
    }
    
    LaunchedEffect(key1 = viewState.value.isLoginPassed){
        when(viewState.value.isLoginPassed) {
            true -> onLoginPassed.invoke()
            false -> Unit
        }
    }
}