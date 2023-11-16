package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesPlayerSettingsScreen

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.model.RoomService
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppScreens
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun DialogFindRoom(navController: NavController, onDismiss: () -> Unit){
    val rooms by RoomService().rooms
    var roomData by remember { mutableStateOf<Room?>(null) }
    var findRoomResult by remember { mutableStateOf(true) }
    var isDialogWaitVisible by remember { mutableStateOf(false) }
    val findRoom:(subKey:String) -> Unit= { subKey ->
        roomData = rooms.firstOrNull { it.key.substring(0, 5) == subKey }
        if(roomData == null) findRoomResult = false
        isDialogWaitVisible = findRoomResult
    }
    if ((roomData != null) && isDialogWaitVisible) {
        RoomService().addPlayerToRoom(roomData!!.key, SessionCurrent.localPlayer)
        DialogWaitGame(
            navController,
            roomData!!,
            onDismiss = {
                RoomService().removePlayerToRoom(roomData!!.key, SessionCurrent.localPlayer)
                isDialogWaitVisible = false
            }
        )
    }
    DialogFindRoomContent(findRoomResult, findRoom,onDismiss)
}
@Composable
fun DialogFindRoomContent(findRoomResult:Boolean,findRoom:(subKey:String) -> Unit,onDismiss: () -> Unit){
    var subKeyText by remember { mutableStateOf(TextFieldValue()) }
    AlertDialog(
        title = { TextPixel("Buscar sala", textStylePixel(Color.White, Color.White,30)) },
        text = {
            if(!findRoomResult){
                TextPixel("No se encontro ninguna sala con el ID: ${subKeyText.text}", textStylePixel(
                    Color.White, Color.Red, 20)
                )
            }else{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    TextPixel("ID: ", textStylePixel(Color.White, Color.DarkGray, 20))
                    Spacer(modifier = Modifier.width(8.dp)) // Agrega un espacio entre el texto y el campo de texto
                    BasicTextField(
                        textStyle = textStylePixel(),
                        modifier = Modifier.border(1.dp, Color.White, RoundedCornerShape(0.dp, 8.dp, 0.dp, 8.dp)).weight(1f),
                        value = subKeyText,
                        onValueChange = {subKeyText = it},
                    )
                }
            }
        },
        confirmButton = {
            if(findRoomResult){
                TextButton(
                    onClick = { findRoom(subKeyText.text) },
                    content = { TextPixel("Buscar", textStylePixel(Color.White, Color.DarkGray,20)) }
                )
            }
            TextButton(
                onClick = { onDismiss() },
                content = { TextPixel("Cerrar", textStylePixel(Color.White, Color.DarkGray,20)) }
            )
        },
        onDismissRequest = { onDismiss() },
        containerColor = Color(7,12,19)
    )
}
@Composable
fun DialogWaitGame(navController: NavController, room: Room, onDismiss: () -> Unit){
    var roomData by remember { mutableStateOf(room) }
    val findGameState:(GameState?)-> Unit = { gameState ->
        if(gameState != null){
            SessionCurrent.gameState = gameState
            navController.navigate(route = AppScreens.WaitingScreen.router)
        }
    }
    val roomValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                roomData = RoomService().convertDataSnapshotToRoom(dataSnapshot)
                SessionCurrent.roomGame = roomData
                if(roomData.players.size == 4 && SessionCurrent.roomGame.gameStateKey == ""){
                    findGameState(GameStateService().createGameState())
                    RoomService().addGameStateToRoom()
                }else if(SessionCurrent.roomGame.gameStateKey != "" || (SessionCurrent.roomGame.players.size == 4 && SessionCurrent.roomGame.gameStateKey != "")){
                    GameStateService().initializeSessionGameState(SessionCurrent.roomGame.gameStateKey,findGameState)
                }
            } else {
                Log.e("Error", "No se pudo convertir dataSnapshot a room")
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    RoomService().getDatabaseChild(room.key).addValueEventListener(roomValueEventListener)
    DialogWaitGameContent(roomData,onDismiss,findGameState)
}
@Composable
fun DialogWaitGameContent(roomData:Room, onDismiss: () -> Unit,initializerGameState: (GameState?) ->Unit) {
    AlertDialog(
        title = {TextPixel(roomData.key.substring(0,5),textStylePixel(Color.White,Color.White,30))},
        text = {TextPixel(messageDialogWait(roomData), textStylePixel(Color.White,Color.DarkGray,20))},
        confirmButton = {
            if (roomData.players.size > 1){
                TextButton(
                    onClick = {
                        initializerGameState(GameStateService().createGameState())
                        RoomService().addGameStateToRoom()
                    },
                    content = {TextPixel("Comenzar", textStylePixel(Color.White,Color.DarkGray,20))}
                )
            } else {
                TextButton(
                    onClick = {onDismiss()},
                    content = {TextPixel("Cerrar", textStylePixel(Color.White,Color.DarkGray,20)) }
                )
            }
        },
        onDismissRequest = { onDismiss() },
        containerColor = Color(7,12,19)
    )
}
fun messageDialogWait(room:Room):String{
    var message = "JUGADORES: ${room.players.size} de 4\n\n"
    message += if (room.players.size > 1){
        "si desea iniciar la partida precione el boton 'Comenza'"
    }else{
        "Esperando mas jugadores para poder comenzar"
    }+"\n\n"
    message += if(room.gameStateKey != ""){
        "ESTADO: Iniciado a espera que entres"
    }else{
        "ESTADO: Esperando"
    }
    return message
}