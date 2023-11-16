package com.example.udprogramacionporcomponentes02proyecto.model

import android.util.Log
import com.example.udprogramacionporcomponentes02proyecto.screens.util.convertMapToPairBooleanBoolean
import com.example.udprogramacionporcomponentes02proyecto.screens.util.convertMapToPairIntInt
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.example.udprogramacionporcomponentes02proyecto.util.State
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
    fun updateGameState(key: String,gameState: GameState){
        val gameStateMap = gameState.toMap()
        database.child(key).updateChildren(gameStateMap)
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
    fun convertDataSnapshotToCurrentThrow(dataSnapshot: DataSnapshot):CurrentThrow?{
        val player = PlayerService().convertDataSnapshotToPlayer(dataSnapshot.child("player"))
            ?: return null
        val checkThrow = dataSnapshot.child("checkThrow").value as Boolean
        val checkMovDice = convertMapToPairBooleanBoolean(dataSnapshot.child("checkMovDice").value as Map<*,*>)
        val dataToDices = convertMapToPairIntInt(dataSnapshot.child("dataToDices").value as Map<*,*>)
        return CurrentThrow(player,checkThrow,checkMovDice,dataToDices)
    }
    fun convertDataSnapshotToListBoardCell(dataSnapshot:DataSnapshot):MutableList<BoardCell>{
        val list = mutableListOf<BoardCell>()
        for(cellData in dataSnapshot.children){
            val position = cellData.child("position").value.toString().toInt()
            val pieces = mutableListOf<Piece>()
            for(pieceData in cellData.child("pieces").children){
                val color = ColorP.valueOf(pieceData.child("color").value.toString()) // Convierte la cadena en un valor del enum
                val statePiece = State.valueOf(pieceData.child("state").value.toString())
                val countStep: Int = pieceData.child("countStep").value.toString().toInt()
                pieces.add(Piece(color,countStep,statePiece))
            }
            list.add(BoardCell(position,pieces))
        }
        return list
    }
    fun convertDataSnapshotToGameState(dataSnapshot: DataSnapshot): GameState? {
        val key = dataSnapshot.key.toString()
        val currentThrow = convertDataSnapshotToCurrentThrow(dataSnapshot.child("currentThrow"))
            ?: return null
        val winner = dataSnapshot.child("winner").value.toString()
        val countPair:Int = dataSnapshot.child("countPair").value.toString().toInt()
        val board = convertDataSnapshotToListBoardCell(dataSnapshot.child("board"))
        return  GameState(key, board = board, currentThrow = currentThrow,countPair,winner)
    }
    fun initializeSessionGameState(key:String,callback: (GameState?) -> Unit){
        findGameState(key,callback)
    }
}