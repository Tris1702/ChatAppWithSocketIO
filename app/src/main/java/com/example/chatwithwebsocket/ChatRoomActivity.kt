package com.example.chatwithwebsocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatwithwebsocket.databinding.ActivityChatRoomBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ChatRoomActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var binding: ActivityChatRoomBinding
    private val viewModel: MessageViewModel by lazy { MessageViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get userName
        intent.extras?.let{
            userName = it.getString("name", "Someone")
        }

        //connect to server
        viewModel.exchangeWithServer(userName)

        val adapter = MessageAdapter()
        lifecycleScope.launch(Dispatchers.Main) {
            with(binding) {
                Timber.d("Set up adapter")
                rcvChatArea.adapter = adapter
                rcvChatArea.layoutManager =
                    LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

                //send event
                btnSend.setOnClickListener {
                    if (edtMessage.text.isNotBlank()) {
                        Timber.d("Send message to server")
                        viewModel.sendMessageToServer(userName, edtMessage.text.toString())
                        rcvChatArea.scrollToPosition(adapter.itemCount-1)
                        edtMessage.text = null
                    }
                }
            }
        }

        viewModel.messages.observe(this){
            adapter.messages = it
        }
    }
}