package com.insanedev.quizshare.ui.screens.main.create_quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.R
import com.insanedev.quizshare.ui.components.StatisticCircle

@Composable
fun ProfileScreen(
    email: String
) {
    Column {
        Row(
            modifier = Modifier
                .padding(top = 24.dp, start = 32.dp, end = 12.dp)
                .fillMaxWidth(),
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.profile_pic_placeholder),
                    contentDescription = "profile picture",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(72.dp)
                        .aspectRatio(1f)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Иван Иванов",
                    fontSize = 24.sp
                )

                Text(
                    text = email,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Row {

                Icon(Icons.Outlined.Edit, contentDescription = "")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Outlined.Settings, contentDescription = "")
            }
        }

        Row(
            modifier = Modifier.padding(start = 24.dp, top = 64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatisticCircle(
                progress = 0f,
                innerContent = "2",
            )
            Spacer(modifier = Modifier.width(32.dp))
            Text(text = stringResource(R.string.quizzes_passed))

        }
        Row(
            modifier = Modifier.padding(start = 24.dp, top = 64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatisticCircle(
                progress = 0.83f,
                innerContent = "83",
            )
            Spacer(modifier = Modifier.width(32.dp))
            Text(text = stringResource(R.string.correctness_percentage))

        }
    }
}