package com.example.udprogramacionporcomponentes02proyecto.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesPlayerSettingsScreen.BottomBarPlayersSettingsScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesPlayerSettingsScreen.ListRooms
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesPlayerSettingsScreen.TopBarPlayersSettingsScreen
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersSettingsScreen(navController:NavController){
    Scaffold(
        topBar = { TopBarPlayersSettingsScreen() },
        bottomBar = { BottomBarPlayersSettingsScreen(navController) },
        content = {
            BackGrounds(1)
            PlayersSettingsScreenContent(navController)
        }
    )
}
@Composable
fun PlayersSettingsScreenContent(navController:NavController){
    ListRooms(navController)
}

