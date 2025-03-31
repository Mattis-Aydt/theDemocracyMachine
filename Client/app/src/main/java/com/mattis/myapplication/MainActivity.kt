package com.mattis.myapplication

import MainScreen
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mattis.myapplication.ui.CreateGameScreen
import com.mattis.myapplication.ui.GameLobbyScreen
import com.mattis.myapplication.ui.HowToPlayScreen
import com.mattis.myapplication.ui.JoinGameScreen
import com.mattis.myapplication.ui.SettingsScreen
import com.mattis.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

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

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("how_to_play") { HowToPlayScreen(navController) }
        composable("join_game") { JoinGameScreen(navController) }
        composable("create_game") { CreateGameScreen(navController) }
        composable("game_lobby") { GameLobbyScreen(navController) }
    }
}

