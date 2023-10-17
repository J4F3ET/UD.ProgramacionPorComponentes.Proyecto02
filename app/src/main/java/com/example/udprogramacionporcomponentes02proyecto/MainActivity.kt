package com.example.udprogramacionporcomponentes02proyecto

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppNavigation
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.UDProgramacionPorComponentes02ProyectoTheme
import com.example.udprogramacionporcomponentes02proyecto.util.RoomCurrent

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
                    AppNavigation()
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Row {
            Box(){
                Button(
                    onClick ={RoomCurrent.test()},
                    modifier = modifier
                ){}
            }
        }

        Row {
            Box(){
                Button(
                    onClick ={RoomCurrent.test2()},
                    modifier = modifier
                ){}
            }
        }

    }

}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UDProgramacionPorComponentes02ProyectoTheme {
        Greeting("Android")
    }
}