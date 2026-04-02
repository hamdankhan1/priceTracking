package com.app.pricetracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.pricetracking.data.repository.StockRepositoryImpl
import com.app.pricetracking.data.websocket.WebSocketManager
import com.app.pricetracking.navigation.NavGraph
import com.app.pricetracking.ui.viewmodel.StockViewModel
import com.app.pricetracking.ui.theme.PriceTrackingTheme
import com.app.pricetracking.ui.viewmodel.StockViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webSocketManager = WebSocketManager()
        val repository = StockRepositoryImpl(webSocketManager)

        setContent {
            PriceTrackingTheme {
                val viewModel: StockViewModel = viewModel(
                    factory = StockViewModelFactory(repository)
                )
                NavGraph(viewModel)
            }
        }
    }
}