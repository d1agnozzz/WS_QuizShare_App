package com.insanedev.quizshare.ui.screens.login.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.insanedev.quizshare.R
import com.insanedev.quizshare.ui.components.QTextField
import com.insanedev.quizshare.ui.screens.login.models.LoginViewState

@Composable
fun SignInView(
    viewState: LoginViewState,
    onTextFieldChange: (String) -> Unit
) {
    Column() {
        QTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            value = viewState.emailValue,
            placeholder = stringResource(id = R.string.email_hint),
            onTextFieldChange = onTextFieldChange
        )
    }

}