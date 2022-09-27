package com.insanedev.quizshare.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.R
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun QuizCardCompleted(
    title: String,
    group: String?,
    quizType: QuizType
) {
    ElevatedCard(
        modifier = Modifier
            .height(96.dp)
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleWithIcon(
                imageVector = Icons.Rounded.CheckCircleOutline,
                modifier = Modifier.height(50.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )


                Text(
                    text = when (quizType) {
                        is QuizType.Test -> stringResource(R.string.result) + "${quizType.correctQuestions}/${quizType.totalQuestions}"
                        is QuizType.Quiz -> stringResource(R.string.not_rating_quiz)
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = group ?: "",
                fontSize = 14.sp,
                color = AppTheme.colors.primaryAccent
            )
        }

    }
}

//@Preview
//@Composable
//fun QuizCardCompletedPreview() {
//    QuizCardCompleted(title = "", group = "", quizType = QuizType.Quiz)
//}
