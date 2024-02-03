package com.example.lifesharing.messenger

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.lifesharing.BuildConfig
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMessengerDetailWithDummyBinding
//import com.example.lifesharing.service.work.ProductWork
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

class MessengerDetailWithDummy : AppCompatActivity() {

    val TAG: String = "로그"

    private lateinit var receiverName: String
    private lateinit var receiverId: String
    var productId: Int?=null
    var roomId: Int?=null

    val BASE_URL = "wss://dev.lifesharing.shop/stomp/chat"

    lateinit var stompClient: StompClient


    lateinit var binding: ActivityMessengerDetailWithDummyBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BASE_URL)


        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_messenger_detail_with_dummy)
        binding.activity = this
        binding.lifecycleOwner = this

        //Log.d(TAG, "onCreate: ${intent.extras!!.getString("opponentName")}")
        receiverName = intent.getStringExtra("opponentName").toString()
        receiverId = intent.getStringExtra("opponentUserId").toString() // 이 두개는 통신을 위해 쓰임
        productId = intent.getIntExtra("productId", 0) // 이것도 서버 통신을 위해 쓰임
        roomId = intent.getIntExtra("roomId", 0)

        Log.d(TAG, "current Room Id : $roomId")

        runStomp(roomId!!)

        Log.d(TAG, "onCreate: $receiverName")
        Log.d(TAG, "onCreate: $productId")

        getProductData()

        binding.messengerDetailUsername.text = receiverName

        binding.messengerDetailBack.setOnClickListener{
            finish()
        }
    }

    @SuppressLint("CheckResult")
    fun runStomp(roomId: Int) {
        stompClient.connect()


        stompClient.topic("/sub/chat/room/${roomId}").subscribe{ topicMessage ->
            Log.d(TAG, "runStomp: ${topicMessage.payload}")
        }

        stompClient.lifecycle().subscribe{  lifecycleEvent ->
            when(lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.d(TAG, "runStomp: opened")
                }

                LifecycleEvent.Type.CLOSED -> {
                    Log.d(TAG, "runStomp: closed")
                }

                LifecycleEvent.Type.ERROR -> {
                    Log.e(TAG, "${lifecycleEvent.exception.message}", )
                }
                else -> {
                    Log.d(TAG, "runStomp: ${lifecycleEvent.message}")
                }
            }
        }
    }

    fun sendStomp(msg: String, roomId: Int, userId: Int) {
        val data = JSONObject()
        data.put("messageType", "CHAT")
        data.put("userId", userId.toString())
        data.put("message", msg)

        stompClient.send("/pub/chat/message", data.toString()).subscribe()
        Log.d(TAG, "sendStomp: $msg")
    }

    private fun getProductData() {

//        try {
//            val apiCall = ProductWork(productId!!)
//            apiCall.getProductInfo()
//            val productData = GlobalApplication.getProductData()
//
//            Log.d(TAG, "getProductData: ${productData!!.imageUrl?.get(0)}")
//
//            Glide
//                .with(this)
//                .load(productData!!.imageUrl?.get(0))
//                .placeholder(R.drawable.profile)
//                .into(binding.productImg)
//            Log.d(TAG, "getProductData: $productData")
//            binding.productName.text = productData.name
//            binding.productDetailLocation.text = productData.location
//            binding.productDayPrice.text = productData.dayPrice.toString()
//            binding.productHourPrice.text = productData.hourPrice.toString()
//
//        } catch (e: Exception) {
//            Log.e(TAG, "getProductData:  ${e.message}", )
//        }


    }

}