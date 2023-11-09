package com.example.udprogramacionporcomponentes02proyecto.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.BottomBarGameScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.TopBarGameScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame.GridCellJail
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame.GridCellZoneWin
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame.GridHorizontalCellsBoard
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame.GridVerticalCellsBoard
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.State
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.createNestedListToBoardCell

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                GameScreenContent()
            }

        }
    )
}

@Composable
fun GameScreenContent(){
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
@Preview
@Composable
fun ColumnCellsBoardPreview(){
    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp
    val listBoard = MutableList<BoardCell>(100){index ->
        BoardCell(
            index,
            mutableListOf(
                Piece(ColorP.BLUE,0, State.DANGER),
                Piece(ColorP.GREEN,0, State.DANGER),
                Piece(ColorP.RED,0, State.DANGER),
                Piece(ColorP.YELLOW,0, State.DANGER)
            )
        )
    }
    GridCellZoneWin(listBoard,maxWidthInDp)
}
@Preview
@Composable
fun GameScreenContentPreview(){
    GameScreenContent()
}
