package com.insanedev.quizshare.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun CircleWithIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
    color = AppTheme.colors.completeStatus,
    modifier = modifier
    .aspectRatio(1f)
    .clip(CircleShape)
    ) {
        Box {
            Icon(imageVector = imageVector, contentDescription = "completed",
            modifier = Modifier.align(Alignment.Center).size(30.dp), tint = Color.White)
        }
    }
}