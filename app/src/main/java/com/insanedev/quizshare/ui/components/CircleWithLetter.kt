package com.insanedev.quizshare.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun CircleWithLetter(
    letter: Char,
    modifier: Modifier = Modifier
) {
    Surface(
        color = AppTheme.colors.primaryAccent,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
    ) {
        Box {
            Text(
                text = letter.toString(),
                fontSize =  24.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}