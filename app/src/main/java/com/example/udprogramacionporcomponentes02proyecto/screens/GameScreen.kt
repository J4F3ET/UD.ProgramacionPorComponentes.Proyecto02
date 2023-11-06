package com.example.udprogramacionporcomponentes02proyecto.screens

import android.content.ClipData.Item
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.BottomBarGameScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.TopBarGameScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.GridCellJail
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.listPositionSecureToBoard
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePiece
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorIndexBackground
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorPlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.shadowColor
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController:NavController){
    Scaffold(
        topBar = { TopBarGameScreen() },
        bottomBar = { BottomBarGameScreen() },
        content = {
            BackGrounds(2)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                GameScreenContent(it)
            }

        }
    )
}

@Composable
fun GameScreenContent(padingValues: PaddingValues?){
    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp
    Box(
        contentAlignment = Alignment.Center
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 columnas\
            content = {
                item {
                    GridCellJail(color = ColorP.BLUE,maxWidthInDp,maxWidthInDp)
                }
                item{
                    GridCellsBoard(ColorP.BLUE,maxWidthInDp)
                }
                item {
                    GridCellJail(color = ColorP.YELLOW,maxWidthInDp,maxWidthInDp)
                }
                item{
                    GridCellsBoard(ColorP.RED,maxWidthInDp)
                }
                item{
                    GridCellsBoard(ColorP.RED,maxWidthInDp)
                }
                item{
                    GridCellsBoard(ColorP.YELLOW,maxWidthInDp)
                }
                item {
                    GridCellJail(color = ColorP.RED,maxWidthInDp,maxWidthInDp)
                }
                item{
                    GridCellsBoard(ColorP.GREEN,maxWidthInDp)
                }
                item {
                    GridCellJail(color = ColorP.GREEN,maxWidthInDp,maxWidthInDp)
                }
            }
        )
    }
}
@Composable
fun GridCellsBoard(color:ColorP,width: Dp){
    val listBoard = MutableList<BoardCell>(100){
            index -> BoardCell(index, mutableListOf(Piece(ColorP.BLUE,0,State.DANGER))) }
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
                    ColumnVerticalCellsBoard(color = color, width = width, boardList = listBoard)
                }
            }
        )
    }
}
@Composable
fun ColumnVerticalCellsBoard(color: ColorP,width: Dp,boardList: List<BoardCell>,){
    Box(modifier = Modifier
        .width(width)
        .height(width)){
        LazyColumn(
            content = {
                items(7) {
                    CellBoardMov(colorCell = color, width = width.div(7), positionBoardCell =boardList[it] )
                }
            }
        )
    }
}
@Composable
fun CellBoardMov(colorCell:ColorP,width: Dp,positionBoardCell: BoardCell){
    val colorBoardCell = if(listPositionSecureToBoard.contains(positionBoardCell.position)) {
        mapColorPlayer[colorCell]?:Color.Transparent
    }else{
        Color.Transparent
    }
    Box(
        modifier = Modifier
            .background(colorBoardCell, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .border(0.5.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .height(width)
            .width(width * 7),
        contentAlignment = Alignment.TopStart
    ){
        LazyHorizontalGrid(
            modifier = Modifier.fillMaxSize(),
            rows = GridCells.Fixed(1),
            horizontalArrangement =  Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            content ={
                item{
                    Text(
                        text = positionBoardCell.position.toString(),
                        color =Color.White,
                        fontFamily = FontFamily(Font(R.font.pixelify_sans_variable_font_wght, FontWeight.Normal)),
                        fontSize = 5.sp
                    )
                }
                items(positionBoardCell.pieces){piece->
                    GridCellPieces(color = piece.color, width = width)
                }
            }
        )
    }
}
@Composable
fun GridCellPieces(color: ColorP, width: Dp){
    Box(modifier = Modifier
        .background(Color.Transparent)
        .height(width)
        .width(width.div(2))
        .padding(1.dp)){
        Image(
            modifier = Modifier.background(Color.Transparent),
            painter = painterResource(mapColorImagePiece[color] ?: R.drawable.piece_blue),
            contentDescription = "Piezas en Ceda",
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview
@Composable
fun ColumnCelsBoardPreview(){
    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp
    val listBoard = MutableList<BoardCell>(100){
            index -> BoardCell(index, mutableListOf(Piece(ColorP.BLUE,0,State.DANGER))) }
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
                ColumnVerticalCellsBoard(ColorP.BLUE,maxWidthInDp,listBoard)
            }
        }
    }
}
//@Preview
//@Composable
//fun PreviewCellBoardMov(){
//    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp
//    val positionBoardCell = BoardCell(1, mutableListOf(Piece(ColorP.BLUE,0,State.DANGER)))
//    CellBoardMov(ColorP.BLUE,maxWidthInDp,positionBoardCell)
//}
@Preview
@Composable
fun GameScreenContentPreview(){
    GameScreenContent(null)
}
