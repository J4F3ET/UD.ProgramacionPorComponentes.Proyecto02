package com.example.udprogramacionporcomponentes02proyecto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppScreens
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WaitingScreen(navController: NavController){
    val loadGame: ()-> Unit = {
        CoroutineScope(Dispatchers.IO).launch {
            // Ejemplo: delay(2000) // Simula una espera de 2 segundos
            // Espera 2 segundos antes de actualizar el estado y navegar
            delay(2000)
            // Actualiza el estado del juego en el hilo principal
            CoroutineScope(Dispatchers.Main).launch {
                // Navega a la siguiente pantalla en Compose
                navController.navigate(route = AppScreens.GameScreen.router)
            }
        }
    }
    loadGame()
    Box(modifier = Modifier.fillMaxSize()){
        BackGrounds(
            indexBg = 2,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
        TextPixel(text = "Esperando ...", textStyle = textStylePixel(Color.White,Color.White,50))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ){
            Image(
                painter = painterResource(id = R.drawable.horus_w4k),
                modifier = Modifier.fillMaxWidth(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }

}

@Preview
@Composable
fun WaitingScreenPreview(){
    WaitingScreen(NavController(LocalContext.current))
}