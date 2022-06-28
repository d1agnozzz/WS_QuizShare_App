package com.insanedev.quizshare.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun QTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    secureText: Boolean = false,
    onTextFieldChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,

        value = value,
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    color = AppTheme.colors.primaryText
                ),
                fontSize = 16.sp

            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0x0C000000),
            focusedBorderColor = AppTheme.colors.primaryAccent

        ),
        visualTransformation = if (secureText) PasswordVisualTransformation() else VisualTransformation.None,
        onValueChange = onTextFieldChange
    )
}

//@Composable
//@Preview
//fun QTextFieldPreview() {
//    QTextField(
//        value = "Email",
//        placeholder = "First Name",
//        onValueChange = {
//
//        }
//    )
//}