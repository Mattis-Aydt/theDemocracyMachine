package com.mattis.myapplication

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattis.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // Call the function to show the buttons
                CenteredButtons()
            }
        }
    }

    @Composable
    fun CenteredButtons() {
        // Column to arrange buttons vertically
        Column(
            modifier = Modifier
                .fillMaxSize() // Take up all available space
                .padding(16.dp), // Add padding around the content
            verticalArrangement = Arrangement.Center, // Vertically center the buttons
            horizontalAlignment = Alignment.CenterHorizontally // Horizontally center the buttons
        ) {
            Button(onClick = { /* Handle first button click */ }) {
                Text("Play")
            }
            Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons
            Button(onClick = { /* Handle second button click */ }) {
                Text("Settings")
            }
            Spacer(modifier = Modifier.height(16.dp)) // Add space between buttons
            Button(onClick = {
                // When "How To Play" button is clicked, start HowToPlayActivity
                val intent = Intent(this@MainActivity, HowToPlayActivity::class.java)
                // Create a Bundle for custom activity options (no transition)
                val options = ActivityOptions.makeCustomAnimation(this@MainActivity, 0, 0)

                // Start the activity with the options bundle
                startActivity(intent, options.toBundle())
            }) {
                Text("How To Play")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MyApplicationTheme {
        // Preview the MainActivity content
        MainActivity().CenteredButtons()
    }
}
