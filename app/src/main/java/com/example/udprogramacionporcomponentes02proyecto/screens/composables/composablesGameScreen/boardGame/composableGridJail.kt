package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.boardGame

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.model.CurrentThrow
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePiece
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorIndexBackground
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.example.udprogramacionporcomponentes02proyecto.util.State
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.addPieceToBoard
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.endShift
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.shouldEnableReleaseButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

@Composable
fun GridCellPiecesJail(piece: Piece, width: Dp){
    var currentThrow by remember {mutableStateOf(SessionCurrent.gameState.currentThrow)}
    var enable by remember {mutableStateOf(shouldEnableReleaseButton(currentThrow))}
    val updateCurrentThrow:(CurrentThrow?)->Unit = {
        if (it != null){
            SessionCurrent.gameState.currentThrow = it
            currentThrow = SessionCurrent.gameState.currentThrow
            enable = shouldEnableReleaseButton(currentThrow)
        }
    }
    val listGameStateValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                updateCurrentThrow(GameStateService().convertDataSnapshotToCurrentThrow(dataSnapshot.child("currentThrow")))
                if(currentThrow.checkMovDice.first && currentThrow.checkMovDice.second){
                    endShift()
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
    Box(modifier = Modifier
        .background(Color.Transparent)
        .height(width.div(4))
        .width(width.div(5))
        .padding(1.dp)
        .clickable(
            enabled = enable,
            onClickLabel = "Click me",
            onClick = {
                //AGREGAR CAMBIOS A LA ROOM PUES NO SE ESTA CAMBIANDO ESTADO DE LA PIECE A AGREFAR
                addPieceToBoard(piece.color)

            }
        )
    ){
        Image(
            modifier = Modifier.background(Color.Transparent),
            painter = painterResource(mapColorImagePiece[piece.color] ?: R.drawable.piece_blue),
            contentDescription = "Piezas en calcer",
            contentScale = ContentScale.Crop,
        )
    }
}
@Composable
fun GridCellJail(listPiece: List<Piece>?, color: ColorP, width: Dp){
    var listMutable = listPiece?.toMutableList()
    if(listMutable == null)
        listMutable = mutableListOf()
    else
        listMutable.removeAll{it.state!=State.JAIL }
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(1.dp, 1.dp, 1.dp, 1.dp))
            .fillMaxSize()
            .height(width)
            .width(width),
        contentAlignment = Alignment.Center
    ){
        BackGrounds(
            indexBg = mapColorIndexBackground[color]?:3,
            modifier = Modifier
                .align(Alignment.Center)
                .blur(13.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .background(Color.Transparent)
        ){
            if(listMutable.isEmpty())return
            LazyVerticalGrid(
                columns = GridCells.Fixed(if(listMutable.size >2)2 else 1),
                content = {
                    items(listMutable){
                        GridCellPiecesJail(it,width)
                    }
                }
            )
        }
    }
}