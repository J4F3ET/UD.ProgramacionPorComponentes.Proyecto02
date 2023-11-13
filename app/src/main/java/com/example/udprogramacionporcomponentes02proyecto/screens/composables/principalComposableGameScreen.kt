package com.example.udprogramacionporcomponentes02proyecto.screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.controlPanel.BottomBarDice
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.controlPanel.BottomBarLocalPlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen.controlPanel.CurrentPlayer

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
