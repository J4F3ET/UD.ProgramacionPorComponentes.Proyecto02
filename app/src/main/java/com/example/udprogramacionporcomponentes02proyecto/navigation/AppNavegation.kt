package com.example.udprogramacionporcomponentes02proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.udprogramacionporcomponentes02proyecto.screens.*
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController,startDestination = AppScreens.IndexScreen.router){
        composable(route = AppScreens.PlayersSettingsScreen.router){playersSettingsScreen(navController)}
        composable(route = AppScreens.GameScreen.router){gameScreen(navController)}
    }
}