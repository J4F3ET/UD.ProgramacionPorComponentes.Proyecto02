package com.example.udprogramacionporcomponentes02proyecto.util


import androidx.compose.ui.graphics.Color
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.CurrentThrow
import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.PlayerService
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.model.RoomService
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorPlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorSecureZone

class UtilGame {
    fun diceThrow(currentThrow: Pair<Int,Int>){
        //

        // ACTUALIZA EL JUGADOR ACTUAL

    }
    fun verifyCellEliminatePiece(cell: BoardCell):BoardCell{
        if(cell.pieces.size <= 1)return cell
        val lastColor = cell.pieces.last().color
        //takeIf verifica que no este vacia la List,
        cell.pieces.takeIf {it.isNotEmpty() }?.let{
            //despues remueve las pieces que no posean el color de la piece final
            it.removeAll{piece -> piece.color != lastColor }
        }
        return cell
    }

    fun calculateIndexBoard(piece: Piece,move:Int=0):Int{
        val possibleIndex = when(piece.color) {
            ColorP.BLUE -> 55
            ColorP.YELLOW -> 38
            ColorP.RED -> 4
            ColorP.GREEN -> 21
            else -> 0
        } + piece.countStep+move
        //68 es 67 = numero total de celdas recorribles + 1 ya que necesito que el valor de desde 0 a 67
        return if(possibleIndex>67) possibleIndex-68 else possibleIndex
    }

    companion object{
        fun updateCheckMovDice(currentThrow: CurrentThrow,mov:Int):CurrentThrow{
            if(currentThrow.checkMovDice.first && currentThrow.checkMovDice.second) return  currentThrow

            if(currentThrow.checkMovDice.first)
                currentThrow.checkMovDice = Pair(true,true)
            else
                currentThrow.checkMovDice = Pair(true,false)

            return currentThrow
        }
        fun shouldEnableReleaseButton(currentThrow: CurrentThrow):Boolean{
            if(!currentThrow.checkThrow)return false
            // Si ya se uso el 5 o el 6
            if(
                (currentThrow.dataToDices.first in 5..6 && currentThrow.checkMovDice.first)||
                (currentThrow.dataToDices.second in 5..6 && currentThrow.checkMovDice.second)
                ) return false
            //RULE2("Comienzas el juego en la base y debes sacar un 5 para mover una ficha a la casilla de inicio."),
            if(currentThrow.dataToDices.first + currentThrow.dataToDices.second == 5) return true
            //RULE5("Si sacas un 6, puedes sacar una ficha de la base o mover una ficha 6 casillas."),
            if(currentThrow.dataToDices.first in 5..6 || currentThrow.dataToDices.second in 5..6) return true
            return false
        }
         fun endShift(){
            SessionCurrent.gameState.board.forEach{
                UtilGame().verifyCellEliminatePiece(it)
            }
            SessionCurrent.gameState.currentThrow.player = calculateCurrentPlayer(
                SessionCurrent.roomGame.players,
                SessionCurrent.gameState.currentThrow.player,
                SessionCurrent.gameState.currentThrow.dataToDices
            )
            SessionCurrent.gameState.currentThrow.checkMovDice = Pair(false,false)
            SessionCurrent.gameState.currentThrow.checkThrow = false
            SessionCurrent.gameState.currentThrow.dataToDices = Pair(0,0)
            GameStateService().updateGameState()
        }
        fun addPieceToBoard(color:ColorP){
            if(SessionCurrent.gameState.currentThrow.checkMovDice.first && SessionCurrent.gameState.currentThrow.checkMovDice.second) return
            val dice1 = SessionCurrent.gameState.currentThrow.dataToDices.first
            val dice2 = SessionCurrent.gameState.currentThrow.dataToDices.second
            val sum = dice1  +  dice2
            if(sum in 5..6){
                SessionCurrent.gameState.currentThrow = updateCheckMovDice(SessionCurrent.gameState.currentThrow,dice1)
                SessionCurrent.gameState.currentThrow = updateCheckMovDice(SessionCurrent.gameState.currentThrow,dice2)
            }
            val newPiece = Piece(color,0,State.SAFE)
            SessionCurrent.gameState.board[UtilGame().calculateIndexBoard(newPiece)].pieces.add(newPiece)
            SessionCurrent.gameState.currentThrow = updateCheckMovDice(SessionCurrent.gameState.currentThrow,if(!SessionCurrent.gameState.currentThrow.checkMovDice.first && dice1 in 5..6) dice1 else dice2)
            updateStateJailToSafe(newPiece)
            RoomService().updateRoom()
            GameStateService().updateGameState()
        }
        fun updateStateJailToSafe(piece: Piece) {
            // Encuentra al jugador actual
            val currentPlayer = SessionCurrent.roomGame.players.find { player ->
                player.uuid == SessionCurrent.gameState.currentThrow.player.uuid &&
                        player.uuid == SessionCurrent.localPlayer.uuid
            }
            // Si el jugador actual existe, actualiza el estado de la pieza
            currentPlayer?.let { player ->
                val pieceIndex = player.pieces.indexOfFirst { pieceFind -> pieceFind.state == State.JAIL }
                player.pieces[pieceIndex] = piece
            }
        }

        fun calculateCurrentPlayer(listToPlayers:List<Player>, oldPlayer: Player, currentThrow: Pair<Int,Int>): Player {
            if(currentThrow.first == currentThrow.second) return oldPlayer
            val index = listToPlayers.indexOf(oldPlayer)
            if (index+1 == listToPlayers.size) return listToPlayers[0]
            return listToPlayers[index+1]
        }
        fun initializationGame(uuid:String):GameState = GameState(
            uuid,
            MutableList(100){index -> BoardCell(index, mutableListOf())},
            CurrentThrow(SessionCurrent.localPlayer)
        )
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
            val grid2x1 = listOf(//GLOBAL GRID 2X1 HORIZONTAL
                (60..66).toList().reversed(),
                (67..73).toList(),
                (0..6).toList()
            )
            val grid3x2 = listOf(//GLOBAL GRID 3X2 VERTICAL
                (9..15).toList().reversed(),
                listOf(16) + (76..83).toList(),
                (17..23).toList()
            )
            val grid2x3 = listOf(//GLOBAL GRID 2X3 HORIZONTAL
                (34..40).toList(),
                listOf(33) + (84..91).toList() ,
                (26..32).toList().reversed()
            )
            val grid1x2 = listOf(//GLOBAL GRID 1X2 VERTICAL
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

