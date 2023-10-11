package com.example.udprogramacionporcomponentes02proyecto.util

import androidx.compose.ui.graphics.Color
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.UUID

class RealTimeManager {
    private lateinit var database: DatabaseReference
    fun createRoom(gameState: GameState, vararg players: Player):Room{
        val listPlayer = mutableListOf<Player>()
        players.forEach { listPlayer.add(it.copy())}
        val room = Room(UUID.randomUUID().toString(),listPlayer,gameState)
        database = Firebase.database.reference
        database.child("rooms").child(room.key).setValue(room)
        print(room.key)
        return room
    }
    fun findRoom(key: String, callback: (Room?) -> Unit) {
        val database = Firebase.database.reference
        val roomRef = database.child("rooms").child(key)
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
    fun updateRoom(key: String,room: Room){
        val database = Firebase.database.reference
        val roomRef = database.child("rooms").child(key)
        val roomMap = room.toMap()
        roomRef.updateChildren(roomMap)
    }
    fun createPlayer(name:String,color: ColorP):Player{
        val player = Player(UUID.randomUUID().toString(),name,color)
        val database = Firebase.database.reference
        database.child("players").child(player.uiid).setValue(player)
        return player
    }
    fun createPlayer(uuid: String,name:String,color: ColorP):Player{
        val player = Player(uuid,name,color)
        val database = Firebase.database.reference
        database.child("players").child(player.uiid).setValue(player)
        return player
    }
    fun findPlayer(uuid: String, callback: (Player?) -> Unit) {
        val database = Firebase.database.reference
        val roomRef = database.child("players").child(uuid)
        roomRef.get().addOnSuccessListener { dataSnapshot ->
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
    private fun convertDataSnapshotToRoom(dataSnapshot: DataSnapshot): Room? {
        val key = dataSnapshot.key
        val playersData = dataSnapshot.child("players")
        val gameStateData = dataSnapshot.child("gameState")

        // Verificar si el nodo "players" y "gameState" existen en el dataSnapshot
        if (!playersData.exists() || !gameStateData.exists()|| key == null) {
            return null // No se pudo convertir a Room si falta alguna de estas propiedades
        }

        // Obtener los valores de los nodos "players" y "gameState"
        val players = mutableListOf<Player>()
        for (playerSnapshot in playersData.children) {
            val player = playerSnapshot.getValue(Player::class.java)
            if (player != null) {
                players.add(player)
            }
        }
        val board = mutableListOf<BoardCell>()
        for (cellSnapshot in gameStateData.child("board").children) {
            val cell = cellSnapshot.getValue(BoardCell::class.java)
            if (cell != null){
                board.add(cell)
            }
        }

        val gameState = GameState(board,gameStateData.child("currentPlayer").getValue(Player::class.java),null)

        return Room(key, players, gameState)
    }
    private fun convertDataSnapshotToPlayer(dataSnapshot: DataSnapshot):Player?{
        val key = dataSnapshot.key
        val name = dataSnapshot.child("name").value.toString()
        val color = dataSnapshot.child("color").getValue(ColorP::class.java)
        val pieces = mutableListOf<Piece>()
        for(pieceData in dataSnapshot.child("pieces").children){
            val piece = pieceData.getValue(Piece::class.java)
            if (piece != null)pieces.add(piece)
        }
        return if (key != null && color != null) Player(key,name,color,pieces) else null
    }

}
