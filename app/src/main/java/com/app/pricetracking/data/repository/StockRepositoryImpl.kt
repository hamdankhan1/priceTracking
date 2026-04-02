package com.app.pricetracking.data.repository

import com.app.pricetracking.data.model.Stock
import com.app.pricetracking.data.websocket.WebSocketManager
import kotlinx.coroutines.flow.Flow

class StockRepositoryImpl(
    private val ws: WebSocketManager
) : StockRepository {

    override val stockStream: Flow<Stock> = ws.updates

    override fun connect() = ws.connect()

    override fun disconnect() = ws.disconnect()

    override suspend fun sendStock(stock: Stock) {
        ws.send(stock)
    }
}