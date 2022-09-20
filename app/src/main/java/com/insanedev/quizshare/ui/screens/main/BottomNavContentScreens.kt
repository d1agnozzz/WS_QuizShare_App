package com.insanedev.quizshare.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insanedev.quizshare.R
import com.insanedev.quizshare.ui.components.CircleWithIcon
import com.insanedev.quizshare.ui.components.GroupCard
import com.insanedev.quizshare.ui.components.StatisticCircle
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun ProfileScreen() {
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
                    text = "example@mail.com",
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
                innerContent = "100",
            )
            Spacer(modifier = Modifier.width(32.dp))
            Text(text = stringResource(R.string.correctness_percentage))

        }
    }
}

@Composable
fun GroupsScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(Modifier.weight(1f)) {
            item {
                GroupCard(groupTitle = "MTUCI_BVT2003", usersRank = 2, totalRank = 28) {

                }
            }
        }
        Row {
            FilledTonalButton(
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = AppTheme.colors.primaryAccent
                ),
                modifier = Modifier.width(128.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = stringResource(id = R.string.join_group),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
                modifier = Modifier.width(128.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "",
                    tint = AppTheme.colors.secondaryText
                )
                Text(
                    text = stringResource(id = R.string.create_group),
                    color = AppTheme.colors.secondaryText
                )
            }
        }
    }
}

@Composable
fun QuizzesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
       LazyColumn {
           item{
               ElevatedCard(
                   modifier = Modifier
                       .height(96.dp)
                       .padding(4.dp)
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
                               text = "Тест 1",
                               fontSize = 16.sp,
                               fontWeight = FontWeight.Medium
                           )
                           Text(
                               text = "Результат: 13/15",
                               fontSize = 14.sp,
                               fontWeight = FontWeight.Normal
                           )
                       }
                       Spacer(modifier = Modifier.weight(1f))
                       Text(
                           text = "MTUCI_BVT2002",
                           fontSize = 14.sp,
                           color = AppTheme.colors.primaryAccent
                       )
                   }
               }
           }
       }
    }
}