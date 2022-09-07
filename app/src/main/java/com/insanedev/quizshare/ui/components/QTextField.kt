package com.insanedev.quizshare.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QTextField(
    value: String,
    label: String,
    onTextFieldChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String = "",
    secureText: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(),
    keyboardActions: KeyboardActions = KeyboardActions.Default,

    ) {
    OutlinedTextField(
        textStyle = TextStyle(
            fontSize = 16.sp,
            letterSpacing = 0.5.sp
        ),
        modifier = modifier.heightIn(64.dp),
        value = value,
        isError = isError,
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = AppTheme.colors.secondaryText
                ),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp
            )
        },

        trailingIcon = {
            if (isError) {
                Icon(Icons.Default.ErrorOutline, contentDescription = "error sign")
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0x0C000000),
            focusedBorderColor = AppTheme.colors.primaryAccent,
            focusedLabelColor = AppTheme.colors.primaryAccent


        ),
        enabled = enabled,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        visualTransformation = if (secureText) PasswordVisualTransformation() else VisualTransformation.None,
        onValueChange = onTextFieldChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
//    if (isError) {
//        Text(
//            text = errorText,
//            style = TextStyle(
//                fontSize = 12.sp,
//                letterSpacing = 0.4.sp
//            ),
//            modifier = Modifier.padding(start = 16.dp, top = 3.dp)
//        )
//    }
}

@Preview(showBackground = true)
@Composable
fun QTextFieldPreview() {
    QTextField(value = "", label = "Test text field", onTextFieldChange = {})
}