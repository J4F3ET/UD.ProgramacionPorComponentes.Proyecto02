package com.example.udprogramacionporcomponentes02proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppNavigation
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.UDProgramacionPorComponentes02ProyectoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//         SessionCurrent.localPlayer = PlayerService().createPlayer("Inicializador", ColorP.RED)
//         SessionCurrent.gameState =  GameStateService().createGameState()
//         val dataset = GameStateService().getDatabaseChild(SessionCurrent.gameState.uuid)
//         dataset.addValueEventListener(GameStateService().gameStateListener)
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