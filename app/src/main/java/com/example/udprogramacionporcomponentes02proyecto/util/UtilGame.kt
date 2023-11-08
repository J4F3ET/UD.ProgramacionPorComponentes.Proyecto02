package com.example.udprogramacionporcomponentes02proyecto.util


import androidx.compose.ui.graphics.Color
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.PlayerService
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorPlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorSecureZone

class UtilGame {
    companion object{
        fun initializationGame(uuid:String):GameState = GameState(uuid,MutableList<BoardCell>(75){
                index -> BoardCell(index, mutableListOf()) },SessionCurrent.localPlayer)
        fun checkPlayerColor(player: Player, room: Room): Player {
            // Saca la lista de colores de los jugadores actuales de la room
            val listColorsCurrentRoom = room.players.map { playerRoom -> playerRoom.color }

            //Verifica que el color de player no este en esa lista, si no esta, retorna jugador
            if(listColorsCurrentRoom.none { color -> color == player.color })return player

            //Devuelve todos los colores registrados de ColorP con el formato de lista
            val allColors = ColorP.values().toList()

            // Extrae todos los colores que no esten en la sala
            val colorsNotExitsTheRoom = allColors.filterNot { color -> listColorsCurrentRoom.contains(color) }

            SessionCurrent.localPlayer = Player(player.uuid,player.name,colorsNotExitsTheRoom[0])
            PlayerService().updatePlayer(SessionCurrent.localPlayer.uuid,SessionCurrent.localPlayer)

            return  SessionCurrent.localPlayer
        }
        fun colorCell(index:Int): Color {
            var colorP = ColorP.TRANSPARENT
            for((key,value)in mapColorSecureZone){
                if(value.contains(index))
                    colorP = key
            }
            return mapColorPlayer[colorP]?:Color.Transparent
        }
        fun createNestedListToBoardCell(locationGrid:String,board:List<BoardCell>):List<List<BoardCell>>{
            val grid2x1 = listOf(//GRID 2X1 HORIZONTAL
                (60..66).toList().reversed(),
                (67..73).toList(),
                (0..6).toList()
            )
            val grid3x2 = listOf(//GRID 3X2 VERTICAL
                (9..15).toList().reversed(),
                listOf(16) + (76..83).toList(),
                (17..23).toList()
            )
            val grid2x3 = listOf(//GRID 2X3 HORIZONTAL
                (34..40).toList(),
                listOf(33) + (84..91).toList() ,
                (26..32).toList().reversed()
            )
            val grid1x2 = listOf(//GRID 1X2 VERTICAL
                (51..57).toList(),
                listOf(50)+(92..99).toList(),
                (43..49).toList().reversed()
            )
            val gridSelect = when(locationGrid){
                "1x2" -> grid1x2
                "2x3" -> grid2x3
                "3x2" -> grid3x2
                else -> grid2x1
            }
            val listNestedToCells = mutableListOf<List<BoardCell>>()
            for(list in gridSelect){
                val listCellSelected = list.map { indexCell -> board[indexCell] }
                listNestedToCells.add(listCellSelected)
            }
            return listNestedToCells
        }
    }

}

