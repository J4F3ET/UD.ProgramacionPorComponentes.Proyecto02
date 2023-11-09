package com.example.udprogramacionporcomponentes02proyecto.screens.composables.composablesPlayerSettingsScreen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.udprogramacionporcomponentes02proyecto.screens.util.TextPixel
import com.example.udprogramacionporcomponentes02proyecto.screens.util.messageRule
import com.example.udprogramacionporcomponentes02proyecto.screens.util.textStylePixel
import com.example.udprogramacionporcomponentes02proyecto.util.RulesGame

@Composable
fun DialogRules(onDismiss: () -> Unit){
    var currentRuleIndex by remember { mutableIntStateOf(0) }
    val rulesList = RulesGame.values()
    val (dialogTitle, dialogText) = messageRule(currentRuleIndex, rulesList)
    AlertDialog(
        title = { TextPixel(dialogTitle, textStylePixel(Color.White, Color.White,30)) },
        text = { TextPixel(dialogText, textStylePixel(Color.White, Color.DarkGray,20)) },
        confirmButton = {
            if (currentRuleIndex < rulesList.size - 1) {
                TextButton(
                    onClick = { currentRuleIndex++ },
                    content = {
                        TextPixel("Siguiente", textStylePixel(
                            Color.White,
                            Color.DarkGray,20)
                        )
                    }
                )
            } else {
                TextButton(
                    onClick = { onDismiss() },
                    content = { TextPixel("Cerrar", textStylePixel(Color.White, Color.DarkGray,20)) }
                )
            }
        },
        onDismissRequest = { onDismiss() },
        containerColor = Color(7,12,19)
    )
}