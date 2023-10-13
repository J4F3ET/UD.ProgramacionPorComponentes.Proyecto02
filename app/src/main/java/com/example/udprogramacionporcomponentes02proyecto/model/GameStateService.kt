package com.example.udprogramacionporcomponentes02proyecto.model

import android.util.Log
import com.example.udprogramacionporcomponentes02proyecto.util.RoomCurrent
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID

class GameStateService {
    private val database: DatabaseReference = Firebase.database("https://proyecto-1c57c-default-rtdb.firebaseio.com/").reference.child("gameStates")

    fun createGameState(): GameState{
        val gameState = UtilGame().initializationGame(UUID.randomUUID().toString())
        database.child(gameState.uuid).setValue(gameState)
        return gameState
    }
    fun updateGameState(){
        val gameStateMap = RoomCurrent.gameState.toMap()
        database.child(RoomCurrent.gameState.uuid).updateChildren(gameStateMap)
    }
    fun findGameState(uuid: String, callback: (GameState?) -> Unit) {
        database.child(uuid).get().addOnSuccessListener { dataSnapshot ->
            if(dataSnapshot.exists())
                callback(convertDataSnapshotToGameState(dataSnapshot))
            else
                callback(null)
        }.addOnFailureListener {
            callback(null)
        }
    }
    fun deleteGameState(uuid: String){
        database.child(uuid).removeValue()
    }
    private fun convertDataSnapshotToGameState(dataSnapshot: DataSnapshot):GameState?{
        val key = dataSnapshot.child("uuid").value.toString()
        val player = PlayerService().convertDataSnapshotToPlayer(dataSnapshot.child("currentPlayer"))
        val winner = PlayerService().convertDataSnapshotToPlayer(dataSnapshot.child("winner"))
        val board = mutableListOf<BoardCell>()
        for(cellData in dataSnapshot.child("board").children){
            val cell = cellData.child("board").getValue(BoardCell::class.java)
            if (cell != null) {
                board.add(cell)
            }
        }
        return player?.let { GameState(key, board = board, currentPlayer = it,winner) }
    }
}