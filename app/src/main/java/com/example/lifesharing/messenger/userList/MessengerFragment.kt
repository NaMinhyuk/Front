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
import com.example.lifesharing.service.work.MessengerRoomListWork


class MessengerFragment : Fragment(), MessengerRecyclerViewInterface {

    val TAG: String = "로그"
    lateinit var binding: FragmentMessengerBinding
    private lateinit var messengerRecyclerViewAdapter: MessengerRecyclerViewAdapter   // 채팅방 리사이클러뷰 어댑터
    var chatRoomList: ArrayList<MessengerRoomListTempResult>?=null    // 채팅방 리스트를 저장할 변수

    // 리스트 리턴값 그냥 앱내부 DB에 다 때려넣고 꺼내와서 onCreateView의 내부에 넣으면 될듯

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        chatRoomList = GlobalApplication.getData() ?: arrayListOf()         // GlobalApplication에서 가져온 채팅방 리스트로  초기화
        // 가져온 채팅룸 리스트를 로그에 출력
        Log.d(TAG, "Loaded chat room list: ${chatRoomList.toString()}")

        // 어댑터 인스턴스 생성 후 초기화
        messengerRecyclerViewAdapter = MessengerRecyclerViewAdapter(this)
        messengerRecyclerViewAdapter.submitList(chatRoomList!!)

        // 해당 레이아웃 바인딩
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messenger, container, false)

        binding.messengerListRecyclerview.apply {
            // 리사이클러뷰 설정
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = messengerRecyclerViewAdapter   // 리사이클러뷰에 어댑터 연결
        }

        // 최신 채팅방 목록 가져오기 및 업데이트 함수 호출
        updateChatRoomList()

        return binding.root
    }

    // 채팅방 리스트 아이템 클릭 인터페이스 구현
    override fun onItemClicked(position: Int) {
        Log.d(TAG, "MessengerFragment - onItemClicked() called")

        val intent = Intent(activity, MessengerDetailWithDummy::class.java)

        // 넘길 데이터
        intent.putExtra("opponentName", chatRoomList!![position].opponentName)
        intent.putExtra("opponentUserId", chatRoomList!![position].receiverId)
        intent.putExtra("productId", chatRoomList!![position].productId)
        intent.putExtra("roomId", chatRoomList!![position].roomId)
        intent.putExtra("sellerName", chatRoomList!![position].opponentName)
        intent.putExtra("sender", chatRoomList!![position].senderId)

        Log.d(TAG, "onItemClicked: 제품 Id ${chatRoomList!![position].productId}")
        startActivity(intent)
    }


    // MessengerFragment로 돌아올 때마다 채팅방 리스트가 업데이트 되도록 함
    override fun onResume() {
        super.onResume()
        updateChatRoomList()  // 데이터를 새로고침하는 메서드를 호출
    }

    private fun updateChatRoomList() {
        // 데이터 소스에서 최신 채팅방 리스트를 가져옴
        val work = MessengerRoomListWork(GlobalApplication.getUserInfoData().userId.toLong())
        work.getMessengerRoomList()  // 서버에서 최신 채팅방 목록 가져오기
    }
}