package com.example.udprogramacionporcomponentes02proyecto.model

import android.content.ContentValues
import android.util.Log
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID

class RoomService {
    private val database: DatabaseReference = Firebase.database("https://proyecto-1c57c-default-rtdb.firebaseio.com/").reference.child("rooms")
    val RoomListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val room = convertDataSnapshotToRoom(dataSnapshot)
                if (room != null) {
                    SessionCurrent.roomGame = room
                } else {
                    Log.e("Error","No se pudo convertir dataSnapshot a room")
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    fun getDatabaseChild(key:String):DatabaseReference{
        return database.child(key)
    }
    fun createRoom(gameStateKey: String, vararg players: Player){
        val listPlayer = mutableListOf<Player>()
        players.forEach { listPlayer.add(it.copy())}
        SessionCurrent.roomGame = Room(UUID.randomUUID().toString(),listPlayer,gameStateKey)
        database.child(SessionCurrent.roomGame.key).setValue(SessionCurrent.roomGame)
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