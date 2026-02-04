// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel // IMPORTANTE
import androidx.navigation.NavType // IMPORTANTE
import androidx.navigation.compose.NavHost // IMPORTANTE
import androidx.navigation.compose.composable // IMPORTANTE
import androidx.navigation.compose.rememberNavController // IMPORTANTE
import androidx.navigation.navArgument // IMPORTANTE
import com.example.p4_miami_gabriel_jorge.ui.theme.P4_Miami_Gabriel_JorgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            P4_Miami_Gabriel_JorgeTheme {
                // 1. Instanciamos el ViewModel y el NavController
                val miamiViewModel: MiamiViewModel = viewModel()
                val navController = rememberNavController()

                // 2. Definimos el NavHost (El mapa de navegación)
                NavHost(
                    navController = navController,
                    startDestination = "welcome"
                ) {
                    // Pantalla de Bienvenida
                    composable("welcome") {
                        WelcomeScreen(onEnter = {
                            navController.navigate("home")
                        })
                    }

                    // Pantalla Principal
                    composable("home") {
                        MainScreen(
                            viewModel = miamiViewModel,
                            onItemClick = { id ->
                                navController.navigate("detail/$id")
                            }
                        )
                    }

                    // Pantalla de Detalle con paso de argumentos
                    composable(
                        route = "detail/{itemId}",
                        arguments = listOf(navArgument("itemId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("itemId") ?: 0
                        DetailScreen(
                            itemId = id,
                            viewModel = miamiViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}