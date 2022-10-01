package com.insanedev.quizshare.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.insanedev.quizshare.navigation.NavigationTree
import com.insanedev.quizshare.ui.components.BottomNavItem
import com.insanedev.quizshare.ui.screens.main.create_quiz.ProfileScreen
import com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz.CreateQuizScreen
import com.insanedev.quizshare.ui.screens.main.quizzes.create_quiz.CreateQuizViewModel

@Composable
fun BottomNavigationGraph(navController: NavHostController, email: String) {
    NavHost(navController, startDestination = BottomNavItem.Quizzes.screen_route) {
        composable(BottomNavItem.Profile.screen_route) {
            ProfileScreen(email)
        }
        composable(BottomNavItem.Groups.screen_route) {
            GroupsScreen()
        }
        composable(BottomNavItem.Quizzes.screen_route) {
            QuizzesScreen(onCreateQuizClick = {
                navController.navigate(NavigationTree.CreateQuiz.name)
            }, onJoinQuizClick = {})
        }
        composable(NavigationTree.CreateQuiz.name) { backStackEntry ->
            val createQuizViewModel = hiltViewModel<CreateQuizViewModel>()
            CreateQuizScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = createQuizViewModel )
        }
    }
}