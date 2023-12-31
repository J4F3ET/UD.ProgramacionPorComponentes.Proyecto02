package com.example.udprogramacionporcomponentes02proyecto.model

import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.State

data class Piece(
    val color: ColorP,
    var countStep: Int,
    var state: State
){
    fun toMap(): HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["color"] = this.color.toString()
        result["countStep"] = this.countStep.toString()
        result["state"]= this.state.toString()
        return result
    }
}
