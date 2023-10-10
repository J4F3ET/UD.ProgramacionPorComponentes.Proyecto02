package com.example.udprogramacionporcomponentes02proyecto.util

import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID

class RealTimeManager {
    private lateinit var database: DatabaseReference
    fun createRoom(room: Room, vararg players: Player):Room{
        val listPlayer = mutableListOf<Player>()
        players.forEach { listPlayer.add(it.copy())}
        val room = Room(UUID.randomUUID().toString(),listPlayer,,)
        database = Firebase.database.reference
        database.child("rooms").child(room.key).setValue(room)
        return room
    }
}