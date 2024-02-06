package com.example.lifesharing.messenger.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResult
import com.example.lifesharing.messenger.userList.MessengerRecyclerViewInterface
import com.example.lifesharing.messenger.userList.MessengerViewHoler

class MessengerRecyclerViewAdapter(messengerRecyclerViewInterface: MessengerRecyclerViewInterface): RecyclerView.Adapter<MessengerViewHoler>() {


    var globalApplication: GlobalApplication = GlobalApplication()

    private var itemList = ArrayList<MessengerRoomListTempResult>()

    private var messengerRecyclerViewInterface: MessengerRecyclerViewInterface?=null

    var showChatActivity: MutableLiveData<Boolean> = MutableLiveData(false)

    fun userChat() {
        showChatActivity.value = true
    }

    init {
        // constructor
        this.messengerRecyclerViewInterface = messengerRecyclerViewInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessengerViewHoler {
        return MessengerViewHoler(
            LayoutInflater.from(parent.context).
            inflate(R.layout.fragment_chat_room_item,
                parent,
                false), this.messengerRecyclerViewInterface!!
        )

    }


    override fun getItemCount(): Int {
        return this.itemList.size
    }

    // 뷰와 뷰홀더가 묶였을때
    override fun onBindViewHolder(holder: MessengerViewHoler, position: Int) {

        holder.bind(this.itemList[position])
    }

    // 외부에서 데이터 넘기기
    fun submitList(itemList: ArrayList<MessengerRoomListTempResult>) {
        this.itemList = itemList
    }


}