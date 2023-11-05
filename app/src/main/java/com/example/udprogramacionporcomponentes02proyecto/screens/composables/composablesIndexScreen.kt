package com.example.udprogramacionporcomponentes02proyecto.screens.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppScreens
import com.example.udprogramacionporcomponentes02proyecto.screens.util.ImgSpeechBubbles
import com.example.udprogramacionporcomponentes02proyecto.screens.util.ImgSpeechBubblesInvert
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.calculateFontSizeByText
import com.example.udprogramacionporcomponentes02proyecto.screens.util.mapImagePlayer
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP


@Composable
fun QuestionOne(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.fillMaxHeight().width(200.dp).padding(0.dp, 50.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.TopCenter
        ){
            ImgSpeechBubbles(200,100)
            TextPixel(text = "¿Quién eres?")
        }
    }
}
@Composable
fun QuestionTwo(name:String){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(200.dp)
                .padding(0.dp, 50.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.TopCenter
        ){
            ImgSpeechBubblesInvert(250,100)
            Box(modifier = Modifier.padding(15.dp,0.dp,0.dp,15.dp)){
                TextPixel(
                    text = "Oh... \n$name",
                    textStyle = textStylePixel(Color.Black,Color.Transparent,
                    fontSize = calculateFontSizeByText(name,185).toInt())
                )
            }
        }
    }
}
@Composable
fun QuestionThree(navController:NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.fillMaxHeight().width(200.dp).padding(0.dp, 50.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.TopCenter
        ){
            ImgSpeechBubbles(200,100)
            TextPixel(text = "Entra")
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        TextButton(onClick = {navController.navigate(route = AppScreens.PlayersSettingsScreen.router)}) {
            TextPixel(text = "Entrar")
        }
    }
}
@Composable
fun Responses(onNameEnteredChange:(Boolean,TextFieldValue)->Unit){
    var nameText by remember { mutableStateOf(TextFieldValue()) }
    var isNameEntered by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize().padding(0.dp, 50.dp, 0.dp, 0.dp),
        contentAlignment = Alignment.BottomCenter
    ){
        Box(contentAlignment = Alignment.Center){
            val width = 350
            val height = 200
            ImgSpeechBubbles(width,height)
            Row(modifier = Modifier.padding(0.dp, 0.dp, 80.dp, 150.dp)){
                if(isNameEntered){
                    TextButton(onClick = { onNameEnteredChange(isNameEntered,nameText) }){
                        TextPixel(text = "Siguiente ->")
                    }
                }else{
                    TextPixel("Escribe aquí")
                }
            }
            Row {
                BasicTextField(
                    textStyle = textStylePixel(),
                    modifier = Modifier
                        .width((width * 0.8).dp)
                        .height((height * 0.4).dp)
                        .padding(0.dp, 0.dp, 0.dp, 50.dp),
                    value = nameText,
                    onValueChange = {
                        nameText = it
                        isNameEntered = it.text.isNotEmpty()
                    },
                )
            }
        }
    }
}
@Composable
fun SelectColor(onColorEnteredChange:(Boolean, ColorP)->Unit){
    val partWidth = LocalConfiguration.current.screenWidthDp / 4
    val selectedImage by remember { mutableStateOf(mapImagePlayer) }
    Box(
        modifier = Modifier.fillMaxSize().padding(0.dp, 50.dp, 0.dp, 0.dp),
        contentAlignment = Alignment.BottomCenter
    ){
        Box(contentAlignment = Alignment.Center){
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                mapImagePlayer.forEach { image ->
                    val isSelected = image == selectedImage
                    Image(
                        painter = painterResource(id = image.value),
                        contentDescription = null,
                        modifier = Modifier
                            .background(if (isSelected) Color.Gray else Color.Transparent)
                            .height(partWidth.dp)
                            .width(partWidth.dp)
                            .clickable { onColorEnteredChange(true, image.key) },
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }

    }
}