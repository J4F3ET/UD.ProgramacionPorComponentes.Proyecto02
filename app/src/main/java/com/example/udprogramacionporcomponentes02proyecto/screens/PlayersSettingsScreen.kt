package com.example.udprogramacionporcomponentes02proyecto.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.BottomBarPlayersSettingsScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.ListRooms
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.TopBarPlayersSettingsScreen
import com.example.udprogramacionporcomponentes02proyecto.util.BackGrounds

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersSettingsScreen(navController:NavController){
    Scaffold(
        topBar = {TopBarPlayersSettingsScreen()},
        bottomBar = {BottomBarPlayersSettingsScreen()},
        content = {
            BackGrounds(1)
            PlayersSettingsScreenContent(navController)
        }
    )
}

@Composable
fun PlayersSettingsScreenContent(navController:NavController){
    ListRooms()
}
@Preview
@Composable
fun PreviewScreen(){
    PlayersSettingsScreen(navController = NavController(LocalContext.current))
}
