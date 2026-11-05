package com.example.smartpantry.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ecopantry.app.UserDashboardScreen

// === YOUR COLOR PALETTE ===
val Primary = Color(0xFFB8E7E7)
val PrimaryDark = Color(0xFF235F62)
val Background = Color(0xFFFFFFFF)
val SecondaryBg = Color(0xFFF3FAFA)
val MutedText = Color(0xFF667B7D)
val DarkButton = Color(0xFF1A2426)

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    val items = listOf(
        BottomNavItem("home", "Home", Icons.Filled.Home),
        BottomNavItem("pantry", "Pantry", Icons.Filled.Eco),
        BottomNavItem("recipes", "Recipes", Icons.Filled.Dashboard),
        BottomNavItem("records", "Log", Icons.Filled.List),
        BottomNavItem("profile", "Profile", Icons.Filled.Eco)
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        containerColor = Background,
        bottomBar = {
            NavigationBar(
                containerColor = Background,
                tonalElevation = 0.dp,
                modifier = Modifier.height(90.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left side items
                    items.take(2).forEach { item ->
                        NavBarItem(
                            item = item,
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            }
                        )
                    }

                    // Central Floating Action Button
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("add") {
                                launchSingleTop = true
                            }
                        },
                        containerColor = DarkButton,
                        shape = CircleShape,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Text(
                            text = "+",
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Right side items
                    items.takeLast(3).forEach { item ->
                        NavBarItem(
                            item = item,
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                UserDashboardScreen()
            }
            composable("pantry") {
                // PantryScreen()
                UserDashboardScreen()
            }
            composable("recipes") {
                // RecipesScreen()
                UserDashboardScreen()
            }
            composable("records") {
                // RecordsScreen()
                UserDashboardScreen()
            }
            composable("profile") {
                // ProfileScreen()
                UserDashboardScreen()
            }
            composable("add") {
                // AddScreen()
                UserDashboardScreen()
            }
        }
    }
}

@Composable
fun NavBarItem(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = if (selected) PrimaryDark else MutedText,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = item.label,
            fontSize = 12.sp,
            color = if (selected) PrimaryDark else MutedText,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}
