package com.insanedev.quizshare.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun QPasswordField(
    onTextFieldChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    value: String = "",
    enabled: Boolean = true,
    isError: Boolean = false,
    showPasswordIcon: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(),
    keyboardActions: KeyboardActions = KeyboardActions.Default,

    ) {

    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        textStyle = TextStyle(
            fontSize = if (showPassword) 16.sp else 18.sp,
            fontWeight = if (showPassword) FontWeight.Normal else FontWeight.W900,
            letterSpacing = if (showPassword) 0.5.sp else 0.5.sp
        ),
        modifier = modifier.heightIn(64.dp),
        value = value,
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

        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(0x0C000000),
            focusedBorderColor = AppTheme.colors.primaryAccent

        ),
        trailingIcon = {
            if (showPasswordIcon) {
                Box(modifier = Modifier.clickable {
                    showPassword = !showPassword
                }) {
                    Icon(Icons.Default.RemoveRedEye, contentDescription = "show/hide password")
                }
            }
        },
        enabled = enabled,
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        visualTransformation = if (!showPassword) PasswordVisualTransformation(mask = '●') else VisualTransformation.None,
        onValueChange = onTextFieldChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QPasswordFieldPreview() {
    QTextField(value = "", label = "Test text field", onTextFieldChange = {})
}