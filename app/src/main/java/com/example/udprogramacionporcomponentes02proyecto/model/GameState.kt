package com.example.udprogramacionporcomponentes02proyecto.model

data class GameState(
    var board: List<BoardCell>,
    var currentPlayer: Player,
    var winner: Player?,
)
