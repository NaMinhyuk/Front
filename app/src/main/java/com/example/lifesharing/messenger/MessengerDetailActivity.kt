package com.example.lifesharing.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.printservice.CustomPrinterIconCallback
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.MainActivity
import com.example.lifesharing.R
import com.example.lifesharing.common.response_body.ProductIdResponseBody
import com.example.lifesharing.databinding.ActivityMessengerDetailBinding
import com.example.lifesharing.messenger.userList.MessengerFragment
import com.example.lifesharing.messenger.viewModel.MakeRoomViewModel
import com.example.lifesharing.messenger.viewModel.MessengerDetailViewModel
import com.example.lifesharing.product.view_model.ProductDetailViewModel
import com.example.lifesharing.service.work.MessengerRoomListWork
import ua.naiksoftware.stomp.Stomp
import java.text.NumberFormat
import java.util.Locale

class MessengerDetailActivity : AppCompatActivity(), MessengerDataTransfer {

    private lateinit var receiverName: String
    private lateinit var receiverId: String
    private lateinit var productId: String

    val TAG: String = "데이터"
    val CHAT_TAG: String = "채팅방 생성 로그"

    lateinit var binding: ActivityMessengerDetailBinding

    val messengerDetailViewModel : MessengerDetailViewModel by viewModels()
    private lateinit var makeRoomViewModel: MakeRoomViewModel
    private lateinit var productViewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_messenger_detail)
        binding.viewModel = messengerDetailViewModel
        binding.activity = this
        binding.lifecycleOwner = this

        val sender = intent.getLongExtra("sender", -1)   // 메시지는 보내는 사람 아이디
        val sellerName = intent.getStringExtra("sellerName")
        val product = intent.getLongExtra("productId", -1)  // 해당 제품의 아이디


        Log.d(CHAT_TAG, "전달받은 Sender Id  : ${sender}")


        makeRoomViewModel = ViewModelProvider(this).get(MakeRoomViewModel::class.java)

        // 채팅방 처리 로직 - 이미 생성된 채팅방이라면 채팅방을 생성하지 않음
        makeRoomViewModel.handleChatRoomCreation(sender, product)

        // makeRoomViewModel.createChatRoom(sender, product)

        makeRoomViewModel.roomCreationStatus.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                Log.d(CHAT_TAG, "채팅방 생성 성공")
                // 채팅방 화면으로 이동
            } else {
                Log.d(CHAT_TAG, "채팅방 생성 실패")
            }
        })

        // 기존 채팅방 ID 관찰
        makeRoomViewModel.existingRoomId.observe(this, Observer { roomId ->
            roomId?.let {
                Log.d(TAG, "기존 채팅방으로 이동: $roomId")
                // 기존 채팅방으로 이동 로직 추가 필요
            }
        })

        makeRoomViewModel.errorMessage.observe(this, Observer { message ->
            Log.d(CHAT_TAG, message)
        })

        // 제품 정보 로딩
        productViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        productViewModel.productData.observe(this, Observer { data ->
            updateUI(data)
        })

        if (product > 0) {
            productViewModel.loadProductData(product)
        }

        // 넘어온 데이터 변수에 담기 intent를 통해 꺼내올 수 있다.
        receiverName = intent.getStringExtra("name").toString()
        receiverId = intent.getStringExtra("userId").toString()
        productId = intent.getStringExtra("productId").toString()

        binding.userNicknameTv.text = sellerName

        // 뒤로가기 -> 채팅방 리스트로 넘어감
        binding.chatBackBtn.setOnClickListener {
            finish()
        }
    }

    fun initStomp() {

        val wsServerUrl : String = "example";
        val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsServerUrl)

        stompClient.connect()
    }

    override fun onDataTransfer(data: String) {
        Log.d(TAG, "Received data in Activity $data ")
    }

    private fun updateUI(data: ProductIdResponseBody?) {
        data?.result?.let {
            // 가격 천 단위로 끊기
            val formatter = NumberFormat.getNumberInstance(Locale.US)

            binding.chatLocationInfo.text = it.location
            binding.chatProcutName.text = it.name
            binding.chatProductDayPrice.text = formatter.format(it.dayPrice)
            binding.chatProductHourPrice.text = formatter.format(it.hourPrice)
            Glide.with(this).load(it.imageUrl?.get(0)).into(binding.chatProductIv)
        }
    }


}