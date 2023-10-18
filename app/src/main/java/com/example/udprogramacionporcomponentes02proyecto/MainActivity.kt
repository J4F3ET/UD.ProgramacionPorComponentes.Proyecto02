package com.example.udprogramacionporcomponentes02proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.PlayerService
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppNavigation
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.UDProgramacionPorComponentes02ProyectoTheme
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
         SessionCurrent.localPlayer = PlayerService().createPlayer("Inicializador", ColorP.RED)
         SessionCurrent.gameState =  GameStateService().createGameState()
         val dataset = GameStateService().getDatabaseChild(SessionCurrent.gameState.uuid)
         dataset.addValueEventListener(GameStateService().gameStateListener)
        super.onCreate(savedInstanceState)
        setContent {
            UDProgramacionPorComponentes02ProyectoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                    //AppNavigation()
                }
            }
        }
    }
}
@Composable
fun Greeting() {
    var gameState by remember { mutableStateOf(SessionCurrent.gameState) }
    Column {
        Row {
            Box(){
                Text(text = gameState.currentPlayer.toString())
            }
        }
        Row {
            Button(
                onClick ={SessionCurrent.test()}
            ){
                Text(text = "ButtonTest")
            }
        }

    }

}