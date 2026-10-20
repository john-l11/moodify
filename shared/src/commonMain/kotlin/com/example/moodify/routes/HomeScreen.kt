package com.example.moodify.routes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.moodify.Greeting
import org.jetbrains.compose.resources.painterResource

import moodify.shared.generated.resources.Res
import moodify.shared.generated.resources.compose_multiplatform

/**
 * This is the home screen that will display statistics of their mood along with top
 * songs that will potentially show up on their playlist
 */
@Composable
fun HomeScreen() {
    var showContent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            text = "Moodify"
        )
        Text (
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            text = "Recently Played Songs"
        )
        // This button will pull a new view that will pull the users listening
        // history
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("View All")
        }
        Row {
            // we'll pull the users most recently played songs here
        }
        Row {
            // The plan is to make this a 2 x 2 grid view just like on spotify
        }
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            text = "What music are you feeling?"
        )
        // Implement Scrollable list here
        // This button will make the api call to spotify to generate the playlist
        // for the user based on the mood that they have selected in the scrollable
        // list.
        // potential features is adding a bias list for artists to make a good
        // portion of the playlist feature selected artists.
        Button(onClick = {}) {
            Text("Make Playlist!")
        }
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }
}