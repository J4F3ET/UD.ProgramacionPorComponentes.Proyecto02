package com.example.udprogramacionporcomponentes02proyecto.model

import android.content.ContentValues.TAG
import android.util.Log
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.initializationGame
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID

class GameStateService {
    private val database: DatabaseReference = Firebase.database("https://proyecto-1c57c-default-rtdb.firebaseio.com/").reference.child("gameStates")
    fun getDatabaseChild(uuid:String):DatabaseReference{
        return database.child(uuid)
    }
    fun createGameState(): GameState{
        val gameState = initializationGame(UUID.randomUUID().toString())
        database.child(gameState.key).setValue(gameState)
        return gameState
    }
    fun updateGameState(){
        val gameStateMap = SessionCurrent.gameState.toMap()
        database.child(SessionCurrent.gameState.key).updateChildren(gameStateMap)
    }
    private fun findGameState(key: String, callback: (GameState?) -> Unit) {
        val gameStateRef = database.child(key)
        gameStateRef.get().addOnSuccessListener{ dataSnapshot ->
            if(dataSnapshot.exists()){
                val gameState =  convertDataSnapshotToGameState(dataSnapshot)
                callback(gameState)
            } else{
                callback(null)
            }

        }.addOnFailureListener {
            Log.i("ERROR","${gameStateRef.key}")
            callback(null)
        }
    }
    fun deleteGameState(uuid: String){
        database.child(uuid).removeValue()
    }
    fun convertDataSnapshotToGameState(dataSnapshot: DataSnapshot): GameState? {
        val key = dataSnapshot.key.toString()
        val player = PlayerService().convertDataSnapshotToPlayer(dataSnapshot.child("currentPlayer"))
            ?: return null
        val winner = dataSnapshot.child("winner").value.toString()
        val countPair:Int = dataSnapshot.child("countPair").value.toString().toInt()
        val board = mutableListOf<BoardCell>()
        for(cellData in dataSnapshot.child("board").children){
            val cell = cellData.child("board").getValue(BoardCell::class.java)
            if (cell != null) {
                board.add(cell)
            }
        }
        return  GameState(key, board = board, currentPlayer = player,countPair,winner)
    }
    fun initializeSessionGameState(key:String,callback: (GameState?) -> Unit){
        findGameState(key,callback)
    }
}