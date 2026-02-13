// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/**
 * Función principal de navegación de la aplicación.
 * Define el NavHost y las rutas entre las diferentes pantallas.
 */
@Composable
fun MiamiApp() {
    val miamiViewModel: MiamiViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(onEnter = { navController.navigate("home") }) }

        composable(
            route = "home",
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) }
        ) {
            MainScreen(
                viewModel = miamiViewModel,
                onItemClick = { id -> navController.navigate("detail/$id") }
            )
        }

        // Pantalla de detalle que recibe el ID del elemento como argumento
        composable(
            route = "detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType }),
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
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
