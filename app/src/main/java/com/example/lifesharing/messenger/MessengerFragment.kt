package com.example.lifesharing.messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentChatRoomItemBinding
import com.example.lifesharing.databinding.FragmentMessengerBinding
import com.example.lifesharing.messenger.model.MessengerRoomItem


class MessengerFragment : Fragment() {

    lateinit var binding: FragmentMessengerBinding

    var itemList = ArrayList<MessengerRoomItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        for (i in 1..5) {
            val myItem = MessengerRoomItem(username = "hayeong $i", location = "i don't know", profile = "", itemImageUrl = null, chattime = "2시간전")
            this.itemList.add(myItem)
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messenger, container, false)
        //inding = FragmentMessengerBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        // binding.messengerRecyclerView.adapter = MessengerViewRecyclerviewAdapter()

        binding.messengerRecyclerView.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }


}