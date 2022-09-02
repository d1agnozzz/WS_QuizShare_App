package com.insanedev.quizshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.insanedev.quizshare.ui.screens.NavHostInit
import com.insanedev.quizshare.ui.theme.QuizShareTheme
import com.insanedev.quizshare.ui.theme.lightPalette
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizShareTheme {
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = lightPalette.background
                    )
                }

                NavHostInit()
            }
        }
    }
}












