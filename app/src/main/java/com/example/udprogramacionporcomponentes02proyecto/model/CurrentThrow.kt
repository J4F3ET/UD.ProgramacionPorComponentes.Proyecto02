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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrentThrow

        if (player != other.player) return false
        if (checkThrow != other.checkThrow) return false
        if (checkMovDice != other.checkMovDice) return false
        if (dataToDices != other.dataToDices) return false

        return true
    }

    override fun hashCode(): Int {
        var result = player.hashCode()
        result = 31 * result + checkThrow.hashCode()
        result = 31 * result + checkMovDice.hashCode()
        result = 31 * result + dataToDices.hashCode()
        return result
    }
}
