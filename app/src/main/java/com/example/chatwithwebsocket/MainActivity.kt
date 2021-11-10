package com.example.chatwithwebsocket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.chatwithwebsocket.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, ChatRoomActivity::class.java)

        with(binding){
            edtUserName.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrBlank()){
                    btnEnterChatRoom.visibility = View.VISIBLE
                }
                else{
                    btnEnterChatRoom.visibility = View.GONE
                }
            }
            btnEnterChatRoom.setOnClickListener{
                if (edtUserName.text.isNullOrBlank()){
                    Toast.makeText(applicationContext,"Please enter your name", Toast.LENGTH_SHORT).show()
                } else {
                    Constant.userName = edtUserName.text.toString()
                    intent.putExtra("name", edtUserName.text.toString())
                    startActivity(intent)
                }
            }
        }
    }
}