package com.example.udprogramacionporcomponentes02proyecto.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.screens.composablesIndex.QuestionOne
import com.example.udprogramacionporcomponentes02proyecto.screens.composablesIndex.Responses
import com.example.udprogramacionporcomponentes02proyecto.util.BackgroundIndex
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexScreen(navController: NavController){
    Scaffold{
        BackgroundIndex()
        indexBodyContent(navController)
    }
}

@Composable
fun indexBodyContent(navController:NavController){
    QuestionOne()
    Responses()
}

