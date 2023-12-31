package com.example.udprogramacionporcomponentes02proyecto.model

data class Room(
    val key: String,
    var players: MutableList<Player>,
    var gameStateKey: String
){
    fun toMap(): HashMap<String, Any> {
        val result = HashMap<String, Any>()
        result["key"] = key
        result["players"] = players.map{ it.toMap() }
        result["gameStateKey"] = gameStateKey
        return result
    }
}
