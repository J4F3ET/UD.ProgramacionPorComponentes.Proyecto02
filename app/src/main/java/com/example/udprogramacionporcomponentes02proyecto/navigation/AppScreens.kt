package com.example.udprogramacionporcomponentes02proyecto.navigation
sealed class AppScreens(val router: String){
    object IndexScreen:AppScreens("indexScreen")
    object PlayersSettingsScreen:AppScreens("playersSettingsScreens")
    object GameScreen:AppScreens("gameScreens")
}