package com.example.udprogramacionporcomponentes02proyecto.model

import androidx.compose.ui.graphics.Color
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.State

data class Piece(
    val color: ColorP,
    var countStep: Int,
    var state: State
){
    fun toMap(): Map<String,Any>{
        val result = HashMap<String,Any>()
        result["color"] = this.color.toString()
        result["countStep"]= this.countStep
        result["state"]= this.state.toString()
        return result
    }
}
