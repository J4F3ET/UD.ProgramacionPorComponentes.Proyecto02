package com.example.udprogramacionporcomponentes02proyecto.model

import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.State

data class Player(
    var uuid: String,
    var name: String,
    val color: ColorP,
    val confirms: Pair<Boolean,Boolean> = Pair(false,false),
    val pieces: List<Piece> = List(4) { Piece(color,0,State.JAIL) }
){
    fun toMap():HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["uuid"] = uuid
        result["name"] = name
        result["color"] = color.toString()
        result["confirms"] = confirms
        result["pieces"] = pieces.map { it.toMap()}
        return result
    }
}
