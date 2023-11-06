package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesGameScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.udprogramacionporcomponentes02proyecto.R
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorImagePiece
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapColorIndexBackground
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP

@Composable
fun GridCellPiecesJail(color: ColorP, with: Dp){
    Box(modifier = Modifier
        .background(Color.Transparent)
        .height(with.div(4))
        .width(with.div(5))
        .padding(1.dp)){
        Image(
            modifier = Modifier.background(Color.Transparent),
            painter = painterResource(mapColorImagePiece[color] ?: R.drawable.piece_blue),
            contentDescription = "Piezas en calcer",
            contentScale = ContentScale.Crop,
        )
    }
}
@Composable
fun GridCellJail(color: ColorP, with: Dp, height: Dp){
    Box(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(0.dp, 8.dp, 0.dp, 8.dp))
            .fillMaxSize()
            .height(height)
            .width(with),
        contentAlignment = Alignment.Center
    ){
        BackGrounds(
            indexBg = mapColorIndexBackground[color]?:3,
            modifier = Modifier
                .align(Alignment.Center)
                .border(1.dp, Color.Transparent, RoundedCornerShape(0.dp, 8.dp, 0.dp, 8.dp))
                .blur(13.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .background(Color.Transparent)
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                content = {
                    items(4){
                        GridCellPiecesJail(color,with)
                    }
                }
            )
        }
    }
}