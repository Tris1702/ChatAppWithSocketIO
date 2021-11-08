package com.example.chatwithwebsocket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatwithwebsocket.databinding.ItemMessageBinding
import com.example.chatwithwebsocket.model.Message

class MessageAdapter:RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    var messages = ArrayList<Message>()
        set(value){
            field = value
//            notifyItemChanged(field.size-1)
            notifyDataSetChanged()
        }

    inner class MessageHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(message: Message){
            with(binding){
                tvUserName.text = message.userName
                tvMessage.text = message.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        return MessageHolder(binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

}