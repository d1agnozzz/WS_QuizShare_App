package com.insanedev.quizshare.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.insanedev.quizshare.ui.theme.AppTheme

@Composable
fun BottomNavigationComposable(navController: NavController) {
    val items = listOf(
        BottomNavItem.Quizzes,
        BottomNavItem.Groups,
        BottomNavItem.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navigationBarDestination = items.any { it.screen_route == currentRoute }

    if (navigationBarDestination) {
        NavigationBar(
            containerColor = AppTheme.colors.surface
        ) {


            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.title) },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 9.sp
                        )
                    },
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
                    onClick = {
                        navController.navigate(item.screen_route) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}