package com.example.run

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

@Composable
fun BottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    Surface {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            navigationItems.forEach { screen ->
                val selected = currentDestination?.route == screen.route
                BottomNavigationItem(
                    icon = {
                        if (selected) {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        } else {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    },
                    label = {
                        if (selected) {
                            Text(
                                text = screen.textLabel,
                                color = Color.Black
                            )
                        } else {
                            Text(
                                text = screen.textLabel,
                                color = Color.Gray
                            )
                        }
                    },
                    selected = selected,
                    onClick = { navController.navigate(screen.route) },
                )
            }
        }
    }
}