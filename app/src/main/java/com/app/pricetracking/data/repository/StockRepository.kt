package com.app.pricetracking.data.repository

import com.app.pricetracking.data.model.Stock
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    val stockStream: Flow<Stock>
    fun connect()
    fun disconnect()
    suspend fun sendStock(stock: Stock)
}