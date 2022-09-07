package com.insanedev.quizshare.ui.screens.login.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.R
import com.insanedev.quizshare.ui.components.QPasswordField
import com.insanedev.quizshare.ui.components.QTextField
import com.insanedev.quizshare.ui.screens.login.models.LoginViewState
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun SignInView(
    viewState: LoginViewState,
    onEmailFieldChange: (String) -> Unit,
    onPasswordFieldChange: (String) -> Unit,
    onForgetClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        QTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = viewState.emailValue,
            enabled = !viewState.isPerforming,
            label = stringResource(id = R.string.email_hint),
            onTextFieldChange = onEmailFieldChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        QPasswordField(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            value = viewState.passwordValue,
            enabled = !viewState.isPerforming,
            label = stringResource(id = R.string.password_hint),
            onTextFieldChange = onPasswordFieldChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        Text(
            modifier = Modifier
                .padding(top = 16.dp, end = 4.dp)
                .align(Alignment.End)
                .clickable(onClick = onForgetClick),
            text = stringResource(id = R.string.forget_password_button),
            color = AppTheme.colors.primaryAccent

        )
        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(44.dp),

            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.primaryAccent,
                contentColor = Color.White
            )
        ) {
            when (viewState.isPerforming) {
                false -> Text(
                    text = stringResource(id = R.string.btn_logIn),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                true -> CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        OutlinedButton(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(44.dp),
            onClick = onRegistrationClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = AppTheme.colors.primaryAccent
            )

        ) {
            Text(
                text = stringResource(id = R.string.btn_toRegistration),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
