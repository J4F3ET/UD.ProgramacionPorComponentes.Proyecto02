package com.example.udprogramacionporcomponentes02proyecto.util

import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.PlayerService
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.model.RoomService

object RoomCurrent {
    lateinit var roomGame: Room
    lateinit var localPlayer: Player
    lateinit var gameState: GameState
    fun test(){
        RoomCurrent.localPlayer = PlayerService().createPlayer("PRUEBA", ColorP.BLUE)
        RoomCurrent.gameState = GameStateService().createGameState()
        RoomService().createRoom(gameState.uuid, RoomCurrent.localPlayer)
        RoomService().updateRoom(roomGame.key, roomGame)
    }
    fun test2(){
        RoomService().deleteRoom(roomGame.key)
    }
}