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
fun BackGrounds(numBg:Int){
    val images = listOf(R.drawable.bg1,R.drawable.bg2,R.drawable.bg3)
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter =  painterResource(id = images[numBg]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}
@Preview
@Composable
fun previewBack(){
    BackGrounds(1)
}
