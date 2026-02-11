package com.example.p4_miami_gabriel_jorge

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MiamiApp() {
    val miamiViewModel: MiamiViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(onEnter = { navController.navigate("home") }) }

        composable("home") {
            MainScreen(
                    viewModel = miamiViewModel,
                    onItemClick = { id -> navController.navigate("detail/$id") }
            )
        }

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
