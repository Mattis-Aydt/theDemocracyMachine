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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mattis.myapplication.NetworkHelper.makeGetRequest
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

@Composable
fun JoinGameScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val gameCode = remember { mutableStateOf("") }
    val playerName = remember { mutableStateOf("") }
    val statusText = remember { mutableStateOf("Enter Game Code") }

    Box(modifier = Modifier.fillMaxSize()) {
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

            TextField(
                value = gameCode.value,
                onValueChange = { gameCode.value = it },
                label = { Text("Game Code") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Frontend validation
                    if (playerName.value.isEmpty()) {
                        statusText.value = "Please enter a player name!"
                        return@Button
                    }
                    if (gameCode.value.isEmpty()) {
                        statusText.value = "Please enter a game code!"
                        return@Button
                    }
                    try {
                        gameCode.value.toInt()
                    } catch (e: NumberFormatException) {
                        statusText.value = "Game code must be a number!"
                        return@Button
                    }

                    coroutineScope.launch {
                        statusText.value = "Joining Game..."
                        val url = "http://192.168.178.40:8000/matchmaking/join/?game_code=${gameCode.value}&player_name=${playerName.value}"
                        val (statusCode,response) = makeGetRequest(url)
                        println(statusCode)
                        when (statusCode) {
                            200 -> {
                                statusText.value = "Game joined successfully!"
                                navController.navigate("game_lobby")
                            }
                            404 -> {
                                statusText.value = "Game not found!"
                            }
                            409 -> {
                                statusText.value = "Game is full!"
                            }
                            403 -> {
                                statusText.value = "Game has already started!"
                            }
                            422 -> {
                                statusText.value = "Player name already taken!"
                            }
                            else -> {

                                statusText.value = "Server error! See logcat for details."
                                Log.e("JoinGameScreen", "Error: $response")
                            }


                        }



                    }
                }
            ) {
                Text("Enter")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

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
