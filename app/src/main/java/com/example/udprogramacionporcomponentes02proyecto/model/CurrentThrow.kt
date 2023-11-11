package com.example.udprogramacionporcomponentes02proyecto.model

data class CurrentThrow(
    var player: Player,
    var checkThrow: Boolean = false,
    var checkMovDice: Pair<Boolean,Boolean> = Pair(false,false),
    var dataToDices: Pair<Int,Int> = Pair(0,0)
){
    fun toMap(): HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["player"] = this.player
        result["checkThrow"] = this.checkThrow
        result["checkMovDice"] = this.checkMovDice
        result["dataToDices"] = this.dataToDices
        return result
    }
}
