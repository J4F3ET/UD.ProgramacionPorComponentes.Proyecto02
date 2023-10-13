package com.example.udprogramacionporcomponentes02proyecto.model

import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.State

data class Player(
    var uuid: String,
    var name: String,
    val color: ColorP,
    val pieces: List<Piece> = List(4) { Piece(color,0,State.JAIL) }
){
    fun toMap():HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["uuid"] = uuid
        result["name"] = name
        result["color"] = color.toString()
        result["pieces"] = pieces.map { it.toMap()}
        return result
    }
}
