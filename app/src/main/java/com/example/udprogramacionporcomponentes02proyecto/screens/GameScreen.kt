package com.example.udprogramacionporcomponentes02proyecto.screens

import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.model.BoardCell
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.model.RoomService
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.BottomBarGameScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.TopBarGameScreen
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame.GridCellJail
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame.GridCellZoneWin
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame.GridHorizontalCellsBoard
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame.GridVerticalCellsBoard
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.createNestedListToBoardCell
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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
    //VARIABLE DE STADO POR SECCION
    var roomData by remember { mutableStateOf(SessionCurrent.roomGame.players) }
    var listBoard by remember { mutableStateOf(SessionCurrent.gameState.board) }
    val list1x1 by remember { mutableStateOf(SessionCurrent.roomGame.players.find{ player ->  player.color == ColorP.BLUE}?.pieces) }
    var list1x2 by remember {mutableStateOf(createNestedListToBoardCell("1x2",listBoard))}
    val list1x3 by remember { mutableStateOf(SessionCurrent.roomGame.players.find{ player ->  player.color == ColorP.YELLOW}?.pieces) }
    var list2x1 by remember {mutableStateOf(createNestedListToBoardCell("2x1",listBoard))}
    var list2x3 by remember {mutableStateOf(createNestedListToBoardCell("2x3",listBoard))}
    val list3x1 by remember { mutableStateOf(SessionCurrent.roomGame.players.find{ player ->  player.color == ColorP.RED}?.pieces) }
    var list3x2 by remember {mutableStateOf(createNestedListToBoardCell("3x2",listBoard))}
    val list3x3 by remember { mutableStateOf(SessionCurrent.roomGame.players.find{ player ->  player.color == ColorP.GREEN}?.pieces) }
    //AQUI VA LOS CALLBACK
    //Callback CellMov
    val updateListGameState:(MutableList<BoardCell>?)->Unit={
        if(it !=null){
            listBoard = it
            val newList1x2 = createNestedListToBoardCell("1x2",listBoard)
            val newList2x1 = createNestedListToBoardCell("2x1",listBoard)
            val newList2x3 = createNestedListToBoardCell("2x3",listBoard)
            val newList3x2 = createNestedListToBoardCell("3x2",listBoard)
            if(list1x2.flatten() != newList1x2.flatten()){
                list1x2 = newList1x2
                Log.i("GameScreenContent","Update Section 1x2")
            }
            if(list2x1.flatten() != newList2x1.flatten()){
                list2x1 = newList2x1
                Log.i("GameScreenContent","Update Section 2x1")
            }
            if(list2x3.flatten() != newList2x3.flatten()){
                list2x3 = newList2x3
                Log.i("GameScreenContent","Update Section 2x3")
            }
            if(list3x2.flatten() != newList3x2.flatten()){
                list3x2 = newList3x2
                Log.i("GameScreenContent","Update Section 3x2")
            }
        }
    }
    //AQUI VA EL LISTENER DE LOS ROOM PLAYER
    val roomValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                roomData = RoomService().convertDataSnapshotToRoom(dataSnapshot).players
                SessionCurrent.roomGame.players = roomData
            } else {
                Log.e("Error", "No se pudo convertir dataSnapshot a room")
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    RoomService().getDatabaseChild(SessionCurrent.roomGame.key).addValueEventListener(roomValueEventListener)
    //AQUI VA EL LISTENER DE LA BOARD
    val listGameStateValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val board = GameStateService().convertDataSnapshotToListBoardCell(dataSnapshot.child("board"))
                updateListGameState(board)
            } else {
                Log.e("Error", "No se pudo convertir dataSnapshot a list<BoadCell>")
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    GameStateService().getDatabaseChild(SessionCurrent.gameState.key).addValueEventListener(listGameStateValueEventListener)

    val maxWidthInDp = (LocalContext.current.resources.displayMetrics.widthPixels.div(9)).dp

    Box(
        contentAlignment = Alignment.Center
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 columnas\
            content = {
                item {//Seccion #1X1 JAIL
                    GridCellJail(list1x1,ColorP.BLUE,maxWidthInDp)
                }
                item{//Seccion #1X2 CELL
                    GridVerticalCellsBoard(list1x2,maxWidthInDp,false)
                }
                item {//Seccion #1X3 JAIL
                    GridCellJail(list1x3,ColorP.YELLOW,maxWidthInDp)
                }
                item{//Seccion #2X1 CELL
                    GridHorizontalCellsBoard(list2x1,maxWidthInDp,false)
                }
                item{//Seccion #2X2 WIN
                    GridCellZoneWin(listBoard,maxWidthInDp)
                }
                item{//Seccion #2X3 CELL
                    GridHorizontalCellsBoard(list2x3,maxWidthInDp,true)
                }
                item {//Seccion #3X1 JAIL
                    GridCellJail(list3x1,ColorP.RED,maxWidthInDp)
                }
                item{//Seccion #3X2 CELL
                    GridVerticalCellsBoard(list3x2,maxWidthInDp,true)
                }
                item {//Seccion #3X3 JAIL
                    GridCellJail(list3x3,ColorP.GREEN,maxWidthInDp)
                }
            }
        )
    }
}
