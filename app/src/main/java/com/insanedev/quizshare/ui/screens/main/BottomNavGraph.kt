package com.insanedev.quizshare.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Profile.screen_route) {
        composable(BottomNavItem.Profile.screen_route) {
            ProfileScreen()
        }
        composable(BottomNavItem.Groups.screen_route) {
            GroupsScreen()
        }
        composable(BottomNavItem.Quizzes.screen_route) {
            QuizzesScreen()
        }

//        composable(
//            route = "${BottomNavItem.Quizzes.screen_route}/{users_email}",
//            arguments = listOf(
//                navArgument("users_email") { type = NavType.StringType }
//            )
//        ) { backStackEntry ->
//            val email = backStackEntry.arguments?.getString("users_email").orEmpty()
//            QuizzesScreen()
//        }

    }
}