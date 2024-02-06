package com.example.lifesharing.messenger.userList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentMessengerBinding
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResult
import com.example.lifesharing.messenger.userChat.MessengerDetailWithDummy


class MessengerFragment : Fragment(), MessengerRecyclerViewInterface {

    val TAG: String = "로그"
    lateinit var binding: FragmentMessengerBinding
    var itemList = ArrayList<MessengerRoomListTempResult>()
    private lateinit var messengerRecyclerViewAdapter: MessengerRecyclerViewAdapter
    var chatRoomList: ArrayList<MessengerRoomListTempResult>?=null

    // 리스트 리턴값 그냥 앱내부 DB에 다 때려넣고 꺼내와서 onCreateView의 내부에 넣으면 될듯

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        chatRoomList = GlobalApplication.getData()

        // 어답터 인스턴스 생성
        messengerRecyclerViewAdapter = MessengerRecyclerViewAdapter(this)
        messengerRecyclerViewAdapter.submitList(chatRoomList!!)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messenger, container, false)

        binding.messengerListRecyclerview.apply {
            // 리사이클러뷰 설정
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = messengerRecyclerViewAdapter
        }

        return binding.root
    }

    override fun onItemClicked(position: Int) {
        Log.d(TAG, "MessengerFragment - onItemClicked() called")

        //this.itemList[position]

//        var name: String? = null
//
//        val title: String = this.itemList[position].opponentName ?: ""

        val intent = Intent(activity, MessengerDetailWithDummy::class.java)

        Log.d(TAG, "onItemClicked: ${chatRoomList!![position].productId}")

        // 넘길 데이터
        intent.putExtra("opponentName", chatRoomList!![position].opponentName)
        intent.putExtra("opponentUserId", chatRoomList!![position].receiverId)
        intent.putExtra("productId", chatRoomList!![position].productId)
        intent.putExtra("roomId", chatRoomList!![position].roomId)
        
        startActivity(intent)
    }


}