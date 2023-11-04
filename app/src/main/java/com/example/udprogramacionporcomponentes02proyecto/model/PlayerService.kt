package com.example.udprogramacionporcomponentes02proyecto.model

import android.content.ContentValues
import android.util.Log
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.example.udprogramacionporcomponentes02proyecto.util.State
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID

class PlayerService {
    private var database: DatabaseReference = Firebase.database("https://proyecto-1c57c-default-rtdb.firebaseio.com/").reference.child("players")
    val playeristener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists() && dataSnapshot.child("uuid").exists()) {
                val player = convertDataSnapshotToPlayer(dataSnapshot)
                if (player != null) {
                    SessionCurrent.localPlayer = player
                } else {
                    Log.e("Error","No se pudo convertir dataSnapshot a Player")
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    fun getDatabaseChild(uuid:String):DatabaseReference{
        return database.child(uuid)
    }
    fun createPlayer(name:String,color: ColorP):Player{
        val player = Player(UUID.randomUUID().toString(),name,color)
        database.child(player.uuid).setValue(player)
        return player
    }
    fun createPlayer(uuid: String,name:String,color: ColorP):Player{
        val player = Player(uuid,name,color)
        database.child(player.uuid).setValue(player)
        return player
    }
    fun findPlayer(uuid: String, callback: (Player?) -> Unit) {
        val playerRef = database.child(uuid)
        playerRef.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.key != null && dataSnapshot.exists()) {
                val player = convertDataSnapshotToPlayer(dataSnapshot)
                callback(player)
            } else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }
    fun updatePlayer(uuid:String,player: Player){
        val playerMap = player.toMap()
        database.child(uuid).updateChildren(playerMap)
    }
    fun deletePlayer(uuid: String){
        database.child(uuid).removeValue()
    }
    fun convertDataSnapshotToPlayer(dataSnapshot: DataSnapshot):Player?{
        if (!dataSnapshot.child("uuid").exists()) return null
        val key = dataSnapshot.child("uuid").value.toString()
        val name = dataSnapshot.child("name").value.toString()
        val colorString = dataSnapshot.child("color").value as String // Obtiene la cadena de la base de datos
        val color = ColorP.valueOf(colorString) // Convierte la cadena en un valor del enum
        val pieces = mutableListOf<Piece>()
        for(pieceData in dataSnapshot.child("pieces").children){
            val statePiece = pieceData.child("state").value as String
            val stateP =State.valueOf(statePiece)
            val countStep: Int = pieceData.child("countStep").value.toString().toInt()
            pieces.add(Piece(color,countStep,stateP))
        }
        return  Player(key,name,color,pieces)
    }
}