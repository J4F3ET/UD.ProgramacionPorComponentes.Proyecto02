package com.example.udprogramacionporcomponentes02proyecto.screens.composablesIndex

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
@Composable
fun TextPixel(text:String){
    Text(
        text = text,
        modifier = Modifier.padding(0.dp,15.dp,0.dp,0.dp),
        style = TextStylePixel()
    )
}
@Composable
fun QuestionOne(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(200.dp)
                .padding(0.dp, 50.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.TopCenter
        ){
            ImgSpeechBubbles(200,100)
            TextPixel(text = "¿Quién eres?")
        }
    }
}
@Composable
fun Responses(){
    var textState by remember { mutableStateOf(TextFieldValue()) }
    var isNameEntered by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 50.dp, 0.dp, 0.dp),
        contentAlignment = Alignment.BottomCenter
    ){
        Box(contentAlignment = Alignment.Center){
            val width = 350
            val height = 200
            ImgSpeechBubbles(width,height)
            Row(modifier = Modifier.padding(0.dp, 0.dp, 80.dp, 150.dp),
            ){
                //if(isNameEntered){
                Button(onClick = { /*TODO*/ }) {
                    TextPixel(text = "Siguiente ->")

                }
                //}else{
                TextPixel("Escribe aquí")
                //}
            }
            Row {
                BasicTextField(
                    textStyle = TextStylePixel(),
                    modifier = Modifier
                        .width((width * 0.8).dp)
                        .height((height * 0.4).dp)
                        .padding(0.dp, 0.dp, 0.dp, 50.dp),
                    value = textState,
                    onValueChange = {
                        textState = it
                        isNameEntered = it.text.isNotEmpty()
                    },
                )
            }
        }
    }
}