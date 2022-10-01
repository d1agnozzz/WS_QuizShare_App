package com.insanedev.quizshare.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.insanedev.quizshare.R
import com.insanedev.quizshare.ui.components.QuizCardCompleted
import com.insanedev.quizshare.ui.components.QuizCardInProgress
import com.insanedev.quizshare.ui.components.QuizType
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun QuizzesScreen(
    onJoinQuizClick: () -> Unit,
    onCreateQuizClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                QuizCardCompleted(title = "Test", group = "Test", quizType = QuizType.Quiz)
            }
            item {
                QuizCardInProgress(title = "Test2", group = "Test2", answeredQuestions = 15, totalQuestions = 27)
            }
        }
        Row {
            FilledTonalButton(
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = AppTheme.colors.primaryAccent
                ),
//                modifier = Modifier.width(128.dp),
                onClick = onJoinQuizClick
            ) {
                Icon(
                    Icons.Default.Link,
                    contentDescription = "Join",
                    tint = Color.White
                )
                Text(
                    text = stringResource(id = R.string.join_quiz),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
//                modifier = Modifier.width(128.dp),
                onClick = onCreateQuizClick
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "",
                    tint = AppTheme.colors.secondaryText
                )
                Text(
                    text = stringResource(id = R.string.create_quiz),
                    color = AppTheme.colors.secondaryText
                )
            }
        }
    }
}