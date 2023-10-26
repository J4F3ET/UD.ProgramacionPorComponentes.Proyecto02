package com.example.udprogramacionporcomponentes02proyecto.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.model.PlayerService
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.QuestionOne
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.Responses
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.QuestionThree
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.QuestionTwo
import com.example.udprogramacionporcomponentes02proyecto.screens.composables.SelectColor
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.BackGrounds
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexScreen(navController: NavController){
    Scaffold{
        BackGrounds(0)
        IndexBodyContent(navController)
    }
}
@Composable
fun IndexBodyContent(navController:NavController){
    var textState by remember { mutableStateOf(TextFieldValue()) }
    var isNameEntered by remember { mutableStateOf(false) }
    var isColorEntered by remember { mutableStateOf(false) }
    val onNameEnteredChange:(Boolean,TextFieldValue) -> Unit = { newValue,newName ->
        isNameEntered = newValue
        textState = newName
    }
    val onColorEnteredChange:(Boolean,ColorP) -> Unit = { newValue,newColor ->
        SessionCurrent.localPlayer = PlayerService().createPlayer(textState.text,newColor)
        isColorEntered = newValue
    }
    if(isNameEntered && !isColorEntered){
        QuestionTwo(textState.text)
        SelectColor(onColorEnteredChange)
    }else if(!isNameEntered){
        QuestionOne()
        Responses(onNameEnteredChange)
    }else{
        QuestionThree(navController)
    }
}
