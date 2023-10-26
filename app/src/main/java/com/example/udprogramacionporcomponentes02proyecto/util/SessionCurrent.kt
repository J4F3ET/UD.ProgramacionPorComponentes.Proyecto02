package com.example.udprogramacionporcomponentes02proyecto.util

import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.Room

object SessionCurrent {
    lateinit var roomGame: Room
    lateinit var localPlayer: Player
    lateinit var gameState: GameState
}