package com.example.udprogramacionporcomponentes02proyecto.model

data class GameState(
    var board: List<BoardCell>,
    var currentPlayer: Player?,
    var winner: Player?,
){
    fun toMap(): Map<String,Any>{
        val result = HashMap<String,Any>()
        result["board"] = this.board.map { it?.toMap() }
        result["currentPlayer"] = this.currentPlayer!!.toMap()
        result["winner"] = this.winner!!.toMap()
        return result

    }
}
