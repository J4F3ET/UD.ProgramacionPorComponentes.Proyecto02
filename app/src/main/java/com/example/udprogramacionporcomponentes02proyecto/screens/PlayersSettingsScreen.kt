package com.example.udprogramacionporcomponentes02proyecto.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppScreens
import com.example.udprogramacionporcomponentes02proyecto.util.BackgroundPlayersSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersSettingsScreen(navController:NavController){
    Scaffold{
        BackgroundPlayersSettings()
    }
}