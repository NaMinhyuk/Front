package com.example.lifesharing.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMessengerDetailWithDummyBinding
import com.example.lifesharing.service.work.ProductWork

class MessengerDetailWithDummy : AppCompatActivity() {

    val TAG: String = "로그"

    private lateinit var receiverName: String
    private lateinit var receiverId: String
    var productId: Int?=null

    lateinit var binding: ActivityMessengerDetailWithDummyBinding

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_messenger_detail_with_dummy)
        binding.activity = this
        binding.lifecycleOwner = this

        //Log.d(TAG, "onCreate: ${intent.extras!!.getString("opponentName")}")
        receiverName = intent.getStringExtra("opponentName").toString()
        receiverId = intent.getStringExtra("opponentUserId").toString() // 이 두개는 통신을 위해 쓰임
        productId = intent.getIntExtra("productId", 0) // 이것도 서버 통신을 위해 쓰임

        Log.d(TAG, "onCreate: $receiverName")
        Log.d(TAG, "onCreate: $productId")

        getProductData()

        binding.messengerDetailUsername.text = receiverName

        binding.messengerDetailBack.setOnClickListener{
            finish()
        }
    }

    private fun getProductData() {
        
        try {
            val apiCall = ProductWork(productId!!)
            apiCall.getProductInfo()
            val productData = GlobalApplication.getProductData()

            Log.d(TAG, "getProductData: ${productData!!.imageUrl?.get(0)}")

            Glide
                .with(this)
                .load(productData!!.imageUrl?.get(0))
                .placeholder(R.drawable.profile)
                .into(binding.productImg)
            Log.d(TAG, "getProductData: $productData")
            binding.productName.text = productData.name
            binding.productDetailLocation.text = productData.location
            binding.productDayPrice.text = productData.dayPrice.toString()
            binding.productHourPrice.text = productData.hourPrice.toString()

        } catch (e: Exception) {
            Log.e(TAG, "getProductData:  ${e.message}", )
        }

     
    }

}