package com.example.udprogramacionporcomponentes02proyecto.model

import androidx.compose.ui.graphics.Color
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.State

data class Player(
    val uiid: String,
    val name: String,
    val color: ColorP,
    val pieces: List<Piece> = List(4) { Piece(color,0,State.JAIL) }
){
    fun toMap():Map<String,Any>{
        val result = HashMap<String,Any>()
        result["uiid"] = uiid
        result["name"] = name
        result["color"] = color.toString()
        result["pieces"] = pieces.map { it.toMap()}
        return result
    }
}
