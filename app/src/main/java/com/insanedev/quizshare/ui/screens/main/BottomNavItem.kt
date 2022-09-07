package com.insanedev.quizshare.ui.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Groups
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(var title: String, var icon: ImageVector, var screen_route: String) {
    Profile("Профиль", Icons.Filled.AccountCircle, "Profile"),
    Groups("Группы", Icons.Filled.Groups, "Groups"),
    Quizzes("Тесты", Icons.Filled.AssignmentTurnedIn, "Quizzes")
}