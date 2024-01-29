package com.example.lifesharing.messenger

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentChatRoomItemBinding
import com.example.lifesharing.databinding.FragmentMessengerBinding
import com.example.lifesharing.messenger.model.MessengerRoomItem


class MessengerFragment : Fragment(), MessengerRecyclerViewInterface {

    val TAG: String = "로그"
    lateinit var binding: FragmentMessengerBinding
    var itemList = ArrayList<MessengerRoomItem>()
    private lateinit var messengerRecyclerViewAdapter: MessengerRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        for (i in 1..5) {
            val myItem = MessengerRoomItem(
                username = "hayeong$i",
                userId = i,
                productId = i+1,
                location = "  울산 무거동  ",
                profile = "",
                itemImageUrl = null,
                chattime = "2시간전"
            )
            this.itemList.add(myItem)
        }

        // 어답터 인스턴스 생성
        messengerRecyclerViewAdapter = MessengerRecyclerViewAdapter(this)
        messengerRecyclerViewAdapter.submitList(this.itemList)
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

        val currentUser = this.itemList[position]

        var name: String? = null

        val title: String = this.itemList[position].username ?: ""

        val intent = Intent(activity, MessengerDetailWithDummy::class.java)

        // 넘길 데이터
        intent.putExtra("name", currentUser.username)
        intent.putExtra("userId", currentUser.userId)
        intent.putExtra("productId", currentUser.productId)

        startActivity(intent)

        //startActivity(Intent(activity, MessengerDetailWithDummy::class.java))
    }


}