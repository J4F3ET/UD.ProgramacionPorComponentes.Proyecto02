package com.example.udprogramacionporcomponentes02proyecto.model

import com.example.udprogramacionporcomponentes02proyecto.util.RoomCurrent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID

class RoomService {
    private val database: DatabaseReference = Firebase.database("https://proyecto-1c57c-default-rtdb.firebaseio.com/").reference.child("rooms")

    fun createRoom(gameStateKey: String, vararg players: Player){
        val listPlayer = mutableListOf<Player>()
        players.forEach { listPlayer.add(it.copy())}
        RoomCurrent.roomGame = Room(UUID.randomUUID().toString(),listPlayer,gameStateKey)
        database.child(RoomCurrent.roomGame.key).setValue(RoomCurrent.roomGame)
    }
    fun findRoom(key: String, callback: (Room?) -> Unit) {
        val roomRef = database.child(key)
        roomRef.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.key != null && dataSnapshot.exists()) {
                val room = convertDataSnapshotToRoom(dataSnapshot)
                callback(room)
            } else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }
    fun updateRoom(key:String,room:Room){
        val roomMap = room.toMap()
        database.child(key).updateChildren(roomMap)
    }
    fun deleteRoom(key:String){
        database.child(key).removeValue()
    }
    private fun convertDataSnapshotToRoom(dataSnapshot: DataSnapshot): Room {
        val key = dataSnapshot.key.toString()
        val playersData = dataSnapshot.child("players")
        val gameStateData = dataSnapshot.child("gameStateKey").value.toString()
        val players = mutableListOf<Player>()
        for (playerSnapshot in playersData.children) {
            val player = PlayerService().convertDataSnapshotToPlayer(playerSnapshot)
            if (player != null) {
                players.add(player)
            }
        }
        return Room(key, players, gameStateData)
    }
}