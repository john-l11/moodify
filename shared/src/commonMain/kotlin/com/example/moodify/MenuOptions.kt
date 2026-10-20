package com.example.moodify

import androidx.compose.ui.graphics.vector.ImageVector
import io.github.hlcaptain.symbols.material.Icons
import io.github.hlcaptain.symbols.material.rounded.vectors.Favorite
import io.github.hlcaptain.symbols.material.rounded.vectors.Home
import io.github.hlcaptain.symbols.material.rounded.vectors.MusicNote
import io.github.hlcaptain.symbols.material.rounded.vectors.Person

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Rounded.Home)
    data object Favorites : Screen("favorites", "Favorites", Icons.Rounded.Favorite)
    data object Playlists: Screen("playlists", "Playlists", Icons.Rounded.MusicNote)
    data object Profile : Screen("profile", "Profile", Icons.Rounded.Person)
}