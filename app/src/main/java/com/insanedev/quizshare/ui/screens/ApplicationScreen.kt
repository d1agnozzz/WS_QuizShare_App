package com.insanedev.quizshare.ui.screens

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.insanedev.quizshare.navigation.NavigationTree
import com.insanedev.quizshare.ui.screens.login.LoginScreen
import com.insanedev.quizshare.ui.screens.login.LoginViewModel
import com.insanedev.quizshare.ui.screens.splash.SplashScreen

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Splash.name) {
        composable(NavigationTree.Splash.name ) { SplashScreen(navController) }
        composable(NavigationTree.Login.name) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(loginViewModel = loginViewModel)
        }
        /*...*/
    }

}