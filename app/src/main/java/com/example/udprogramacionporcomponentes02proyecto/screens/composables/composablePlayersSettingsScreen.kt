package com.example.udprogramacionporcomponentes02proyecto.screens.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.model.RoomService
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPlayersSettingsScreen(){
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(7,12,19)),
        title = { /*TODO*/ },
        actions = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Column {
                    TextPixel(text = "Escoja una sala", Color.White,false)
                }
            }
        }
    )
}
@Composable
fun BottomBarPlayersSettingsScreen(){
    BottomAppBar(
        containerColor = Color.Transparent/*Color(7,12,19)*/,
        tonalElevation = 10.dp,
        contentPadding = PaddingValues(10.dp,0.dp)
    ){
        ElevatedButton(
            onClick = { /*TODO*/ },
            content = { TextPixel(text = "Buscar", color = Color.White, boolean = true) },
            colors = ButtonDefaults.buttonColors(
                Color.Transparent, Color.Black, Color.Black, Color.Black),
            border = BorderStroke(2.dp, Color.White)
        )
        Spacer(modifier = Modifier.width(10.dp))
        ElevatedButton(
            onClick = { /*TODO*/ },
            content = { TextPixel(text = "Crear", color = Color.White, boolean = true) },
            colors = ButtonDefaults.buttonColors(
                Color.Transparent, Color.Black, Color.Black, Color.Black),
            border = BorderStroke(2.dp, Color.White)
        )
    }
}
@Composable
fun ListRooms() {
    val rooms by RoomService().rooms
    LazyColumn(
        modifier =  Modifier.fillMaxHeight(0.9f),
        contentPadding = PaddingValues(0.dp,65.dp),
        userScrollEnabled= true,
    )  {
        items(rooms.size) { index ->
            if (rooms[index].players.size < 4){
                ItemRoom(room = rooms[index])
            }
        }
    }
}
@Composable
fun ItemRoom(room: Room){
    Row(
        Modifier
            .border(1.dp, Color.Black, RectangleShape)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Black, Color.Transparent, Color.Black)
                )
            )
    ){
        Text(
            style = TextStyle(
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.pixelify_sans_variable_font_wght, FontWeight.Normal)),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp, // Cambia el tamaño del texto
                lineHeight = 23.sp, // Ajusta la altura de línea
                letterSpacing = 0.sp,
                shadow = Shadow(
                    color = Color.Black, // Color del sombreado (negro)
                    offset = Offset(2f, 2f), // Desplazamiento del sombreado en dp
                    blurRadius = 18f // Radio de desenfoque del sombreado en dp
                )
            ),
            modifier = Modifier.padding(2.dp,4.dp),
            text = "Id: ${room.key}\n" +
                    "Jugadores: ${room.players.size}/4\n"
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
}