package com.example.udprogramacionporcomponentes02proyecto.model

data class Room(
    var key: String = "",
    val players: List<Player?>,
    val gameState: GameState
){
    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["key"] = key
        result["players"] = players.map { it?.toMap() }
        result["gameState"] = gameState.toMap() // Asumiendo que GameState también tiene un método toMap
        return result
    }
}
