package com.example.udprogramacionporcomponentes02proyecto.util

import android.util.Log
import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.PlayerService
import com.example.udprogramacionporcomponentes02proyecto.model.Room

object SessionCurrent {
    lateinit var roomGame: Room
    lateinit var localPlayer: Player
    lateinit var gameState: GameState
    fun test(){
        localPlayer = PlayerService().createPlayer("Prueba",ColorP.BLUE)
        gameState.currentPlayer = localPlayer
        GameStateService().updateGameState()
    }
}