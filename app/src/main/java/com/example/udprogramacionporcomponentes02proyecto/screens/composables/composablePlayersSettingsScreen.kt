package com.example.udprogramacionporcomponentes02proyecto.screens.composables

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.model.RoomService
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppScreens
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.messageRule
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.util.RulesGame
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPlayersSettingsScreen(){
    var isDialogVisible by remember { mutableStateOf(false) }
    if (isDialogVisible) {
        Dialog(
            onDismiss = { isDialogVisible = false }
        )
    }
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(7,12,19)),
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Column {
                    TextPixel(text = "Escoja una sala", Color.White,false)
                }
            }
        },
        actions = {
            IconButton(onClick = { isDialogVisible = true}) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
            }
        }
    )
}
@Composable
fun BottomBarPlayersSettingsScreen(navController: NavController){
    var isDialogFindRoomVisible by remember { mutableStateOf(false) }
    var isDialogWaitVisible by remember { mutableStateOf(false) }
    if (isDialogFindRoomVisible) {
        DialogFindRoom(
            navController,
            onDismiss = {
                isDialogFindRoomVisible = false
            }
        )
    }
    if (isDialogWaitVisible){
        DialogWait(navController = navController, room = SessionCurrent.roomGame) {
            RoomService().removePlayerToRoom(SessionCurrent.roomGame.key,SessionCurrent.localPlayer)
            isDialogWaitVisible = false
        }
    }
    BottomAppBar(
        containerColor = Color.Transparent/*Color(7,12,19)*/,
        tonalElevation = 10.dp,
        contentPadding = PaddingValues(10.dp,0.dp)
    ){
        ElevatedButton(
            onClick = {isDialogFindRoomVisible = true},
            content = { TextPixel(text = "Buscar", color = Color.White, boolean = true) },
            colors = ButtonDefaults.buttonColors(
                Color.Transparent, Color.Black, Color.Black, Color.Black),
            border = BorderStroke(2.dp, Color.White)
        )
        Spacer(modifier = Modifier.width(10.dp))
        ElevatedButton(
            onClick =
            {
                RoomService().createRoom("",SessionCurrent.localPlayer)
                isDialogWaitVisible =true
            },
            content = { TextPixel(text = "Crear", color = Color.White, boolean = true) },
            colors = ButtonDefaults.buttonColors(
                Color.Transparent, Color.Black, Color.Black, Color.Black),
            border = BorderStroke(2.dp, Color.White)
        )
    }
}
@Composable
fun ListRooms(navController: NavController) {
    val rooms by RoomService().rooms
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(),
        contentPadding = PaddingValues(0.dp,70.dp),
        userScrollEnabled= true,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        items(rooms.size) { index ->
            if (rooms[index].players.size < 4){
                ItemRoom(room = rooms[index],navController)
            }
        }
    }
}
@Composable
fun ItemRoom(room: Room,navController: NavController){
    var isDialogVisible by remember { mutableStateOf(false) }
    if (isDialogVisible) {
        DialogWait(
            navController,
            room,
            onDismiss = {
                RoomService().removePlayerToRoom(room.key,SessionCurrent.localPlayer)
                isDialogVisible = false
            }
        )
    }
    Row(
        modifier = Modifier
            .border(1.dp, Color.DarkGray, RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(7, 12, 19),
                        Color.Transparent,
                        Color(7, 12, 19)
                    )
                )
            )
            .clickable {
                RoomService().addPlayerToRoom(room.key, SessionCurrent.localPlayer)
                isDialogVisible = true
            }
            .fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Center
    ){
        TextPixel(
            text = "ID: ${room.key.substring(0,5)}    JUGADORES: ${room.players.size} DE 4",
            textStyle = textStylePixel(Color.White,Color.Black,23)
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
}
@Composable
fun Dialog(onDismiss: () -> Unit){
    var currentRuleIndex by remember { mutableIntStateOf(0) }
    val rulesList = RulesGame.values()
    val (dialogTitle, dialogText) = messageRule(currentRuleIndex, rulesList)
    AlertDialog(
        title = {
            TextPixel(dialogTitle, textStylePixel(Color.White,Color.White,30))
        },
        text = {
            TextPixel(dialogText, textStylePixel(Color.White,Color.DarkGray,20))
        },
        confirmButton = {
            if (currentRuleIndex < rulesList.size - 1) {
                TextButton(
                    onClick = { currentRuleIndex++ },
                ) {
                    TextPixel("Siguiente", textStylePixel(Color.White,Color.DarkGray,20))
                }
            } else {
                TextButton(
                    onClick = { onDismiss() },
                ) {
                    TextPixel("Cerrar", textStylePixel(Color.White,Color.DarkGray,20))
                }
            }
        },
        onDismissRequest = { onDismiss() },
        containerColor = Color(7,12,19)
    )
}
@Composable
fun DialogWait(navController: NavController,room: Room,onDismiss: () -> Unit){
    var roomData by remember { mutableStateOf(room) }
    val roomValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                roomData = RoomService().convertDataSnapshotToRoom(dataSnapshot)
                SessionCurrent.roomGame = roomData
                if(roomData.players.size == 4){
                    navController.navigate(route = AppScreens.GameScreen.router)
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
    AlertDialogWait(roomData,navController,onDismiss)
}
@Composable
fun AlertDialogWait(roomData:Room,navController: NavController,onDismiss: () -> Unit) {
    AlertDialog(
        title = {
            TextPixel(roomData.key.substring(0,5), textStylePixel(Color.White,Color.White,30))
        },
        text = {
            TextPixel(messageDialogWait(roomData), textStylePixel(Color.White,Color.DarkGray,20)
            )
        },
        confirmButton = {
            if (roomData.players.size > 1){
                TextButton(
                    onClick = {
                        if (roomData.gameStateKey != "") {
                            navController.navigate(route = AppScreens.GameScreen.router)
                        }else{
                            SessionCurrent.gameState = GameStateService().createGameState()
                            RoomService().addGameStateToRoom()
                            navController.navigate(route = AppScreens.GameScreen.router)
                        }
                    },
                ) {
                    TextPixel("Comenzar", textStylePixel(Color.White,Color.DarkGray,20))
                }
            } else {
                TextButton(
                    onClick = {
                        onDismiss()
                    },
                ) {
                    TextPixel("Cerrar", textStylePixel(Color.White,Color.DarkGray,20))
                }
            }
        },
        onDismissRequest = { onDismiss() },
        containerColor = Color(7,12,19)
    )
}
@Composable
fun DialogFindRoom(navController: NavController,onDismiss: () -> Unit){
    val rooms by RoomService().rooms
    var roomData by remember { mutableStateOf<Room?>(null) }
    var subKeyText by remember { mutableStateOf(TextFieldValue()) }
    var isSubKeyEntered by remember { mutableStateOf(false) }
    var findRoomResult by remember { mutableStateOf(true) }
    var isDialogWaitVisible by remember { mutableStateOf(false) }
    val findRoom:(subKey:String) -> Unit= { subKey ->
        roomData = rooms.firstOrNull { it.key.substring(0, 5) == subKey }
        if(roomData == null) findRoomResult = false
        isDialogWaitVisible = findRoomResult
    }
    if ((roomData != null) && isDialogWaitVisible) {
        RoomService().addPlayerToRoom(roomData!!.key, SessionCurrent.localPlayer)
        DialogWait(
            navController,
            roomData!!,
            onDismiss = {
                RoomService().removePlayerToRoom(roomData!!.key,SessionCurrent.localPlayer)
                isDialogWaitVisible = false
            }
        )
    }
    AlertDialog(
        title = {
            TextPixel("Buscar sala", textStylePixel(Color.White,Color.White,30))
        },
        text = {
            if(!findRoomResult){
                TextPixel("No se encontro ninguna sala con el ID: ${subKeyText.text}", textStylePixel(Color.White, Color.Red, 20))
            }else{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    TextPixel("ID: ", textStylePixel(Color.White, Color.DarkGray, 20))
                    Spacer(modifier = Modifier.width(8.dp)) // Agrega un espacio entre el texto y el campo de texto
                    BasicTextField(
                        textStyle = textStylePixel(),
                        modifier = Modifier
                            .border(1.dp, Color.White, RoundedCornerShape(0.dp, 8.dp, 0.dp, 8.dp))
                            .weight(1f), // Utiliza "weight" para expandir el campo de texto para llenar el espacio restante
                        value = subKeyText,
                        onValueChange = {
                            subKeyText = it
                            isSubKeyEntered = it.text.isNotEmpty()
                        },
                    )
                }
            }
        },
        confirmButton = {
            if(findRoomResult){
                TextButton(
                    onClick = { findRoom(subKeyText.text) },
                ) {
                    TextPixel("Buscar", textStylePixel(Color.White,Color.DarkGray,20))
                }
            }
            TextButton(
                onClick = { onDismiss() },
            ) {
                TextPixel("Cerrar", textStylePixel(Color.White,Color.DarkGray,20))
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