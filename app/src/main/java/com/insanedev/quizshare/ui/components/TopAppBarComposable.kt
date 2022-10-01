package com.insanedev.quizshare.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun TopAppBarComposable(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        backgroundColor = AppTheme.colors.surface,
        contentColor = AppTheme.colors.primaryText,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.clickable(onClick = onBackClick)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = ""
        )

    }
}