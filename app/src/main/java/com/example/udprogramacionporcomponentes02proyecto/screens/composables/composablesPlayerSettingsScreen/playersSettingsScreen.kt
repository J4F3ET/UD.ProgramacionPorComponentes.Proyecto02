package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesPlayerSettingsScreen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.model.RoomService
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPlayersSettingsScreen(){
    var isDialogVisible by remember { mutableStateOf(false) }
    if (isDialogVisible) DialogRules(onDismiss = { isDialogVisible = false })
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
    if (isDialogFindRoomVisible)DialogFindRoom(navController){isDialogFindRoomVisible = false}
    if (isDialogWaitVisible){
        DialogWaitGame(navController = navController, room = SessionCurrent.roomGame) {
            RoomService().removePlayerToRoom(SessionCurrent.roomGame.key,SessionCurrent.localPlayer)
            isDialogWaitVisible = false
        }
    }
    BottomAppBar(
        containerColor = Color.Transparent,
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
            onClick = {
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
    ){
        items(rooms.size){if(rooms[it].players.size < 4) ItemRoom(room = rooms[it],navController) }
    }
}
@Composable
fun ItemRoom(room: Room,navController: NavController){
    var isDialogVisible by remember { mutableStateOf(false) }
    val onDismiss:()->Unit={
        RoomService().removePlayerToRoom(room.key,SessionCurrent.localPlayer)
        isDialogVisible = false
    }
    if (isDialogVisible)DialogWaitGame(navController,room,onDismiss)
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

