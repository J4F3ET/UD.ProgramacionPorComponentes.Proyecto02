package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.CurrentThrow
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.model.RoomService
import com.example.udprogramacionporcomponentes02proyecto.screens.util.Numbers
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePiece
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.colorCell
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.movPieceToBoard
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.shouldEnableReleaseButtonCellMov
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

@Composable
fun GridVerticalCellsBoard(listColumns:List<List<BoardCell>>, width: Dp, directionInvert:Boolean){
    Box(
        modifier = Modifier
            .height(width)
            .width(width),
        contentAlignment = Alignment.Center
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            content = {
                items(3){
                    ColumnVerticalCellsBoard(
                        width = width,
                        boardList = listColumns[it],
                        directionInvert = directionInvert
                    )
                }
            }
        )
    }
}
@Composable
fun GridHorizontalCellsBoard(listRows:List<List<BoardCell>>, width: Dp, directionInvert:Boolean){
    Box(
        modifier = Modifier
            .height(width)
            .width(width),
        contentAlignment = Alignment.Center
    ){
        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            content = {
                items(3){
                    RowHorizontalCellsBoard(
                        width = width,
                        boardList = listRows[it],
                        directionInvert = directionInvert
                    )
                }
            }
        )
    }
}
@Composable
fun RowHorizontalCellsBoard(width: Dp, boardList: List<BoardCell>, directionInvert:Boolean){
    val orientationCell = if(directionInvert)"-x" else "x"
    Box(modifier = Modifier
        .fillMaxSize()
        .width(width.times(1.08f))
        .height(width)){
        LazyRow(
            reverseLayout = directionInvert,
            content = {
                items(7) {
                    CellBoardVerticalMov(
                        width = width.times(0.155f),//El valor sale del pierde de valores, de 1.08 osea un 8% y del divido en 7, la cantidad de celdas 1.08/7 = 0.154...
                        height = width,
                        orientationCell = orientationCell,
                        positionBoardCell =boardList[it]
                    )
                }
            }
        )
    }
}
@Composable
fun ColumnVerticalCellsBoard(width: Dp, boardList: List<BoardCell>, directionInvert:Boolean){
    val orientationCell = if(directionInvert)"y" else "-y"
    Box(modifier = Modifier
        .width(width)
        .height(width)){
        LazyColumn(
            reverseLayout = directionInvert,
            content = {
                items(7) {
                    CellBoardHorizontalMov(
                        width = width,
                        height =  width.div(7),
                        orientationCell = orientationCell,
                        positionBoardCell =boardList[it] )
                }
            }
        )
    }
}
@Composable
fun CellBoardHorizontalMov(width: Dp, height: Dp, orientationCell:String, positionBoardCell: BoardCell){
    val colorBoardCell = colorCell(positionBoardCell.position)
    Box(
        modifier = Modifier
            .background(colorBoardCell, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .border(0.5.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .height(height)
            .width(width),
        contentAlignment = Alignment.TopStart
    ){
        LazyHorizontalGrid(
            modifier = Modifier.fillMaxSize(),
            rows = GridCells.Fixed(1),
            horizontalArrangement =  Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            content ={
                if (Numbers){
                    item{
                        Text(
                            text = positionBoardCell.position.toString(),
                            color = Color.White,
                            fontSize = 8.sp
                        )
                    }
                }
                items(positionBoardCell.pieces){piece->
                    GridCellPieces(
                        piece = piece,
                        width = width.div(7),
                        height = width.div(3),
                        orientation = orientationCell
                    )
                }
            }
        )
    }
}
@Composable
fun CellBoardVerticalMov(width: Dp, height: Dp, orientationCell:String, positionBoardCell: BoardCell){
    val colorBoardCell = colorCell(positionBoardCell.position)
    Box(
        modifier = Modifier
            .background(colorBoardCell, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .border(0.55.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .height(height)
            .width(width),
        contentAlignment = Alignment.TopStart
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment =  Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            content ={
                if(Numbers){
                    item{
                        Text(
                            text = positionBoardCell.position.toString(),
                            color = Color.White,
                            fontSize = 8.sp
                        )
                    }
                }
                items(positionBoardCell.pieces.size){indexPiece->
                    GridCellPieces(
                        piece = positionBoardCell.pieces[indexPiece],
                        width = width,
                        height = width,
                        orientation = orientationCell
                    )
                }
            }
        )
    }
}
@Composable
fun GridCellPieces(piece: Piece, width: Dp, height: Dp, orientation:String){
    var currentThrow by remember {mutableStateOf(SessionCurrent.gameState.currentThrow)}
    var enable by remember {mutableStateOf(shouldEnableReleaseButtonCellMov(SessionCurrent.gameState.currentThrow,piece))}
    val listGameStateValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                GameStateService().convertDataSnapshotToCurrentThrow(dataSnapshot.child("currentThrow"))?.let{
                    currentThrow = it
                    enable = shouldEnableReleaseButtonCellMov(it,piece)
                }
            } else {
                Log.e("Error", "No se pudo convertir dataSnapshot a list<BoadCell>")
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    GameStateService().getDatabaseChild(SessionCurrent.gameState.key).addValueEventListener(listGameStateValueEventListener)
    val rotate = when(orientation){
        "x" -> 90f
        "-x" -> 270f
        "y" -> 0f
        "-y" -> 180f
        else -> 0f
    }
    Box(modifier = Modifier
        .background(Color.Transparent)
        .height(height)
        .width(width)
        .padding(1.dp)
        .clickable(
            enabled = enable,
            onClickLabel = "Click me",
            onClick = {
                currentThrow = movPieceToBoard(piece,currentThrow)
                if(currentThrow != SessionCurrent.gameState.currentThrow){
                    SessionCurrent.gameState.currentThrow = currentThrow
                    RoomService().updateRoom(SessionCurrent.roomGame.key,SessionCurrent.roomGame)
                    GameStateService().updateGameState()
                }
            }
        )) {
        Image(
            modifier = Modifier
                .background(Color.Transparent)
                .rotate(rotate),
            painter = painterResource(mapColorImagePiece[piece.color] ?: R.drawable.piece_blue),
            contentDescription = "Piezas en Ceda",
            contentScale = ContentScale.Crop,
        )
    }
}
