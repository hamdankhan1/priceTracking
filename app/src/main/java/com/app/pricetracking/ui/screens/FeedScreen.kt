package com.app.pricetracking.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.pricetracking.ui.components.StockRow
import com.app.pricetracking.ui.viewmodel.StockViewModel

@Composable
fun FeedScreen(
    viewModel: StockViewModel,
    onItemClick: (String) -> Unit
) {
    val stocks by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startFeed()
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.stopFeed() }
    }

    Column {

        Text(
            text = "📊 Live Market",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(stocks) { stock ->
                StockRow(stock) {
                    onItemClick(stock.symbol)
                }
            }
        }
    }
}