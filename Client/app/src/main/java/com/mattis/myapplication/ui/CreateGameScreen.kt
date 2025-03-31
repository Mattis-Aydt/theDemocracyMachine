package com.mattis.myapplication.ui

import android.util.Log
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
import com.mattis.myapplication.NetworkHelper
import com.mattis.myapplication.NetworkHelper.makeGetRequest
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun CreateGameScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val statusText = remember { androidx.compose.runtime.mutableStateOf("Press Create to start") }
    val playerName = remember { androidx.compose.runtime.mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(statusText.value, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = playerName.value,
                onValueChange = { playerName.value = it },
                label = { Text("Player Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Create button
            Button(
                onClick = {
                    if (playerName.value.isEmpty()) {
                        statusText.value = "Please enter a player name!"
                        return@Button
                    }
                    coroutineScope.launch {
                        statusText.value = "Creating Game..."  // Show loading state

                        val (statusCode, responseBody) = makeGetRequest("http://192.168.178.40:8000/matchmaking/create/?player_name=${playerName.value}")
                        when (statusCode) {
                            200 -> {
                                statusText.value = "Game created successfully!"
                                navController.navigate("game_lobby")
                            }
                            else -> {
                                statusText.value = "Server error! See logcat for details."
                                Log.e("CreateGameScreen", "Error: $responseBody")
                            }
                        }

                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create")
            }


            Spacer(modifier = Modifier.height(16.dp))
        }

        // Back button
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