package com.mattis.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mattis.myapplication.NetworkHelper.makeGetRequest
import kotlinx.coroutines.launch

@Composable
fun JoinGameScreen(navController: NavHostController) {
    // Create a state variable to hold the game code input
    val coroutineScope = rememberCoroutineScope()
    val game_code = remember { androidx.compose.runtime.mutableStateOf("") }
    val statusText = remember { androidx.compose.runtime.mutableStateOf("Enter Game Code") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Centered content
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(statusText.value, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Text input field
            TextField(
                value = game_code.value,
                onValueChange = { game_code.value = it },
                label = { Text("Game Code") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Enter button
            Button(
                onClick = {
                    coroutineScope.launch {
                        statusText.value = "Joining Game..."
                        val response = makeGetRequest("http://192.168.178.40:8000/matchmaking/join/?game_code=${game_code.value}")

                        if (response.contains("game_code")) {
                            val returned_game_code = response.substringAfter("game_code:").trim()
                            statusText.value = "Joined Game! Match ID: $returned_game_code"
                        } else {
                            statusText.value = "Error: $response"
                        }
                    }
                }
            ) {
                Text("Enter")
            }


            Spacer(modifier = Modifier.height(16.dp))
        }

        // Back button in the bottom left corner
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("Back to Main Menu")
        }
    }
}