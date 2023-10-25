package com.example.udprogramacionporcomponentes02proyecto.screens.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.udprogramacionporcomponentes02proyecto.R

@Composable
fun ImgSpeechBubbles(width:Int,height:Int){
    Image(
        painter =  painterResource(id = R.drawable.speech_bubbles),
        contentDescription = null,
        //modifier = Modifier.align(Alignment.Center),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun ImgSpeechBubblesInvert(width: Int, height: Int) {
    Image(
        painter = painterResource(id = R.drawable.speech_bubbles),
        contentDescription = null,
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .graphicsLayer(
                scaleX = -1f, // Escala en el eje X para lograr el efecto de espejo
            ),
        contentScale = ContentScale.FillBounds
    )
}

fun TextStylePixel(): TextStyle {
    return TextStyle(
        color = Color.Black,
        fontFamily = FontFamily(Font(R.font.pixelify_sans_variable_font_wght, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp, // Cambia el tamaño del texto
        lineHeight = 40.sp, // Ajusta la altura de línea
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = Color(color = 0xFFFFFFFF), // Color del sombreado (negro)
            offset = Offset(2f, 2f), // Desplazamiento del sombreado en dp
            blurRadius = 15f // Radio de desenfoque del sombreado en dp
        )
    )
}
fun TextStylePixel(color: Color,boolean: Boolean): TextStyle {
    var shadow =if(boolean) {Shadow(
        color = Color.White, // Color del sombreado (blanco)
        offset = Offset(2f, 2f), // Desplazamiento del sombreado en dp
        blurRadius = 15f // Radio de desenfoque del sombreado en dp
    )}else{
        Shadow(
            color = Color.Black, // Color del sombreado (negro)
            offset = Offset(2f, 2f), // Desplazamiento del sombreado en dp
            blurRadius = 18f // Radio de desenfoque del sombreado en dp
        )
    }
    return TextStyle(
        color = color,
        fontFamily = FontFamily(Font(R.font.pixelify_sans_variable_font_wght, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp, // Cambia el tamaño del texto
        lineHeight = 40.sp, // Ajusta la altura de línea
        letterSpacing = 0.sp,
        shadow = shadow
    )
}
@Composable
fun TextPixel(text:String){
    Text(
        text = text,
        modifier = Modifier.padding(0.dp,15.dp,0.dp,0.dp),
        style = TextStylePixel()
    )
}
@Composable
fun TextPixel(text:String,color: Color,boolean: Boolean){
    Text(
        text = text,
        modifier = Modifier.padding(0.dp,15.dp,0.dp,0.dp),
        style = TextStylePixel(color,boolean)
    )
}