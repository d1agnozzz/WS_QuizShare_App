package com.insanedev.quizshare.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.insanedev.quizshare.ui.screens.main.BottomNavigationComposable
import com.insanedev.quizshare.ui.screens.main.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
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
            NavigationGraph(navController = navController)
        }
    }
}