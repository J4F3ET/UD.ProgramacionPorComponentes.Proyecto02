package com.example.udprogramacionporcomponentes02proyecto.util

import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealTimeManager {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Rooms")
    fun createRoom(room: Room){
        val key = databaseReference.push().key
        print("creando")
        if(key != null)
            databaseReference.child(key).setValue(room)
    }
    fun updateRoom(key: String, room: Room){
        databaseReference.child(key).setValue(room)
    }
    fun findRoom(pKey:String):Flow<Room>{
        val flow = callbackFlow<Room> {
            val listener = databaseReference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val rooms = snapshot.children.mapNotNull { snapshot ->
                        val room = snapshot.getValue(Room::class.java)
                        snapshot.key?.let { room?.copy(key = it) }
                    }
                    val room =
                    trySend((rooms.filter { it.key == pKey }).first()).isSuccess
                }
                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            })
            awaitClose{databaseReference.removeEventListener(listener)}
        }
        return flow
    }
}