package com.example.udprogramacionporcomponentes02proyecto.util

import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.Piece

class UtilGame {
    fun initializationGame():GameState = GameState(List<BoardCell>(100){ index -> BoardCell(index, mutableListOf()) },null,null)
}