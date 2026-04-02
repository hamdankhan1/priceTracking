package com.app.pricetracking.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pricetracking.data.model.Stock
import com.app.pricetracking.data.repository.StockRepository
import com.app.pricetracking.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class StockViewModel(
    private val repository: StockRepository
) : ViewModel() {

    private val symbols = Constants.STOCK_SYMBOLS

    private val _uiState = MutableStateFlow<List<Stock>>(emptyList())
    val uiState = _uiState.asStateFlow()

    private var isRunning = false

    fun startFeed() {
        if (isRunning) return
        isRunning = true

        repository.connect()

        viewModelScope.launch {

            /// 🔁 SEND LOOP
            launch {
                while (isRunning) {
                    symbols.forEach {
                        val price = Random.nextDouble(100.0, 1000.0)
                        val change = Random.nextDouble(-10.0, 10.0)

                        repository.sendStock(Stock(it, price, change))
                    }
                    delay(2000)
                }
            }

            /// 📡 RECEIVE STREAM
            launch {
                repository.stockStream.collect { stock ->
                    updateList(stock)
                }
            }
        }
    }

    fun stopFeed() {
        isRunning = false
        repository.disconnect()
    }

    private fun updateList(stock: Stock) {
        val list = _uiState.value.toMutableList()

        list.removeAll { it.symbol == stock.symbol }
        list.add(stock)

        _uiState.value = list.sortedByDescending { it.price }
    }
}