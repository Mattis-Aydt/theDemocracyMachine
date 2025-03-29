package com.mattis.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mattis.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // Wrap navigation in a Surface with the app background
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // NavHost with no custom transitions
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("how_to_play") { HowToPlayScreen(navController) }
        composable("join_game") {  JoinGameScreen(navController)}
    }
}



@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate("join_game") {
                popUpTo("main")
                launchSingleTop = true
            }
        }) {
            Text("Join Game")

        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Handle create game logic */ }) {
            Text("Create Game")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("settings") {
                popUpTo("main")
                launchSingleTop = true
            }
        }) {
            Text("Settings")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("how_to_play") {
                popUpTo("main")
                launchSingleTop = true
            }
        }) {
            Text("How To Play")
        }
    }
}
@Composable
fun JoinGameScreen(navController: NavHostController) {
    // Create a state variable to hold the game code input
    val gameCode = remember { androidx.compose.runtime.mutableStateOf("") }

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
            Text("Enter Game Code", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Text input field
            TextField(
                value = gameCode.value,
                onValueChange = { gameCode.value = it },
                label = { Text("Game Code") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Enter button
            Button(
                onClick = {
                    // Handle game code submission
                    println("Game Code Entered: ${gameCode.value}")
                    // You can navigate or take any action here
                },
                modifier = Modifier.fillMaxWidth()
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


@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Main Menu")
        }
    }
}

@Composable
fun HowToPlayScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("How To Play", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Main Menu")
        }
    }
}
