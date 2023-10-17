package com.example.udprogramacionporcomponentes02proyecto.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.udprogramacionporcomponentes02proyecto.util.backgroundIndex

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun indexScreen(navController: NavController){
    Scaffold{
        backgroundIndex()
        indexBodyContent(navController)
    }
}

@Composable
fun indexBodyContent(navController:NavController){

}