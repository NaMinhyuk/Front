package com.example.lifesharing.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMessengerDetailWithDummyBinding

class MessengerDetailWithDummy : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverId: String
    private lateinit var productId: String

    lateinit var binding: ActivityMessengerDetailWithDummyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_messenger_detail_with_dummy)
        binding.activity = this
        binding.lifecycleOwner = this

        receiverName = intent.getStringExtra("name").toString()
        receiverId = intent.getStringExtra("userId").toString() // 이 두개는 통신을 위해 쓰임
        productId = intent.getStringExtra("productId").toString() // 이것도 서버 통신을 위해 쓰임

        binding.messengerDetailUsername.text = receiverName

        binding.messengerDetailBack.setOnClickListener{
            finish()
        }
    }
}