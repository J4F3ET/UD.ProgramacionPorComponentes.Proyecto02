package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePiece
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.State
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.colorCell

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
                if (positionBoardCell.position < 68 ){
                    item{
                        Text(
                            text = positionBoardCell.position.toString(),
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.pixelify_sans_variable_font_wght, FontWeight.Normal)),
                            fontSize = 5.sp
                        )
                    }
                }
                items(positionBoardCell.pieces){piece->
                    GridCellPieces(
                        color = piece.color,
                        width = width.div(7f),
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
            verticalArrangement = Arrangement.Center,
            content ={
                if(positionBoardCell.position < 68){
                    item{
                        Text(
                            text = positionBoardCell.position.toString(),
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.pixelify_sans_variable_font_wght, FontWeight.Normal)),
                            fontSize = 5.sp
                        )
                    }
                }
                items(positionBoardCell.pieces.size){indexPiece->
                    GridCellPieces(
                        color = positionBoardCell.pieces[indexPiece].color,
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
fun GridCellPieces(color: ColorP, width: Dp, height: Dp, orientation:String){
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
        .padding(1.dp)){
        Image(
            modifier = Modifier.background(Color.Transparent).rotate(rotate),
            painter = painterResource(mapColorImagePiece[color] ?: R.drawable.piece_blue),
            contentDescription = "Piezas en Ceda",
            contentScale = ContentScale.Crop,
        )
    }
}

/*
@Preview
@Composable
fun ColumnCellsBoardPreview(){
    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp
    val listBoard = MutableList<BoardCell>(100){ index ->
        BoardCell(index, mutableListOf(
                Piece(ColorP.BLUE,0,State.DANGER),
                Piece(ColorP.RED,0,State.DANGER)
            )
        )
    }
    Box(
        modifier = Modifier
            .height(maxWidthInDp)
            .width(maxWidthInDp),
        contentAlignment = Alignment.Center
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ){
            item {
                ColumnVerticalCellsBoard(ColorP.BLUE,maxWidthInDp,listBoard,false)
            }
        }
    }
}
@Preview
@Composable
fun RowCelsBoardPreview(){
    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp
    val listBoard = MutableList<BoardCell>(100){ index ->
        BoardCell(index, mutableListOf(
            Piece(ColorP.BLUE,0,State.DANGER),
            Piece(ColorP.RED,0,State.DANGER)
        )
        )
    }
    Box(
        modifier = Modifier
            .height(maxWidthInDp)
            .width(maxWidthInDp),
        contentAlignment = Alignment.Center
    ){
        LazyHorizontalGrid(
            rows = GridCells.Fixed(3)
        ){
            item(3) {
                RowHorizontalCellsBoard(ColorP.BLUE,maxWidthInDp,listBoard,true)
            }
        }
    }
}
@Preview
@Composable
fun PreviewCellBoardMov(){
    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp
    val positionBoardCell = BoardCell(1, mutableListOf(Piece(ColorP.BLUE,0,State.DANGER)))
    CellBoardMov(ColorP.BLUE,maxWidthInDp,positionBoardCell)
}
 */