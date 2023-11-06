package com.example.udprogramacionporcomponentes02proyecto.ui.theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.screens.util.listImagesBackgrounds

@Composable
fun BackGrounds(indexBg:Int){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter =  painterResource(id = listImagesBackgrounds[indexBg]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}
@Composable
fun BackGrounds(indexBg:Int,modifier: Modifier){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter =  painterResource(id = listImagesBackgrounds[indexBg]),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}
