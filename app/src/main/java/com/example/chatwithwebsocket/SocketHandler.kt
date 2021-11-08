package com.example.chatwithwebsocket

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {
    private lateinit var socket: Socket

    @Synchronized
    fun setSocket(){
        try{
            socket = IO.socket(Constant.URL_SERVER)
        }catch (e: URISyntaxException){

        }
    }

    @Synchronized
    fun getSocket(): Socket{
        return socket
    }

    @Synchronized
    fun setConnection(){
        socket.connect()
    }

}