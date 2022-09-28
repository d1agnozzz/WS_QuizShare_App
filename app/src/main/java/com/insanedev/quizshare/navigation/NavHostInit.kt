package com.insanedev.quizshare.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.insanedev.quizshare.navigation.NavigationTree
import com.insanedev.quizshare.ui.screens.login.LoginScreen
import com.insanedev.quizshare.ui.screens.login.LoginViewModel
import com.insanedev.quizshare.ui.screens.splash.SplashScreen
import com.insanedev.quizshare.ui.screens.splash.SplashViewModel

@Composable
fun NavHostInit() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Main.name)

    {
        composable(NavigationTree.Splash.name) {
            val splashViewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                splashViewModel,
                onTokenNotFound = {
                    navController.navigate(
                        NavigationTree.Login.name
                    ) {
                        popUpTo(0)
                    }
                },
                onTokenFound = { email ->
                    navController.navigate("${NavigationTree.Main.name}/$email")
                }
            )
        }
        composable(NavigationTree.Login.name) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(loginViewModel = loginViewModel) {
                navController.navigate(
                    NavigationTree.Main.name
                ) {
                    popUpTo(0)
                }
            }
        }
        composable(
            route = "${NavigationTree.Main.name}/email",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            MainScreen()
        }
        /*...*/
    }

}