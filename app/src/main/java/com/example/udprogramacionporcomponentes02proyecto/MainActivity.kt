package com.example.udprogramacionporcomponentes02proyecto

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.UDProgramacionPorComponentes02ProyectoTheme
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.RealTimeManager
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UDProgramacionPorComponentes02ProyectoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UDProgramacionPorComponentes02ProyectoTheme {
        Greeting("Android")
    }
}