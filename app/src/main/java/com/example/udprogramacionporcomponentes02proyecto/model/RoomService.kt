package com.example.udprogramacionporcomponentes02proyecto.model

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.UUID

class RoomService {
    private val database: DatabaseReference = Firebase.database("https://proyecto-1c57c-default-rtdb.firebaseio.com/").reference.child("rooms")
    private val _rooms =  mutableStateOf(emptyList<Room>())
    val rooms: State<List<Room>> get() = _rooms
    val roomListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                SessionCurrent.roomGame  = convertDataSnapshotToRoom(dataSnapshot)
            }else {
                Log.e("Error","No se pudo convertir dataSnapshot a room")
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    private val roomsListener = object : ValueEventListener{
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val updatedRooms = mutableListOf <Room>()
                for (roomSnapshot in dataSnapshot.children) {
                    val room = convertDataSnapshotToRoom(roomSnapshot)
                    updatedRooms.add(room)
                }
                _rooms.value = updatedRooms
            }else{
                Log.e("Error","No se pudo convertir dataSnapshot a rooms")
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    init {
        database.addValueEventListener(roomsListener)
    }
    fun getDatabaseRooms():DatabaseReference = database
    fun getDatabaseChild(key:String):DatabaseReference = database.child(key)
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
    public fun convertDataSnapshotToRoom(dataSnapshot: DataSnapshot): Room {
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