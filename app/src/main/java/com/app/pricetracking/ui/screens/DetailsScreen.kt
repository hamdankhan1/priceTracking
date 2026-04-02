package com.app.pricetracking.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.pricetracking.ui.viewmodel.StockViewModel

@Composable
fun DetailsScreen(symbol: String, viewModel: StockViewModel) {

    val stocks by viewModel.uiState.collectAsState()
    val stock = stocks.find { it.symbol == symbol }

    stock?.let {

        val isUp = it.change >= 0
        val color = if (isUp) Color(0xFF16C784) else Color(0xFFEA3943)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            /// 🔹 Symbol Title
            Text(
                text = it.symbol,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            /// 🔹 Price
            Text(
                text = "₹ ${"%.2f".format(it.price)}",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            /// 🔹 Change
            Text(
                text = if (isUp) "▲ Rising" else "▼ Falling",
                color = color,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(30.dp))

            /// 🔹 Description
            Text(
                text = "This is live data for $symbol. Prices are updated in real-time using WebSocket connection.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}