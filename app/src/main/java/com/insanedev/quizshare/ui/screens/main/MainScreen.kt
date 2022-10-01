package com.insanedev.quizshare.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.insanedev.quizshare.ui.components.BottomNavigationComposable
import com.insanedev.quizshare.ui.screens.main.BottomNavigationGraph
import com.insanedev.quizshare.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    email: String
) {

    val systemUiController = rememberSystemUiController()
    val primaryAccent = AppTheme.colors.primaryAccent

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Main Screen",
            fontSize = 26.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }

    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationComposable(navController = navController) }
    ) {

        Box(modifier = Modifier.padding(it)) {
            BottomNavigationGraph(navController = navController, email)
        }
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = primaryAccent
        )
    }
}