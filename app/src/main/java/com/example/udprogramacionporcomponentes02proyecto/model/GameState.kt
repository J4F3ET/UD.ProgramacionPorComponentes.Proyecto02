package com.example.udprogramacionporcomponentes02proyecto.model

data class GameState(
    var key: String,
    var board: MutableList<BoardCell>,
    var currentThrow:CurrentThrow,
    var countPair:Int = 0,
    var winner: String = "",
){
    fun toMap(): HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["key"] = this.key
        result["board"] = this.board.map { it.toMap() }
        result["currentThrow"] = this.currentThrow.toMap()
        result["countPair"] = this.countPair
        result["winner"] = winner
        return result
    }

}
