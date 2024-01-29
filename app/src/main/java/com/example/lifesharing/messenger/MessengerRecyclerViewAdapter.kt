package com.example.lifesharing.messenger

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.messenger.model.MessengerRoomItem

class MessengerRecyclerViewAdapter(messengerRecyclerViewInterface: MessengerRecyclerViewInterface): RecyclerView.Adapter<MessengerViewHoler>() {


    var globalApplication: GlobalApplication = GlobalApplication()

    private var itemList = ArrayList<MessengerRoomItem>()

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

        val currentUser = this.itemList[position]

//        holder.itemView.setOnClickListener {
//            val intent = Intent(globalApplication, MessengerDetailActivity::class.java)
//
//            // 넘길 데이터
//            intent.putExtra("name", currentUser.username)
//            intent.putExtra("userId", currentUser.userId)
//            intent.putExtra("productId", currentUser.productId)
//
//            globalApplication.startActivity(intent)
//        }

        holder.bind(this.itemList[position])
    }

    // 외부에서 데이터 넘기기
    fun submitList(itemList: ArrayList<MessengerRoomItem>) {
        this.itemList = itemList
    }


}