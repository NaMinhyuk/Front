package com.example.lifesharing.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMessengerDetailBinding
import com.example.lifesharing.messenger.viewModel.MessengerDetailViewModel

class MessengerDetailActivity : AppCompatActivity(), MessengerDataTransfer {

    private lateinit var receiverName: String
    private lateinit var receiverId: String
    private lateinit var productId: String


    val TAG: String = "데이터"

    lateinit var binding: ActivityMessengerDetailBinding

    val messengerDetailViewModel : MessengerDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_messenger_detail)
        binding.viewModel = messengerDetailViewModel
        binding.activity = this
        binding.lifecycleOwner = this


        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.messenger_tool_bar)

        // 넘어온 데이터 변수에 담기 intent를 통해 꺼내올 수 있다.
        receiverName = intent.getStringExtra("name").toString()
        receiverId = intent.getStringExtra("userId").toString()
        productId = intent.getStringExtra("productId").toString()

        binding.messengerToolBar.title = receiverName
        // 액션바에 상대방 이름 보여주는 기능 추가

        supportActionBar?.title = receiverName

        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true) // 앱바 뒤로가기 버튼 만들기

    }

    override fun onDataTransfer(data: String) {
        Log.d(TAG, "Received data in Activity $data ")
    }
}