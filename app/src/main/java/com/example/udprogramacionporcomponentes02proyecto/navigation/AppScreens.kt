package com.example.udprogramacionporcomponentes02proyecto.navigation
sealed class AppScreens(val router: String){
    object IndexScreen:AppScreens("indexScreens")
    object PlayersSettingsScreen:AppScreens("playersSettingsScreens")
    object GameScreen:AppScreens("gameScreens")
}