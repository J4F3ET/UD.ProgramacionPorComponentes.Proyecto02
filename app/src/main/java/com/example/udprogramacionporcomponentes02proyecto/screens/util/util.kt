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
import com.example.udprogramacionporcomponentes02proyecto.util.RulesGame

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
@Composable
fun TextPixel(text:String){
    Text(
        text = text,
        modifier = Modifier.padding(0.dp,15.dp,0.dp,0.dp),
        style = textStylePixel()
    )
}
@Composable
fun TextPixel(text:String,color: Color,boolean: Boolean){
    Text(
        text = text,
        modifier = Modifier.padding(0.dp,15.dp,0.dp,0.dp),
        style = textStylePixel(color,if(boolean)Color.Black else Color.White,30)
    )
}
@Composable
fun TextPixel(text:String, textStyle: TextStyle){
    Text(
        text = text,
        modifier = Modifier.padding(0.dp,15.dp,0.dp,0.dp),
        style = textStyle
    )
}
fun shadowColor(color:Color) = Shadow(
    color = color,
    offset = Offset(2f, 2f), // Desplazamiento del sombreado en dp
    blurRadius = 15f // Radio de desenfoque del sombreado en dp
)
fun textStylePixel(fontColor: Color, shadowColor: Color,fontSize:Int): TextStyle {
    return TextStyle(
        color = fontColor,
        fontFamily = FontFamily(Font(R.font.pixelify_sans_variable_font_wght, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = fontSize.sp, // Cambia el tamaño del texto
        lineHeight = (fontSize+3).sp, // Ajusta la altura de línea
        letterSpacing = 0.sp,
        shadow = shadowColor(shadowColor)
    )
}
fun textStylePixel(): TextStyle {
    return TextStyle(
        color = Color.Black,
        fontFamily = FontFamily(Font(R.font.pixelify_sans_variable_font_wght, FontWeight.Normal)),
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp, // Cambia el tamaño del texto
        lineHeight = 40.sp, // Ajusta la altura de línea
        letterSpacing = 0.sp,
        shadow = shadowColor(Color.White)
    )
}

fun messageRule(index:Int,rules:Array<RulesGame>):Pair<String, String>{
    return "Regla ${index+1} de ${rules.size} " to rules[index].description
}