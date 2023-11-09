package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell

@Composable
fun GridCellZoneWin(board:List<BoardCell>, maxWidthInDp: Dp){
    val indicesSubList = board.mapNotNull {
            cell -> if (cell.position in listOf(99, 91, 75, 83)) cell else null
    }
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
                    CellWinner(width,indicesSubList)
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
fun CellsBoardInZoneWinner(width: Dp, cells:Pair<BoardCell, BoardCell>){
    val configurationCells = when(cells.first.position){
        59 -> Pair(Pair("x","-y"), Alignment.TopStart)
        41 -> Pair(Pair("-x","-y"), Alignment.TopEnd)
        7 -> Pair(Pair("x","y"), Alignment.BottomStart)
        25 -> Pair(Pair("-x","y"), Alignment.BottomEnd)
        else -> Pair(Pair("x","y"), Alignment.BottomStart)
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
            CellBoardHorizontalMov(width.times(2.5f),width.div(2),configurationCells.first.second,cells.second)
        }
    }
}
@Composable
fun CellZoneWinner(area: Dp, cell: BoardCell){
    val mapOrientation = mapOf(
        82 to Pair(true, Alignment.BottomCenter),// True = Row
        90 to Pair(false, Alignment.CenterEnd),// False = Column
        98 to Pair(true, Alignment.TopCenter),
        74 to Pair(false, Alignment.CenterStart)
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
                CellBoardHorizontalMov(width.times(2),height,orientationCell,cell)
            else
                CellBoardVerticalMov(width,height,orientationCell,cell)
        }
    }
}
@Composable
fun CellWinner(area: Dp, listBordCell:List<BoardCell>){
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
        LazyHorizontalGrid(
            modifier = Modifier.fillMaxSize(),
            rows = GridCells.Fixed(listBordCell.size),
            horizontalArrangement =  Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            content ={
                for (cell in listBordCell){
                    items(cell.pieces){piece->
                        GridCellPieces(
                            color = piece.color,
                            width = area.div(4f),
                            height = area.div(2),
                            orientation = "y"
                        )
                    }
                }

            }
        )
    }

}