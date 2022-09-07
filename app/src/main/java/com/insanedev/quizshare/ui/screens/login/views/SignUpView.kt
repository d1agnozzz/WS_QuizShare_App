package com.insanedev.quizshare.ui.screens.login.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
fun SignUpView(
    viewState: LoginViewState,
    onFirstNameFieldChange: (String) -> Unit,
    onSecondNameFieldChange: (String) -> Unit,
    onPatronymicNameFieldChange: (String) -> Unit,
    onEmailFieldChange: (String) -> Unit,
    onPasswordFieldChange: (String) -> Unit,
    onPasswordRepeatFieldChange: (String) -> Unit,
    onForgetClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    with(viewState) {
        Column {
            // First
            QTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = viewState.firstNameValue,
                enabled = !viewState.isPerforming,
                isError = firstNameHelperTextId != null,
                errorText = "Это обязательное поле",
                label = stringResource(id = R.string.first_name_hint),
                onTextFieldChange = onFirstNameFieldChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                )
            )
            Text(
                text = firstNameHelperTextId?.let { stringResource(id = it) } ?: "",
//            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                style = TextStyle(
                    fontSize = 12.sp,
                    letterSpacing = 0.4.sp
                ),
                modifier = Modifier.padding(start = 16.dp)
            )


            // Second
            QTextField(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .fillMaxWidth(),
                value = viewState.secondNameValue,
                enabled = !viewState.isPerforming,
                isError = secondNameHelperTextId != null,
                label = stringResource(id = R.string.second_name_hint),
                onTextFieldChange = onSecondNameFieldChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            Text(
                text = secondNameHelperTextId?.let { stringResource(id = it) } ?: "",
//            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                style = TextStyle(
                    fontSize = 12.sp,
                    letterSpacing = 0.4.sp
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
            // Patronymic
            QTextField(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .fillMaxWidth(),
                value = viewState.patronymicNameValue,
                enabled = !viewState.isPerforming,
                label = stringResource(id = R.string.patronymic_name_hint),
                onTextFieldChange = onPatronymicNameFieldChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            Text(
                text = "",
//            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                style = TextStyle(
                    fontSize = 12.sp,
                    letterSpacing = 0.4.sp
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
            // Email
            QTextField(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .fillMaxWidth(),
                value = viewState.emailValue,
                enabled = !viewState.isPerforming,
                isError = emailHelperTextId != null,
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
            Text(
                text = emailHelperTextId?.let { stringResource(id = it) } ?: "",
//            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                style = TextStyle(
                    fontSize = 12.sp,
                    letterSpacing = 0.4.sp
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
            // Password
            QPasswordField(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .fillMaxWidth(),
                value = viewState.passwordValue,
                onTextFieldChange = onPasswordFieldChange,
                enabled = !viewState.isPerforming,
                isError = passwordHelperTextId != null,
                label = stringResource(id = R.string.password_hint),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )
            Text(
                text = passwordHelperTextId?.let { stringResource(id = it) } ?: "",
//            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                style = TextStyle(
                    fontSize = 12.sp,
                    letterSpacing = 0.4.sp
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
            // Repeat password
            QPasswordField(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .fillMaxWidth(),
                value = viewState.passwordRepeatValue,
                onTextFieldChange = onPasswordRepeatFieldChange,
                enabled = !viewState.isPerforming,
                isError = passwordRepeatHelperTextId != null,
                label = stringResource(id = R.string.repeat_password_hint),
                showPasswordIcon = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
            Text(
                text = passwordRepeatHelperTextId?.let { stringResource(id = it) } ?: "",
                style = TextStyle(
                    fontSize = 12.sp,
                    letterSpacing = 0.4.sp
                ),
                modifier = Modifier.padding(start = 16.dp)
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(44.dp),

                onClick = onRegistrationClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.primaryAccent,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.btn_register),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(44.dp),
                onClick = onLoginClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppTheme.colors.primaryAccent
                )

            ) {
                Text(
                    text = stringResource(id = R.string.btn_toLogIn),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
