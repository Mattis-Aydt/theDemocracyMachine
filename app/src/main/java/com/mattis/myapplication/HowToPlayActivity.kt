package com.mattis.myapplication

import android.app.ActivityOptions
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mattis.myapplication.ui.theme.MyApplicationTheme

class HowToPlayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                HowToPlayScreen()
            }
        }
    }

    @Composable
    fun HowToPlayScreen() {
        // Column to arrange content
        Column(
            modifier = Modifier
                .fillMaxSize() // Take up all available space
                .padding(16.dp), // Add padding
            verticalArrangement = Arrangement.Center, // Vertically center the content
            horizontalAlignment = Alignment.CenterHorizontally // Horizontally center the content
        ) {
            Text("How to Play")
            Spacer(modifier = Modifier.height(16.dp)) // Add space
            Button(onClick = {
                // Custom finish with no animation
                finish()
                overridePendingTransition(0, 0) // Close the activity with no transition animation

            }) {
                Text("Back to Main Activity")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HowToPlayPreview() {
    MyApplicationTheme {
        HowToPlayActivity().HowToPlayScreen()
    }
}
