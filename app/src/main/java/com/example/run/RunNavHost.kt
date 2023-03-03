package com.example.run

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.run.ui.screens.HomeScreen
import com.example.run.ui.screens.history.HistoryScreen

@Composable
fun RunNavHost(
    navController: NavHostController,
    startDestination: String = Home.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeScreen()
        }
        composable(route = History.route) {
            HistoryScreen()
        }
    }
}