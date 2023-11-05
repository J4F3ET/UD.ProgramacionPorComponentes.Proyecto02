package com.example.udprogramacionporcomponentes02proyecto.screens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorPlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapImagePlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController:NavController){
    Scaffold(
        topBar = { TopBarGameScreen() },
        bottomBar = { BottomBarGameScreen() },
        content = {
            BackGrounds(2)
            GameScreenContent(it)
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun TopBarGameScreen(){
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(7,12,19)),
        title = {},
        actions = { CurrentPlayer()}
    )

 }
@Composable
fun BottomBarGameScreen(){
    BottomAppBar(
        containerColor = Color(7,12,19),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BottomBarDice()
                BottomBarLocalPlayer()
            }
        }
    )
}
fun calculateCurrentPlayer(listToPlayers:List<Player>,oldPlayer: Player,currentThrow1:Int,currentThrow2:Int):Player{
    if(currentThrow1 == currentThrow2) return oldPlayer
    val index = listToPlayers.indexOf(oldPlayer)
    if (index+1 == listToPlayers.size) return listToPlayers[0]
    return listToPlayers[index+1]
}
@Composable
fun BottomBarDice(){
    var currentThrow1 by remember { mutableIntStateOf(0) }
    var currentThrow2 by remember { mutableIntStateOf(0) }
    val updateGame:() -> Unit= {
        currentThrow1 = (1..6).random()
        currentThrow2 = (1..6).random()
        SessionCurrent.gameState.currentPlayer = calculateCurrentPlayer(SessionCurrent.roomGame.players,SessionCurrent.gameState.currentPlayer,currentThrow1,currentThrow2)
        GameStateService().updateGameState()
    }
    Row(
        modifier = Modifier.clickable(
            enabled = true,
            onClick = {
                updateGame()
            }
        )
    ){
        Box(
            modifier = Modifier
                .border(1.dp, Color.White, RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                .width(60.dp)
                .height(60.dp),
            contentAlignment = Alignment.TopCenter
        ){
            TextPixel(text = "$currentThrow1", textStylePixel(Color.White, Color.Black,40))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .border(1.dp, Color.White, RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                .width(60.dp)
                .height(60.dp),
            contentAlignment = Alignment.TopCenter
        ){
            TextPixel(text = "$currentThrow2", textStylePixel(Color.White, Color.Black,40))
        }
    }
}
@Composable
fun GameScreenContent(padingValues: PaddingValues){

}
@Composable
fun CurrentPlayer() {
    var gameState by remember { mutableStateOf(SessionCurrent.gameState)}
    var colorCurrentPlayer = mapColorPlayer[gameState.currentPlayer.color] ?: Color.Transparent
    val updateGameState:(GameState?)->Unit={
        if(it !=null){
            SessionCurrent.gameState = it
            gameState = SessionCurrent.gameState
        }
    }
    val gameStateValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                updateGameState(GameStateService().convertDataSnapshotToGameState(dataSnapshot))
            } else {
                Log.e("Error", "No se pudo convertir dataSnapshot a room")
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    GameStateService().getDatabaseChild(gameState.key).addValueEventListener(gameStateValueEventListener)
    //Filtra las key que se encuentren en la RoomGame de la session
    val mapPlayersInGame: Map<ColorP, Int> =
        mapImagePlayer.filterKeys { key -> key in SessionCurrent.roomGame.players.map { it.color } }
    Box(
        modifier = Modifier.fillMaxWidth()
            .shadow(5.dp, MaterialTheme.shapes.small, true, colorCurrentPlayer),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            mapPlayersInGame.forEach { image ->
                Image(
                    painter = painterResource(id = image.value),
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .height(60.dp)
                        .width(60.dp)
                        .aspectRatio(1f) // Mantén la relación de aspecto cuadrada
                        .clip(MaterialTheme.shapes.small)
                        .blur(if (gameState.currentPlayer.color != image.key) 5.dp else 0.dp)
                        .shadow(10.dp, MaterialTheme.shapes.small, true, colorCurrentPlayer),
                    contentScale = ContentScale.Crop,

                    )
            }
        }
    }
}
@Composable
fun BottomBarLocalPlayer(){
    SessionCurrent.localPlayer.color.let {colorP ->
        val color: Color? = mapColorPlayer[colorP]
        val idImage:Int? = mapImagePlayer[colorP]
        if (color == null || idImage == null)return
        Row {
            TextPixel(
                text = SessionCurrent.localPlayer.name+"  ",
                textStyle = textStylePixel(Color.White,color,35)
            )
            Box(
                modifier = Modifier.wrapContentSize(Alignment.Center),
            ) {
                Image(
                    painter = painterResource(id = idImage),
                    contentDescription = null,
                    modifier = Modifier
                        .height(60.dp)
                        .aspectRatio(1f) // Mantén la relación de aspecto cuadrada
                        .clip(MaterialTheme.shapes.small) // Opcional: agrega un recorte según la forma que desees
                )
            }
        }
    }
}