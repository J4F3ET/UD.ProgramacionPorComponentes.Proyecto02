package com.example.udprogramacionporcomponentes02proyecto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.BottomBarGameScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.TopBarGameScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.CellBoardHorizontalMov
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.CellBoardVerticalMov
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.GridCellJail
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.GridCellPieces
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.GridHorizontalCellsBoard
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.GridVerticalCellsBoard
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePiece
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorPlayer
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.State
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.createNestedListToBoardCell


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
    //AQUI VA EL LISTENER DE LA BOARD

    val listBoard = MutableList<BoardCell>(100){
            index -> BoardCell(index, mutableListOf(/*Piece(ColorP.BLUE,0, State.DANGER)*/)) }
    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp
    Box(
        contentAlignment = Alignment.Center
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 columnas\
            content = {
                item {//Seccion #1X1 JAIL
                    GridCellJail(color = ColorP.BLUE,maxWidthInDp)
                }
                item{//Seccion #1X2 CELL
                    GridVerticalCellsBoard(createNestedListToBoardCell("1x2",listBoard),maxWidthInDp,false)
                }
                item {//Seccion #1X3 JAIL
                    GridCellJail(color = ColorP.YELLOW,maxWidthInDp)
                }
                item{//Seccion #2X1 CELL
                    GridHorizontalCellsBoard(createNestedListToBoardCell("2x1",listBoard),maxWidthInDp,false)
                }
                item{//Seccion #2X2 WIN
                    GridCellZoneWin(listBoard,maxWidthInDp)
                }
                item{//Seccion #2X3 CELL
                    GridHorizontalCellsBoard(createNestedListToBoardCell("2x3",listBoard),maxWidthInDp,true)
                }
                item {//Seccion #3X1 JAIL
                    GridCellJail(color = ColorP.RED,maxWidthInDp)
                }
                item{//Seccion #3X2 CELL
                    GridVerticalCellsBoard(createNestedListToBoardCell("3x2",listBoard),maxWidthInDp,true)
                }
                item {//Seccion #3X3 JAIL
                    GridCellJail(color = ColorP.GREEN,maxWidthInDp)
                }
            }
        )
    }
}
@Composable
fun GridCellZoneWin(board:List<BoardCell>,maxWidthInDp: Dp){
    Box(
        modifier = Modifier
            .height(maxWidthInDp)
            .width(maxWidthInDp),
        contentAlignment = Alignment.Center
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 columnas\
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            content = {
                val  width =maxWidthInDp.div(3f)
                item {//Seccion Winer #1X1 CELLS
                    CellsBoardInZoneWinner(width,Pair(board[59],board[58]))
                }
                item {//Seccion #1X2 CELL SECURE
                    CellZoneWinner( width,board[98])
                }
                item {//Seccion #1X3 CELLS
                    CellsBoardInZoneWinner(width,Pair(board[41],board[42]))
                }
                item {//Seccion #2X1 CELL SECURE
                    CellZoneWinner(width,board[74])
                }
                item {//Seccion #2X2 WINNER ZONE
                    CellWinner(width)
                }
                item {//Seccion #2X3 CELL SECURE
                    CellZoneWinner( width,board[90])
                }
                item {//Seccion Winer #3X1 CELLS
                    CellsBoardInZoneWinner(width,Pair(board[7],board[8]))
                }
                item {//Seccion #3X2 CELL SECURE
                    CellZoneWinner( width,board[82])
                }
                item {//Seccion #3X3 CELLS
                    CellsBoardInZoneWinner(width,Pair(board[25],board[24]))
                }
            }
        )
    }
}
@Composable
fun CellsBoardInZoneWinner(width: Dp,cells:Pair<BoardCell,BoardCell>){
    val configurationCells = when(cells.first.position){
        59 -> Pair(Pair("x","-y"),Alignment.TopStart)
        41 -> Pair(Pair("-x","-y"),Alignment.TopEnd)
        7 -> Pair(Pair("x","y"),Alignment.BottomStart)
        25 -> Pair(Pair("-x","y"),Alignment.BottomEnd)
        else -> Pair(Pair("x","y"),Alignment.BottomStart)
    }
    Box(
        contentAlignment = configurationCells.second
    ){
        Box(//Fist es la Columna y Second es la Row, se renderiza primero la columna y luego la fila
            modifier = Modifier
                .background(Color.Transparent)
                .height(width)
                .width(width.div(2))
                .border(0.5.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp)),
            contentAlignment = Alignment.Center
        ){
            CellBoardVerticalMov(width.div(2),width,configurationCells.first.first,cells.first)
        }
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .height(width.div(2))
                .width(width.times(1.4f))
                .border(0.5.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp)),
            contentAlignment = Alignment.Center
        ){
            CellBoardHorizontalMov(width.times(1.4f),width.div(2),configurationCells.first.second,cells.second)
        }
    }
}
@Composable
fun CellZoneWinner(area: Dp,cell: BoardCell){
    val mapOrientation = mapOf(
        82 to Pair(true,Alignment.BottomCenter),// True = Row
        90 to Pair(false,Alignment.CenterEnd),// False = Column
        98 to Pair(true,Alignment.TopCenter),
        74 to Pair(false,Alignment.CenterStart)
    )
    val width = if(mapOrientation[cell.position]?.first == true) area.times(1.3f) else area.div(2)
    val height = if(mapOrientation[cell.position]?.first == true) area.div(2) else area
    val orientationCell = when(cell.position){
        90 -> "-x"
        98 -> "-y"
        74 -> "x"
        else -> "y"
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .width(area)
            .height(area),
        contentAlignment = mapOrientation[cell.position]?.second ?: Alignment.Center
    ){
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .height(height)
                .width(width)
                .border(0.5.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp)),
            contentAlignment = Alignment.Center
        ){
            if(mapOrientation[cell.position]?.first == true)
                CellBoardHorizontalMov(width,height,orientationCell,cell)
            else
                CellBoardVerticalMov(width,height,orientationCell,cell)
        }
    }
}
@Composable
fun CellWinner(area: Dp){
//    val colorBoardCell = if(listPositionSecureToBoard.contains(positionBoardCell.position)) {
//        mapColorPlayer[colorCell]?: Color.Transparent
//    }else{
//        Color.Transparent
//    }
    Box(
        modifier = Modifier
            .border(0.5.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
            .height(area)
            .width(area),
        contentAlignment = Alignment.TopStart
    ){
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.portal),
                contentDescription = "background winner zone",
                contentScale = ContentScale.FillBounds
            )
        }
//        LazyHorizontalGrid(
//            modifier = Modifier.fillMaxSize(),
//            rows = GridCells.Fixed(1),
//            horizontalArrangement =  Arrangement.Center,
//            verticalArrangement = Arrangement.Center,
//            content ={
//                item{
//                    Text(
//                        text = positionBoardCell.position.toString(),
//                        color = Color.White,
//                        fontFamily = FontFamily(Font(R.font.pixelify_sans_variable_font_wght, FontWeight.Normal)),
//                        fontSize = 5.sp
//                    )
//                }
//                items(positionBoardCell.pieces){piece->
//                    GridCellPieces(
//                        color = piece.color,
//                        width = width.div(6.9f),
//                        height = width.div(3),
//                        orientation = orientationCell
//                    )
//                }
//            }
//        )
    }
}
@Preview
@Composable
fun ColumnCellsBoardPreview(){
    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp
    val listBoard = MutableList<BoardCell>(100){
            index -> BoardCell(index, mutableListOf(Piece(ColorP.BLUE,0, State.DANGER))) }
    GridCellZoneWin(listBoard,maxWidthInDp)
}
@Preview
@Composable
fun GameScreenContentPreview(){
    GameScreenContent(null)
}
