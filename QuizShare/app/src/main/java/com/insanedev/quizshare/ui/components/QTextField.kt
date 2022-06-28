package com.insanedev.quizshare.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun QTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
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

                )
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0x0C000000),
            focusedBorderColor = AppTheme.colors.primaryAccent

        ),
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