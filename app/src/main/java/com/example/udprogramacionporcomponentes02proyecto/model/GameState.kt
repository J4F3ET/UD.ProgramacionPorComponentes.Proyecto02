package com.example.udprogramacionporcomponentes02proyecto.model

data class GameState(
    var key: String,
    var board: MutableList<BoardCell>,
    var currentPlayer: Player,
    var winner: String = "",
){
    fun toMap(): HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["key"] = this.key
        result["board"] = this.board.map { it.toMap() }
        result["currentPlayer"] = this.currentPlayer.toMap()
        result["winner"] = winner
        return result
    }

}
