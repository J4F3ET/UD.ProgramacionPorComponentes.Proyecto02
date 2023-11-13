package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.controlPanel

import android.content.ContentValues
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.udprogramacionporcomponentes02proyecto.model.CurrentThrow
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorPlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.resolveUpdateDiceToGameState
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.shouldEnableReleaseButtonDice
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

@Composable
fun BottomBarDice(){
    var currentThrow by remember {
        mutableStateOf(SessionCurrent.gameState.currentThrow)
    }
    var enable by remember {
        mutableStateOf(
            shouldEnableReleaseButtonDice(SessionCurrent.gameState.currentThrow)
        )
    }
    val colorCurrentThrow = mapColorPlayer[currentThrow.player.color]

    val updateDiceGame:() -> Unit= {
        currentThrow.checkThrow = true
        currentThrow.dataToDices = Pair((1..6).random(), (1..6).random())
        if(resolveUpdateDiceToGameState(currentThrow)) {
            SessionCurrent.gameState.currentThrow = currentThrow
            GameStateService().updateGameState()
        }
    }
    val updateCurrentThrow:(CurrentThrow?)->Unit = {
        if (it != null){
            currentThrow = it
            SessionCurrent.gameState.currentThrow = it
            enable = shouldEnableReleaseButtonDice(it)
            if(UtilGame.finishEndShift(it)){
                UtilGame.endShift(SessionCurrent.gameState)
            }
        }
    }
    val listGameStateValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                updateCurrentThrow(GameStateService().convertDataSnapshotToCurrentThrow(dataSnapshot.child("currentThrow")))
            } else {
                Log.e("Error", "No se pudo convertir dataSnapshot a list<BoadCell>")
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
    GameStateService().getDatabaseChild(SessionCurrent.gameState.key).addValueEventListener(listGameStateValueEventListener)
    Row(
        modifier = Modifier.clickable(
            enabled = enable,
            onClick = {
                updateDiceGame()
            }
        )
    ){
        Box(
            modifier = Modifier
                .border(1.dp, colorCurrentThrow?: Color.White, RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                .width(60.dp)
                .height(60.dp),
            contentAlignment = Alignment.TopCenter
        ){
            TextPixel(text = "${currentThrow.dataToDices.first}", textStylePixel(Color.White, colorCurrentThrow?:Color.Black,40))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .border(1.dp, colorCurrentThrow?: Color.White, RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                .width(60.dp)
                .height(60.dp),
            contentAlignment = Alignment.TopCenter
        ){
            TextPixel(text = "${currentThrow.dataToDices.second}", textStylePixel(Color.White, colorCurrentThrow?: Color.Black,40))
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