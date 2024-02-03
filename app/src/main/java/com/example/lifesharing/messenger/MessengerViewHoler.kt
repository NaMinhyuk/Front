package com.example.lifesharing.messenger

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentChatRoomItemBinding
import com.example.lifesharing.databinding.FragmentMessengerBinding
import com.example.lifesharing.messenger.model.MessengerRoomItem
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResult

class MessengerViewHoler(itemView: View,
                         messengerRecyclerViewInterface: MessengerRecyclerViewInterface) //생성자 부분
    : RecyclerView.ViewHolder(itemView) ,
        View.OnClickListener // 상속부분
{

    val TAG: String = "로그"

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

    fun bind(messengerRoomListResult: MessengerRoomListTempResult) {
        itemUser.text = messengerRoomListResult.opponentName
        itemLocation.text = messengerRoomListResult.opponentAddress
        itemTime.text = messengerRoomListResult.updatedAt

        // last Chat을 따로 chat 구현 후 넣어야 할듯
        Glide
            .with(GlobalApplication.instance)
            .load(messengerRoomListResult.productId)
            .centerCrop()
            .placeholder(R.drawable.profile)
            .into(itemProfileImg)

        Glide
            .with(GlobalApplication.instance)
            .load(messengerRoomListResult.productId)
            .centerCrop()
            .placeholder(R.drawable.camera_dummy)
            .into(itemImg)
    }

    override fun onClick(p0: View?) {

        try {
            this.messengerRecyclerViewInterface?.onItemClicked(adapterPosition)
        } catch (e: Exception) {
            Log.d(TAG, "onClick: ${e.message}")
        }

    }

}