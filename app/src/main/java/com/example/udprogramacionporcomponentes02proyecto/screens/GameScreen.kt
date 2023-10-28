package com.example.udprogramacionporcomponentes02proyecto.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController:NavController){
    Scaffold(
        topBar = { TopBarGameScreen() },
        bottomBar = { BottomBarGameScreen() },
        content = {
            BackGrounds(2)
            GameScreenContent()
        }
    )
}
@Composable
 fun TopBarGameScreen(){}
@Composable
fun BottomBarGameScreen(){}
@Composable
fun GameScreenContent(){

}
@Preview
@Composable
fun preview(){
    GameScreen(navController = NavController(LocalContext.current))
}