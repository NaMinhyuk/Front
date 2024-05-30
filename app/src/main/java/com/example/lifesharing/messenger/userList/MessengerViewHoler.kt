package com.example.lifesharing.messenger.userList

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentChatRoomItemBinding
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResult
import com.example.lifesharing.messenger.userList.MessengerRecyclerViewInterface
import com.example.lifesharing.util.DateUtils

/** 채팅방 리스트에 필요한 데이터를 뷰에 로드 */
class MessengerViewHoler(itemView: View, messengerRecyclerViewInterface: MessengerRecyclerViewInterface) //생성자 부분
    : RecyclerView.ViewHolder(itemView) ,
        View.OnClickListener // 상속부분
{

    val TAG: String = "로그"


    var binding = FragmentChatRoomItemBinding.bind(itemView)

    private val itemProfileImg = binding.chatRoomItemProfileImg
    private val itemUser = binding.chatRoomItemUsername
    private val itemLocation = binding.chatRoomItemLocation
    private val itemTime = binding.chatRoomItemTime
    private val itemImg = binding.chatRoomItemImg
    private val itemLastChat = binding.chatRoomItemLastChat

    private var messengerRecyclerViewInterface : MessengerRecyclerViewInterface? = null

    init {
        itemView.setOnClickListener(this)    // itemView의 클릭 리스너로 this (본 클래스 인스턴스)를 지정
        this.messengerRecyclerViewInterface = messengerRecyclerViewInterface
    }

    fun bind(messengerRoomListResult: MessengerRoomListTempResult) {    // MessengerRoomListTempResult 객체를 파라미터로 받아 각 뷰에 데이터를 설정
        itemUser.text = messengerRoomListResult.opponentName
        itemLocation.text = messengerRoomListResult.opponentAddress
        itemTime.text = DateUtils.formatChatTimestamp(messengerRoomListResult.updatedAt.toString())  // 날짜 포맷팅 적용
        itemLastChat.text = messengerRoomListResult.lastMessage

        // last Chat을 따로 chat 구현 후 넣어야 할듯
        Glide
            .with(GlobalApplication.instance)
            .load(messengerRoomListResult.productId)
            .centerCrop()
            .placeholder(R.drawable.profile_userimg)
            .into(itemProfileImg)  // 사용자 프로필 이미지

        Glide
            .with(GlobalApplication.instance)
            .load(messengerRoomListResult.productId)
            .centerCrop()
            .placeholder(R.drawable.camera_dummy)
            .into(itemImg)        // 제품 이미지
    }

    override fun onClick(p0: View?) {
        // 채팅 리스트 아이템 클릭 리스너
        try {
            this.messengerRecyclerViewInterface?.onItemClicked(adapterPosition)
        } catch (e: Exception) {
            Log.d(TAG, "onClick: ${e.message}")
        }

    }

}