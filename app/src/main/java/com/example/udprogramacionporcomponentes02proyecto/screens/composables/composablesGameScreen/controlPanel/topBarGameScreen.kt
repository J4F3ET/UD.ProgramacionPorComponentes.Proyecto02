package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.controlPanel

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.udprogramacionporcomponentes02proyecto.model.GameState
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorPlayer
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


@Composable
fun CurrentPlayer() {
    var gameState by remember { mutableStateOf(SessionCurrent.gameState) }
    val colorCurrentPlayer = mapColorPlayer[gameState.currentThrow.player.color] ?: Color.Transparent
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
                Log.e("Error", "No se pudo convertir dataSnapshot a GameState")
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    GameStateService().getDatabaseChild(gameState.key).addValueEventListener(gameStateValueEventListener)
    //Filtra las key que se encuentren en la RoomGame de la session
    val mapPlayersInGame: Map<ColorP, Int> =
        mapColorImagePlayer.filterKeys { key -> key in SessionCurrent.roomGame.players.map { it.color } }
    Box(
        modifier = Modifier
            .fillMaxWidth()
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
                        .blur(if (gameState.currentThrow.player.color != image.key) 5.dp else 0.dp)
                        .shadow(10.dp, MaterialTheme.shapes.small, true, colorCurrentPlayer),
                    contentScale = ContentScale.Crop,

                    )
            }
        }
    }
}