package com.example.udprogramacionporcomponentes02proyecto.util

import android.util.Log
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
        RoomCurrent.localPlayer = PlayerService().createPlayer("PruebaAntes", ColorP.BLUE)
        RoomCurrent.gameState = GameStateService().createGameState()
        RoomCurrent.roomGame = RoomService().createRoom(gameState.uuid, RoomCurrent.localPlayer)
        val player2 = Player(RoomCurrent.localPlayer.uuid, "PruebaDespues", ColorP.BLUE, RoomCurrent.localPlayer.pieces)
        PlayerService().updatePlayer(RoomCurrent.localPlayer.uuid, player2)
        RoomCurrent.roomGame.players.find {player -> player.uuid == player2.uuid }?.name = player2.name
        RoomService().updateRoom(roomGame.key, roomGame)
    }
    fun test2(){
        RoomService().deleteRoom(roomGame.key)
    }
}