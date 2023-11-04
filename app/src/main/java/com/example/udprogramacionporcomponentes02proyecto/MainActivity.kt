package com.example.udprogramacionporcomponentes02proyecto

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.udprogramacionporcomponentes02proyecto.model.GameStateService
import com.example.udprogramacionporcomponentes02proyecto.model.Piece
import com.example.udprogramacionporcomponentes02proyecto.model.Player
import com.example.udprogramacionporcomponentes02proyecto.model.PlayerService
import com.example.udprogramacionporcomponentes02proyecto.model.Room
import com.example.udprogramacionporcomponentes02proyecto.model.RoomService
import com.example.udprogramacionporcomponentes02proyecto.navigation.AppNavigation
import com.example.udprogramacionporcomponentes02proyecto.ui.theme.UDProgramacionPorComponentes02ProyectoTheme
import com.example.udprogramacionporcomponentes02proyecto.util.ColorP
import com.example.udprogramacionporcomponentes02proyecto.util.SessionCurrent
import com.example.udprogramacionporcomponentes02proyecto.util.State
import com.example.udprogramacionporcomponentes02proyecto.util.UtilGame.Companion.initializationGame

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        SessionCurrent.roomGame = Room("2f9016a3-4ffb-4dbd-92d1-8d99a74ccacf",
//            mutableListOf(
//                Player("103d6625-4d0f-45ef-921a-56335a0762a3","Inicializador",
//                    ColorP.RED,
//                        listOf(
//                        Piece(ColorP.RED,0,State.JAIL),
//                        Piece(ColorP.RED,0,State.JAIL),
//                        Piece(ColorP.RED,0,State.JAIL),
//                        Piece(ColorP.RED,0,State.JAIL)
//                    )
//                ),
//                Player("473d2f99-182b-4991-86c7-5b51cfa5c10b","Prueba",
//                    ColorP.BLUE,
//                    listOf(
//                        Piece(ColorP.BLUE,0,State.JAIL),
//                        Piece(ColorP.BLUE,0,State.JAIL),
//                        Piece(ColorP.BLUE,0,State.JAIL),
//                        Piece(ColorP.BLUE,0,State.JAIL)
//                    )
//                ),
//                Player("77c6c9e7-dead-46e7-a62a-80b5b7bd559a","Rojo",
//                    ColorP.YELLOW,
//                    listOf(
//                        Piece(ColorP.YELLOW,0,State.JAIL),
//                        Piece(ColorP.YELLOW,0,State.JAIL),
//                        Piece(ColorP.YELLOW,0,State.JAIL),
//                        Piece(ColorP.YELLOW,0,State.JAIL)
//                    )
//                ),
//                Player("fef4d4d5-0862-4415-8ae4-65aa992122af","verde",
//                    ColorP.GREEN,
//                    listOf(
//                        Piece(ColorP.GREEN,0,State.JAIL),
//                        Piece(ColorP.GREEN,0,State.JAIL),
//                        Piece(ColorP.GREEN,0,State.JAIL),
//                        Piece(ColorP.GREEN,0,State.JAIL)
//                    )
//                )
//            ),"5ac82bc4-41f8-4863-b195-189c2caf6bb4"
//        )
//        SessionCurrent.localPlayer = Player("103d6625-4d0f-45ef-921a-56335a0762a3","Inicializador",
//            ColorP.RED,
//            listOf(
//                Piece(ColorP.RED,0,State.JAIL),
//                Piece(ColorP.RED,0,State.JAIL),
//                Piece(ColorP.RED,0,State.JAIL),
//                Piece(ColorP.RED,0,State.JAIL)
//            )
//        )
//        SessionCurrent.gameState = initializationGame("5ac82bc4-41f8-4863-b195-189c2caf6bb4")
        super.onCreate(savedInstanceState)
        setContent {
            UDProgramacionPorComponentes02ProyectoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}