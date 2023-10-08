package com.example.udprogramacionporcomponentes02proyecto.model

import android.app.GameState
import com.example.udprogramacionporcomponentes02proyecto.util.Player

data class Room(
    val key: String?= null,
    val players: List<Player?>?,
    val gameState: GameState?

)
