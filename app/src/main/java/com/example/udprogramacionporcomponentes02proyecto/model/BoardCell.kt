package com.example.udprogramacionporcomponentes02proyecto.model
data class BoardCell(
    val position: Int,
    val pieces: MutableList<Piece>,
){
    fun toMap():Map<String,Any>{
        val result = HashMap<String,Any>()
        result["position"]= this.position
         result["pieces"] = pieces.map { it?.toMap() }
        return result
    }
}
