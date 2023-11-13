package com.example.udprogramacionporcomponentes02proyecto.util


import android.util.Log
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
    fun calculateIndexBoardWinnerZone(piece: Piece,move: Int=0):Int{
        val especialCountStep = piece.countStep+move
        val ins = if(especialCountStep>79)especialCountStep-78 else especialCountStep-72
        val possibleIndex = if(ins>7) ins - 6 else ins
        val index = when(piece.color) {
            ColorP.BLUE -> 92
            ColorP.YELLOW -> 84
            ColorP.RED -> 68
            ColorP.GREEN -> 76
            else -> 0
        } + possibleIndex
        //78 coun step es el limite pues 79 es Win
        return index
    }
    fun calculateCountStepWinnerZone(piece: Piece,index: Int=0):Int{
        return 72 + index - when(piece.color) {
            ColorP.BLUE -> 92
            ColorP.YELLOW -> 84
            ColorP.RED -> 68
            ColorP.GREEN -> 76
            else -> 0
        }
    }
    fun resolvePieceState(index: Int):State{
        val listCellSecure = listOf(11, 16, 28, 33, 45, 50, 62, 67,55,38,4,21,68..74,76..82,84..90,92..98)
        val listCellWinner = listOf(75,83,91,99)
        if(listCellSecure.contains(index)) return State.SAFE
        if(listCellWinner.contains(index)) return State.WINNER
        return State.DANGER
    }
    companion object{
        private fun updateCheckReleasePieceDice(currentThrow: CurrentThrow):CurrentThrow{
            if(!(currentThrow.checkMovDice.first && currentThrow.checkMovDice.second) && currentThrow.dataToDices.first + currentThrow.dataToDices.second in 5..6){
                currentThrow.checkMovDice = Pair(true,true)
            }else{
                //Si el primer movimiento ya se efectuo y el segundo esta en 5..6 quiere dercir que el segundo es true
                if(currentThrow.dataToDices.second in 5..6){
                    currentThrow.checkMovDice = Pair(currentThrow.checkMovDice.first,true)
                }else{
                    currentThrow.checkMovDice = Pair(true,currentThrow.checkMovDice.second)
                }
            }
            return  currentThrow
        }
        fun shouldEnableReleaseButtonCellMov(currentThrow: CurrentThrow,piece:Piece):Boolean{
            //Verifica que se alla lanzado los dados
            if(!currentThrow.checkThrow)return false
            //Verifica que el jugador sea el autorizado correr ficha
            if(currentThrow.player != SessionCurrent.localPlayer) return false
            //Verificar el uso de los lanzamientos
            if(currentThrow.checkMovDice.first && currentThrow.checkMovDice.second) return false
            //Verifica estados de la pieza
            if(piece.state == State.JAIL || piece.state == State.WINNER) return false

            return true
        }
        fun shouldEnableReleaseButtonCellJail(currentThrow: CurrentThrow):Boolean{
            //RULE2("Comienzas el juego en la base y debes sacar un 5 para mover una ficha a la casilla de inicio."),
            //RULE5("Si sacas un 6, puedes sacar una ficha de la base o mover una ficha 6 casillas."),

            // Verifica que si el dado uno se uso y cumple con el 5 y 6
            if(!currentThrow.checkMovDice.first && (currentThrow.dataToDices.first in 5..6))
                return true
            // Verifica que si el dado dos se uso y cumple con el 5 y 6
            if(!currentThrow.checkMovDice.second && (currentThrow.dataToDices.second in 5..6))
                return true
            // La suma da 5 o 6 y no se han usado los lazamientos(2)
            if(
                !(currentThrow.checkMovDice.first && currentThrow.checkMovDice.second) &&
                currentThrow.dataToDices.first+currentThrow.dataToDices.second  in 5..6
                ) return true
            return false
        }
        fun shouldEnableReleaseButtonDice(currentThrow: CurrentThrow):Boolean{
            //Si el jugador es el correcto
            if(currentThrow.player == SessionCurrent.localPlayer){
                //Si no ha oprimido el boton
                if(!currentThrow.checkThrow){
                    //Si algunod e los dados esta en 0
                    if((currentThrow.dataToDices.first == 0 || currentThrow.dataToDices.second == 0)&& !currentThrow.checkMovDice.first&& !currentThrow.checkMovDice.first){
                        return true
                    }
                }
            }
            return false

        }
        fun resolveUpdateDiceToGameState(currentThrow:CurrentThrow):Boolean{
            if(
                !currentThrow.checkMovDice.first && //Si no se registra movimiento 1
                !currentThrow.checkMovDice.second &&//Si no se registra movimiento 2
                currentThrow.dataToDices.first != 0 && // Si el dado uno es diferente a 0
                currentThrow.dataToDices.second != 0 // Si el dado dos es diferente a 0
            ){
                return true
            }
            return false
        }
        fun endShift(gameState: GameState){
            if(!gameState.currentThrow.checkMovDice.first || !gameState.currentThrow.checkMovDice.second) return
            gameState.board.forEach{
                UtilGame().verifyCellEliminatePiece(it)
            }
            gameState.currentThrow = CurrentThrow(
                calculateCurrentPlayer(SessionCurrent.roomGame.players,gameState.currentThrow)
            )
            SessionCurrent.gameState = gameState
            GameStateService().updateGameState()
        }
        fun addPieceToBoard(currentThrow:CurrentThrow):CurrentThrow{
            //Funcion solo es posible sis e habilita la salida de celdas de la carcel
            //shouldEnableReleaseButtonCellJail
            val newPiece = updateStateJailToSafe()
            val indexNewPieceToBoard = UtilGame().calculateIndexBoard(newPiece)
            SessionCurrent.gameState.board[indexNewPieceToBoard].pieces.add(newPiece)
            return updateCheckReleasePieceDice(currentThrow)
        }
        private fun updatePieceInRoom(oldPiece:Piece, newPiece:Piece):Boolean{
            SessionCurrent.roomGame.players.first {player ->
                player.color == SessionCurrent.gameState.currentThrow.player.color
            }.let{player ->
                if(player.pieces.remove(oldPiece)){
                    player.pieces.add(newPiece)
                    return true
                }
            }
            return false
        }
        fun movPieceToBoard(oldPiece: Piece,currentThrow: CurrentThrow):CurrentThrow{
            val mov = if(!currentThrow.checkMovDice.first){
                currentThrow.dataToDices.first
            } else if(!currentThrow.checkMovDice.second){
                currentThrow.dataToDices.second
            } else {
                Log.e("movPieceToBoard","Movimiento 0")
                0
            }
            val oldIndexToBoardCell = if(oldPiece.countStep < 71)UtilGame().calculateIndexBoard(oldPiece) else UtilGame().calculateIndexBoardWinnerZone(oldPiece)
            val newIndexToBoardCell = if(oldPiece.countStep+mov < 71)UtilGame().calculateIndexBoard(oldPiece,mov) else UtilGame().calculateIndexBoardWinnerZone(oldPiece,mov)

            if(!SessionCurrent.gameState.board[oldIndexToBoardCell].pieces.remove(oldPiece)) return currentThrow

            val newPiece = Piece(
                oldPiece.color,
                if(oldPiece.countStep + mov < 71) oldPiece.countStep + mov else UtilGame().calculateCountStepWinnerZone(oldPiece,newIndexToBoardCell),
                UtilGame().resolvePieceState(newIndexToBoardCell)
            )
            if(!updatePieceInRoom(oldPiece,newPiece))return currentThrow
            SessionCurrent.gameState.board[newIndexToBoardCell].pieces.add(newPiece)
            currentThrow.checkMovDice = if(!currentThrow.checkMovDice.first)
                Pair(true,currentThrow.checkMovDice.second)
            else
                Pair(currentThrow.checkMovDice.first,true)
            return currentThrow
        }
        private fun updateStateJailToSafe():Piece {
            //Estos cambios son el la Room
            // Encuentra al jugador actual
            val currentPlayer = SessionCurrent.roomGame.players.first { player ->
                player.color == SessionCurrent.gameState.currentThrow.player.color }
            // Si el jugador actual existe, actualiza el estado de la pieza
            currentPlayer.let { player ->
                player.pieces.first { pieceFind -> pieceFind.state == State.JAIL }.let {
                    it.countStep = 0
                    it.state = State.SAFE
                    return it
                }
            }
        }
        private fun calculateCurrentPlayer(listToPlayers:List<Player>, currentThrow: CurrentThrow): Player {
            var nextPlayer: Player = currentThrow.player
            if(currentThrow.dataToDices.first == currentThrow.dataToDices.second){
                return nextPlayer
            }
            if (listToPlayers.last().color == currentThrow.player.color) {
                nextPlayer = listToPlayers.first()
            }else{
                val nextPlayerIndex = listToPlayers.indexOfFirst{it.color == currentThrow.player.color
                } + 1
                if(listToPlayers[nextPlayerIndex].color != currentThrow.player.color){
                    nextPlayer = listToPlayers[nextPlayerIndex]
                }
            }
            return nextPlayer
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
        fun finishEndShift(currentThrow: CurrentThrow):Boolean{
            if(currentThrow.dataToDices.first == 0 || currentThrow.dataToDices.second == 0 ){
                return !shouldEnableReleaseButtonDice(currentThrow)
            }
            if(!currentThrow.checkMovDice.first || !currentThrow.checkMovDice.second)return false
            if(!currentThrow.checkThrow)return false
            var buttonMovEnable = false
            currentThrow.player.pieces.forEach{
                if(shouldEnableReleaseButtonCellMov(currentThrow,it) ){
                    buttonMovEnable = true
                    return@forEach
                }
            }

            val buttonJailEnable = shouldEnableReleaseButtonCellJail(currentThrow)
            val unmovingPiece = currentThrow.player.pieces.firstOrNull{
                it.state == State.DANGER || it.state == State.SAFE
            } == null

            // Tengo piezas inmoviles y bonoes de dichas piesas listos para usar, por ende no debo de cambiar de turno
            if(unmovingPiece && buttonJailEnable)return false

            // Tengo movimientos
            if(buttonMovEnable) return false

            // No pude hacer ningun movimiento entonces retorne true
            currentThrow.checkMovDice = Pair(true,true)
            return true
        }
    }
}

