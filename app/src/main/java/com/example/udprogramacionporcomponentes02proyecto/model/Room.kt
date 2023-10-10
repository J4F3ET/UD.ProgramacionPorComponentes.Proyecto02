package com.example.udprogramacionporcomponentes02proyecto.model

import android.app.GameState

data class Room(
    var key: String = "",
    val players: List<Player?>,
    val gameState: GameState?
)
