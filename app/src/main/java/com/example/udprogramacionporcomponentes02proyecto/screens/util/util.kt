package com.example.udprogramacionporcomponentes02proyecto.screens.util

import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
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
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.RulesGame

@Composable
fun ImgSpeechBubbles(width:Int,height:Int){
    Image(
        painter =  painterResource(id = R.drawable.speech_bubbles),
        contentDescription = null,
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
fun calculateFontSizeByText(texto: String, maxWidthDp: Int): Float {
    val density = Resources.getSystem().displayMetrics.density
    val maxWidthPx = (maxWidthDp * density).toInt()
    var textSize = 18f
    val textPaint = Paint()
    val rect = Rect()
    textPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, Resources.getSystem().displayMetrics)
    textPaint.getTextBounds(texto, 0, texto.length, rect)
    var textWidth = rect.width()
    while (textWidth > maxWidthPx) {
        textSize -= 1f
        textPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, Resources.getSystem().displayMetrics)
        textPaint.getTextBounds(texto, 0, texto.length, rect)
        textWidth = rect.width()
    }
    return textSize
}
fun calculateCurrentPlayer(listToPlayers:List<Player>, oldPlayer: Player, currentThrow1:Int, currentThrow2:Int): Player {
    if(currentThrow1 == currentThrow2) return oldPlayer
    val index = listToPlayers.indexOf(oldPlayer)
    if (index+1 == listToPlayers.size) return listToPlayers[0]
    return listToPlayers[index+1]
}
val mapColorSecureZone = mapOf(
    ColorP.BLUE to (92..99).toList() + listOf(55),
    ColorP.YELLOW to (84..91).toList() + listOf(38),
    ColorP.RED to (68..75).toList() + listOf(4),
    ColorP.GREEN to (76..83).toList() + listOf(21),
    ColorP.NEUTRAL to listOf(11, 16, 28, 33, 45, 50, 62, 67)
)

val listImagesBackgrounds = listOf(
    R.drawable.bg1,
    R.drawable.bg2,
    R.drawable.bg3,
    R.drawable.general_blue,
    R.drawable.general_yellow,
    R.drawable.general_red,
    R.drawable.general_green
)
val mapColorImagePlayer = mapOf(
    ColorP.RED to R.drawable.general_red,
    ColorP.BLUE to R.drawable.general_blue,
    ColorP.YELLOW to R.drawable.general_yellow,
    ColorP.GREEN to R.drawable.general_green
)
val mapColorPlayer = mapOf(
    ColorP.RED to Color(0.694f, 0.255f, 0.224f, 1.0f),
    ColorP.BLUE to Color(0.298f, 0.42f, 0.733f, 0.8f),
    ColorP.YELLOW to Color(0.847f, 0.749f, 0.125f, 0.8f),
    ColorP.GREEN to Color(0.349f, 0.62f, 0.286f, 0.8f),
    ColorP.NEUTRAL to Color(0.337f, 0.345f, 0.369f, 0.8f)
)

val mapColorIndexBackground = mapOf(
    ColorP.BLUE to 3,
    ColorP.YELLOW to 4,
    ColorP.RED to 5,
    ColorP.GREEN to 6
)
val mapColorImagePiece = mapOf(
    ColorP.BLUE to R.drawable.piece_blue,
    ColorP.YELLOW to R.drawable.piece_yellow,
    ColorP.RED to R.drawable.piece_red,
    ColorP.GREEN to R.drawable.piece_green
)