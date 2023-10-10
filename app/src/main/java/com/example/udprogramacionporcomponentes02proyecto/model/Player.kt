package com.example.udprogramacionporcomponentes02proyecto.model

import androidx.compose.ui.graphics.Color

data class Player(
    val uiid: String,
    val name: String,
    val color: Color,
    val pieces: List<Piece> = List(4) { Piece(color) }
)
