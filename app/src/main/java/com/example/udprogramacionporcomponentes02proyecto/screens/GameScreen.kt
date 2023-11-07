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
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.GridCellJail
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.GridCellPieces
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.GridHorizontalCellsBoard
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.GridVerticalCellsBoard
import com.example.udprogramacionporcomponentes02proyecto.screens.util.listPositionSecureToBoard
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePiece
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorPlayer
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
                item {//Seccion #1X1 JAIL
                    GridCellJail(color = ColorP.BLUE,maxWidthInDp)
                }
                item{//Seccion #1X2 CELL
                    GridVerticalCellsBoard(ColorP.BLUE,maxWidthInDp,false)
                }
                item {//Seccion #1X3 JAIL
                    GridCellJail(color = ColorP.YELLOW,maxWidthInDp)
                }
                item{//Seccion #2X1 CELL
                    GridHorizontalCellsBoard(ColorP.RED,maxWidthInDp,false)
                }
                item{//Seccion #2X2 WIN
                    GridCellZoneWin(ColorP.YELLOW,maxWidthInDp)
                }
                item{//Seccion #2X3 CELL
                    GridHorizontalCellsBoard(ColorP.YELLOW,maxWidthInDp,true)
                }
                item {//Seccion #3X1 JAIL
                    GridCellJail(color = ColorP.RED,maxWidthInDp)
                }
                item{//Seccion #3X2 CELL
                    GridVerticalCellsBoard(ColorP.GREEN,maxWidthInDp,true)
                }
                item {//Seccion #3X3 JAIL
                    GridCellJail(color = ColorP.GREEN,maxWidthInDp)
                }
            }
        )
    }
}
@Composable
fun GridCellZoneWin(color: ColorP, maxWidthInDp: Dp){
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
                    CellsBoardInZoneWinner(width,3)
                }
                item {//Seccion #1X2 CELL SECURE
                    CellZoneWinner( width,2)
                }
                item {//Seccion #1X3 CELLS
                    CellsBoardInZoneWinner(width,2)
                }
                item {//Seccion #2X1 CELL SECURE
                    CellZoneWinner(width,3)
                }
                item {//Seccion #2X2 WINNER ZONE
                    CellWinner(width)
                }
                item {//Seccion #2X3 CELL SECURE
                    CellZoneWinner( width,1)
                }
                item {//Seccion Winer #3X1 CELLS
                    CellsBoardInZoneWinner(width,0)
                }
                item {//Seccion #3X2 CELL SECURE
                    CellZoneWinner( width,0)
                }
                item {//Seccion #3X3 CELLS
                    CellsBoardInZoneWinner(width,1)
                }
            }
        )
    }
}
@Composable
fun CellsBoardInZoneWinner(width: Dp,cornerIndex:Int){
    val listRotation = listOf(
        Pair(Pair(90f,180f),Alignment.BottomStart),
        Pair(Pair(0f,90f),Alignment.BottomEnd),
        Pair(Pair(270f,0f),Alignment.TopEnd),
        Pair(Pair(180f,270f),Alignment.TopStart)
    )
    Box(
        contentAlignment = listRotation[cornerIndex].second
    ){
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .height(width)
                .width(width.div(2))
                .border(0.25.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
                .padding(1.dp),
            contentAlignment = Alignment.Center
        ){
        }
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .height(width.div(2))
                .width(width)
                .border(0.25.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
                .padding(1.dp),
            contentAlignment = Alignment.Center
        ){
        }
    }
}
@Composable
fun CellZoneWinner(area: Dp,cellIndex:Int){
    val orientation = listOf(
        Pair(true,Alignment.BottomCenter),// True = Row
        Pair(false,Alignment.CenterEnd),// False = Column
        Pair(true,Alignment.TopCenter),
        Pair(false,Alignment.CenterStart)
    )
    Box(
        modifier = Modifier
            .width(area)
            .height(area),
        contentAlignment = orientation[cellIndex].second
    ){
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .height(if (orientation[cellIndex].first) area.div(2) else area)
                .width(if (orientation[cellIndex].first) area else area.div(2))
                .border(0.3.dp, Color.White, RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
                .padding(1.dp),
            contentAlignment = Alignment.Center
        ){

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
        Box(modifier = Modifier.fillMaxSize(1f)){
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
    GridCellZoneWin(ColorP.BLUE,maxWidthInDp)
}
@Preview
@Composable
fun GameScreenContentPreview(){
    GameScreenContent(null)
}
