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
fun CreateGameScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val statusText = remember { androidx.compose.runtime.mutableStateOf("Press Create to start") }

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

            // Create button
            Button(
                onClick = {
                    coroutineScope.launch {
                        statusText.value = "Creating Game..."  // Update UI while waiting
                        val response = makeGetRequest("http://192.168.178.40:8000/matchmaking/create")

                        // Simulate JSON parsing if response contains "match_id"
                        if (response.contains("match_id")) {
                            val matchId = response.substringAfter("match_id:").trim()
                            statusText.value = "Game Created! Match ID: $matchId"
                        } else {
                            statusText.value = "Error: $response"
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