package com.app.pricetracking.data.websocket

import com.app.pricetracking.data.model.Stock
import com.app.pricetracking.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketManager {

    private val client = OkHttpClient()
    private var socket: WebSocket? = null

    private val _updates = MutableSharedFlow<Stock>()
    val updates = _updates.asSharedFlow()

    fun connect() {
        val request = Request.Builder()
            .url(Constants.WEBSOCKET_URL)
            .build()

        socket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onMessage(webSocket: WebSocket, text: String) {
                val stock = Gson().fromJson(text, Stock::class.java)
                CoroutineScope(Dispatchers.IO).launch {
                    _updates.emit(stock)
                }
            }
        })
    }

    fun send(stock: Stock) {
        socket?.send(Gson().toJson(stock))
    }

    fun disconnect() {
        socket?.close(1000, null)
    }
}