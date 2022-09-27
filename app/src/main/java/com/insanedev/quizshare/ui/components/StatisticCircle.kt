package com.insanedev.quizshare.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun StatisticCircle(
    progress: Float,

    size: Dp = 112.dp,
    strokeWidth: Dp = 12.dp,
    modifier: Modifier = Modifier,
    innerContent: String
) {
    Box(modifier = modifier.size(size)) {
        CircularProgressIndicator(
            progress = 1f,
            strokeWidth = strokeWidth,
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF2F0EF)
        )
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = strokeWidth,
            modifier = Modifier.fillMaxSize(),
            color = AppTheme.colors.completeStatus
        )
        Text(
            text = innerContent,
            fontSize = (size.value/2.5).sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

