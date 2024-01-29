package com.example.lifesharing.messenger

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentChatRoomItemBinding
import com.example.lifesharing.databinding.FragmentMessengerBinding
import com.example.lifesharing.messenger.model.MessengerRoomItem

class MessengerViewHoler(itemView: View,
                         messengerRecyclerViewInterface: MessengerRecyclerViewInterface) //생성자 부분
    : RecyclerView.ViewHolder(itemView) ,
        View.OnClickListener // 상속부분
{

    // custom viewholder

    var binding = FragmentChatRoomItemBinding.bind(itemView)

    private val itemProfileImg = binding.chatRoomItemProfileImg
    private val itemUser = binding.chatRoomItemUsername
    private val itemLocation = binding.chatRoomItemLocation
    private val itemTime = binding.chatRoomItemTime
    private val itemImg = binding.chatRoomItemImg

    private var messengerRecyclerViewInterface : MessengerRecyclerViewInterface? = null

    init {
        itemView.setOnClickListener(this)
        this.messengerRecyclerViewInterface = messengerRecyclerViewInterface
    }

    fun bind(messengerRoomItem: MessengerRoomItem) {
        itemUser.text = messengerRoomItem.username
        itemLocation.text = messengerRoomItem.location
        itemTime.text = messengerRoomItem.chattime

        Glide
            .with(GlobalApplication.instance)
            .load(messengerRoomItem.profile)
            .centerCrop()
            .placeholder(R.drawable.profile)
            .into(itemProfileImg)

        Glide
            .with(GlobalApplication.instance)
            .load(messengerRoomItem.itemImageUrl)
            .centerCrop()
            .placeholder(R.drawable.camera_dummy)
            .into(itemImg)
    }

    override fun onClick(p0: View?) {



        this.messengerRecyclerViewInterface?.onItemClicked(adapterPosition)
    }

}