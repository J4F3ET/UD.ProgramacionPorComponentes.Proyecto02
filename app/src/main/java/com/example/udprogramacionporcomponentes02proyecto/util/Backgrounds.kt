package com.example.udprogramacionporcomponentes02proyecto.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.udprogramacionporcomponentes02proyecto.R

@Composable
fun BackgroundIndex(){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter =  painterResource(id = R.drawable.bg1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}
@Composable
fun BackgroundPlayersSettings(){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter =  painterResource(id = R.drawable.bg2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}
@Composable
fun BackgroundGame(){

}
@Preview
@Composable
fun previewBack(){
    BackgroundIndex()
}