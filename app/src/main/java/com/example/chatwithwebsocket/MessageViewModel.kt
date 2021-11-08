package com.example.chatwithwebsocket

import android.util.Log
import androidx.lifecycle.*
import com.example.chatwithwebsocket.model.Message
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

class MessageViewModel : ViewModel() {
    private val _messages: MutableLiveData<ArrayList<Message>> = MutableLiveData()
    val messages: LiveData<ArrayList<Message>> = _messages
    private lateinit var socket: Socket
    init {
        _messages.postValue(ArrayList())
    }

    private fun addNewMessage(message: Message){
        Timber.d("Add a new Message")
        _messages.value!!.add(message)
        _messages.postValue(_messages.value)
    }
    fun exchangeWithServer(userName: String){
        viewModelScope.launch(Dispatchers.IO){
            SocketHandler.setSocket()
            SocketHandler.setConnection()

            socket = SocketHandler.getSocket()
            socket.emit("join", userName)
            socket.on("message") {arg ->
                if (arg[0] != null){
                    val data = arg[0] as JSONObject
                    try{
                        val m = Message(data.getString("senderName"), data.getString("message"))
                        Timber.d("got message from server")
                        addNewMessage(m)
                    } catch (e: JSONException){
                        Timber.e(e.toString())
                    }
                }
            }
        }
    }
    fun sendMessageToServer(userName: String, message: String){
        viewModelScope.launch(Dispatchers.IO){
            Timber.d("Send message to server")
            socket.emit("messagedetection", userName, message)
        }
    }
}