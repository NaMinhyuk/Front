package com.example.lifesharing.messenger

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.databinding.FragmentChatRoomItemBinding
import com.example.lifesharing.databinding.FragmentMessengerBinding
import com.example.lifesharing.messenger.model.MessengerRoomItem

class MessengerViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView)  {


    var binding = FragmentChatRoomItemBinding.bind(itemView)

    private val itemProfileImg = binding.chatRoomItemProfileImg
    private val itemUser = binding.chatRoomItemUsername
    private val itemLocation = binding.chatRoomItemLocation
    private val itemTime = binding.chatRoomItemTime
    private val itemImg = binding.chatRoomItemImg

    init {

    }

    fun bind(messengerRoomItem: MessengerRoomItem) {
        itemUser.text = messengerRoomItem.username
        itemLocation.text = messengerRoomItem.location
        itemTime.text = messengerRoomItem.chattime
    }

}