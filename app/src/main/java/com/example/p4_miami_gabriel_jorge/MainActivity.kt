// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.p4_miami_gabriel_jorge.ui.theme.P4_Miami_Gabriel_JorgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { P4_Miami_Gabriel_JorgeTheme { MiamiApp() } }
    }
}
