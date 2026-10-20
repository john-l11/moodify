package com.example.moodify

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moodify.routes.FavoritesScreen
import com.example.moodify.routes.HomeScreen
import com.example.moodify.routes.PlaylistsScreen
import com.example.moodify.routes.ProfileScreen

/**
 * This is going to be the main routing function that will make the calls to
 * the other composable functions. The other composable functions will be what
 * renders the pages to the router host.
 */
@Composable
//@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val menu = listOf<Screen>(
            Screen.Home,
            Screen.Favorites,
            Screen.Playlists,
            Screen.Profile
        )
        Scaffold (
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    menu.forEach{ screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = androidx.compose.ui.Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) { HomeScreen() }
                composable(Screen.Favorites.route) { FavoritesScreen() }
                composable(Screen.Playlists.route) { PlaylistsScreen() }
                composable(Screen.Profile.route) { ProfileScreen() }
            }
        }
    }
}