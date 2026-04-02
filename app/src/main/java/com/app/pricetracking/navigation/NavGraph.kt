package com.app.pricetracking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.pricetracking.ui.screens.*
import com.app.pricetracking.ui.viewmodel.StockViewModel

@Composable
fun NavGraph(viewModel: StockViewModel) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen {
                navController.navigate("feed") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("feed") {
            FeedScreen(viewModel) {
                navController.navigate("details/$it")
            }
        }

        composable("details/{symbol}") {
            val symbol = it.arguments?.getString("symbol")!!
            DetailsScreen(symbol, viewModel)
        }
    }
}