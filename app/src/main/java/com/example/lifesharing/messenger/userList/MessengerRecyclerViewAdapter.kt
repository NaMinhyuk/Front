package com.example.lifesharing.messenger.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResult
import com.example.lifesharing.messenger.userList.MessengerRecyclerViewInterface
import com.example.lifesharing.messenger.userList.MessengerViewHoler

class MessengerRecyclerViewAdapter(messengerRecyclerViewInterface: MessengerRecyclerViewInterface): RecyclerView.Adapter<MessengerViewHoler>() {

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
        return MessengerViewHoler(LayoutInflater.from(parent.context).inflate(R.layout.fragment_chat_room_item, parent, false), this.messengerRecyclerViewInterface!!
        )
    }


    override fun getItemCount(): Int {
        return this.itemList.size
    }

    // 뷰와 뷰홀더가 묶였을때
    override fun onBindViewHolder(holder: MessengerViewHoler, position: Int) {

        holder.bind(itemList[position])
    }

    // 외부에서 데이터 넘기기
    fun submitList(newItemList: ArrayList<MessengerRoomListTempResult>) {
        //this.itemList = itemList

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = itemList.size
            override fun getNewListSize(): Int = newItemList.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return itemList[oldItemPosition].roomId == newItemList[newItemPosition].roomId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return itemList[oldItemPosition] == newItemList[newItemPosition]
            }
        })

        itemList.clear()
        itemList.addAll(newItemList)
        diffResult.dispatchUpdatesTo(this)
    }
}