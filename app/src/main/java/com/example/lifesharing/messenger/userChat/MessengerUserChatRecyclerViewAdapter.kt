package com.example.lifesharing.messenger.userChat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.databinding.ChatReceiverItemBinding
import com.example.lifesharing.databinding.ChatSenderItemBinding
import com.example.lifesharing.messenger.model.ChatItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessengerUserChatRecyclerViewAdapter(private val userId: Int) :
    ListAdapter<ChatItem, RecyclerView.ViewHolder>(diffUtil) {

    private var chatList = ArrayList<ChatItem>()

    inner class SenderChatItemViewHolder(private val binding: ChatSenderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatItem) {
            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val currentTime : Long = System.currentTimeMillis()
            binding.chatSenderMessage.text = chatItem.message
            binding.chatSenderTime.text = dateFormat.format(currentTime)
        }
    }

    inner class ReceiverChatItemViewHolder(private val binding: ChatReceiverItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatItem) {
            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val currentTime : Long = System.currentTimeMillis()
            binding.chatReceiverMessage.text = chatItem.message
            binding.chatReceiverTime.text = dateFormat.format(currentTime)
        }
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<ChatItem>() {
            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem == newItem
            }
        }

        private const val MY_CHAT = 1
        private const val OTHER_CHAT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (userId == chatList[position].userId)
            MY_CHAT
        else OTHER_CHAT
    }

    override fun getItemCount(): Int {
        return chatList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MY_CHAT) {
            SenderChatItemViewHolder(
                ChatSenderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            ReceiverChatItemViewHolder(
                ChatReceiverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MY_CHAT) {
            (holder as SenderChatItemViewHolder).bind(chatList[position])
        } else {
            (holder as ReceiverChatItemViewHolder).bind(chatList[position])
        }
    }

    fun addItem(chatItem: ChatItem) {
        chatList.add(chatItem)
    }

}



