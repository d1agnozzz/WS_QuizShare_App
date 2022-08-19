package com.insanedev.quizshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.insanedev.quizshare.ui.screens.ApplicationScreen
import com.insanedev.quizshare.ui.theme.AppTheme
import com.insanedev.quizshare.ui.theme.QuizShareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizShareTheme {
                val systemUiController = rememberSystemUiController()
                val surface = Color.Transparent
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = surface
                    )
                }

                ApplicationScreen( )
            }
        }
    }
}

