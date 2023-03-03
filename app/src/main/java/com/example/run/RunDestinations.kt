package com.example.run

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector

interface RunDestinations {
    val icon: ImageVector
    val route: String
    val textLabel: String
}

object History : RunDestinations {
    override val icon: ImageVector = Icons.Default.Timeline
    override val route: String = "history"
    override val textLabel: String = "History"
}

object Home : RunDestinations {
    override val icon: ImageVector = Icons.Default.Home
    override val route: String = "home"
    override val textLabel: String = "Home"
}

val navigationItems = listOf(Home, History)