package com.insanedev.quizshare.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.insanedev.quizshare.R
import com.insanedev.quizshare.ui.components.GroupCard
import com.insanedev.quizshare.ui.theme.AppTheme

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