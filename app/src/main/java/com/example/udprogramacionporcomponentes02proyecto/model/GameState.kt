package com.example.udprogramacionporcomponentes02proyecto.model

data class GameState(
    var uuid: String,
    var board: MutableList<BoardCell>,
    var currentPlayer: Player,
    var winner: Player?,
){
    fun toMap(): HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["uuid"] = this.uuid
        result["board"] = this.board.map { it.toMap() }
        result["currentPlayer"] = this.currentPlayer.toMap()
        result["winner"] = winner?.toMap() ?: Any()
        return result
    }

}
