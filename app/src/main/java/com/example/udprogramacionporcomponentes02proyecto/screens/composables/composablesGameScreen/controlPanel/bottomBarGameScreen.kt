package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.controlPanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.calculateCurrentPlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorPlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent

@Composable
fun BottomBarDice(){
    var currentThrow1 by remember { mutableIntStateOf(0) }
    var currentThrow2 by remember { mutableIntStateOf(0) }
    val updateGame:() -> Unit= {
        currentThrow1 = (1..6).random()
        currentThrow2 = (1..6).random()
        SessionCurrent.gameState.currentPlayer = calculateCurrentPlayer(
            SessionCurrent.roomGame.players,
            SessionCurrent.gameState.currentPlayer,currentThrow1,currentThrow2)
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
fun BottomBarLocalPlayer(){
    SessionCurrent.localPlayer.color.let { colorP ->
        val color: Color? = mapColorPlayer[colorP]
        val idImage:Int? = mapColorImagePlayer[colorP]
        if (color == null || idImage == null)return
        Row {
            TextPixel(
                text = SessionCurrent.localPlayer.name+"  ",
                textStyle = textStylePixel(Color.White,color,35)
            )
            Box(
                modifier = Modifier.wrapContentSize(Alignment.Center),
            ){
                Image(
                    painter = painterResource(id = idImage),
                    contentDescription = null,
                    modifier = Modifier.height(60.dp).aspectRatio(1f).clip(MaterialTheme.shapes.small)
                )
            }
        }
    }
}