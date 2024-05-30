package com.example.lifesharing.messenger.userChat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lifesharing.BuildConfig
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMessengerDetailWithDummyBinding
import com.example.lifesharing.messenger.model.ChatItem
import com.example.lifesharing.messenger.model.response_body.ChatList
import com.example.lifesharing.messenger.viewModel.ChatListViewModel
import com.example.lifesharing.messenger.viewModel.MakeRoomViewModel
import com.example.lifesharing.product.view_model.ProductDetailViewModel
import org.json.JSONObject
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.text.NumberFormat
import java.util.Locale

/**
 * 채팅방 View
 */
class MessengerDetailWithDummy : AppCompatActivity() {

    val TAG: String = "로그"
    val CHAT_TAG: String = "채팅방 생성 로그"
    val BASE_URL = BuildConfig.STOMP_BASE_URL    // STOMP 프로토콜을 사용하기 위한 URL

    private lateinit var receiverName: String
    private lateinit var receiverId: String
    private var productId: Long?=null
    private var roomId: Long?=null

    lateinit var stompClient: StompClient     // 메시지 교환을 위한 STOMP 프로토콜 사용

    lateinit var binding: ActivityMessengerDetailWithDummyBinding

    private lateinit var messengerUserChatRecyclerViewAdapter: MessengerUserChatRecyclerViewAdapter
    private lateinit var productViewModel : ProductDetailViewModel
    private lateinit var makeRoomViewModel: MakeRoomViewModel
    private lateinit var chatListViewModel: ChatListViewModel

    val userId = GlobalApplication.prefs.getString("user_id", "")


    override fun onCreate(savedInstanceState: Bundle?) {

        val sender = intent.getLongExtra("sender", -1)   // 메시지는 보내는 사람 아이디
        val sellerName = intent.getStringExtra("sellerName")
        val product = intent.getLongExtra("productId", -1)  // 해당 제품의 아이디

        Log.d(CHAT_TAG, "전달받은 Sender Id  : ${sender}")

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, BASE_URL)

        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_messenger_detail_with_dummy)
        binding.activity = this
        binding.lifecycleOwner = this

        // 채팅 내용을 보여주기 위한 리사이클러뷰와 어댑터 설정
        messengerUserChatRecyclerViewAdapter = MessengerUserChatRecyclerViewAdapter(userId.toInt())
        binding.messengerChatRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MessengerDetailWithDummy, LinearLayoutManager.VERTICAL, false )
            adapter = messengerUserChatRecyclerViewAdapter
        }


        receiverName = intent.getStringExtra("opponentName").toString()
        receiverId = intent.getStringExtra("opponentUserId").toString() // 이 두개는 통신을 위해 쓰임
        productId = intent.getLongExtra("productId", -1) // 이것도 서버 통신을 위해 쓰임
        roomId = intent.getLongExtra("roomId", -1)

        Log.d(TAG, "current Room Id : $roomId")

        //
        runStomp(roomId!!, userId.toLong())

        Log.d(TAG, "onCreate: $receiverName")
        Log.d(TAG, "onCreate: $productId")
        Log.e(TAG, "userID: $userId")


        productViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        // 제품 아이디에 해당하는 제품 정보 요청
        productViewModel.loadProductData(productId!!)
        // 제품 정보를 관찰하여 화면에 필요한 정보 로드
        productViewModel.productData.observe(this, Observer { productData ->
            if (productData == null) {
                Log.d(TAG, "제품 정보를 로드할 수 없습니다.")
            } else {
                Glide
                    .with(this)
                    .load(productData!!.result?.imageUrl?.get(0))
                    .placeholder(R.drawable.profile)
                    .into(binding.productImg)
                Log.d(TAG, "getProductData: $productData")
                binding.productName.text = productData.result?.name
                binding.productDetailLocation.text = productData.result?.location

                // 가격 천 단위로 끊기
                val formatter = NumberFormat.getNumberInstance(Locale.US)

                binding.productDayPrice.text = formatter.format(productData.result?.dayPrice)
                binding.productHourPrice.text = formatter.format(productData.result?.hourPrice)
            }
        })


        makeRoomViewModel = ViewModelProvider(this).get(MakeRoomViewModel::class.java)

        // 채팅방 처리 로직 - 제품에 대한 채팅방이 존재하는지 확인하는 함수 호출
        makeRoomViewModel.handleChatRoomCreation(sender, product)
        // 기존 채팅방 ID 관찰
        makeRoomViewModel.existingRoomId.observe(this, Observer { roomId ->
            roomId?.let {
                Log.d(TAG, "기존 채팅방으로 이동: $roomId")
                // 기존 채팅방으로 이동 로직 추가 필요

                this.roomId = roomId
                // 기존 채팅방이 확인되면 STOMP 연결 설정
                runStomp(roomId, userId.toLong())
            }
        })
        makeRoomViewModel.roomCreationStatus.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                Log.d(CHAT_TAG, "채팅방 생성 성공")
                // 채팅방 화면으로 이동

                runStomp(roomId!!, userId.toLong()) // 채팅방이 성공적으로 생성되었으면 STOMP 연결 설정
            } else {
                Log.d(CHAT_TAG, "채팅방 생성 실패")
            }
        })
        makeRoomViewModel.errorMessage.observe(this, Observer { message ->
            Log.d(CHAT_TAG, message)
        })


        binding.messengerDetailUsername.text = sellerName

        // 뒤로가기 버튼
        binding.messengerDetailBack.setOnClickListener{
            finish()
        }


        // 채팅 전송 버튼
        binding.chatSendBtn.setOnClickListener {
            if (roomId != null) {
                // 사용자가 입력한 텍스트를 STOMP를 통해 전송
                sendStomp(binding.chatEditText.text.toString(), userId.toLong())
                binding.chatEditText.text = null                                    // 입력 상태를 초기화
            } else {
                Log.e(TAG, "roomId가 설정되지 않았습니다. 채팅을 보낼 수 없습니다.")
            }
        }

        // 채팅 내용 뷰모델 초기화
        chatListViewModel = ViewModelProvider(this).get(ChatListViewModel::class.java)

        // 채팅 내용 로드
        roomId?.let {
            chatListViewModel.loadChatContent(it)
        }

        // observer를 통해 변경된 사항이나 업데이트를 관찰(반영)
        chatListViewModel.chatMessages.observe(this, Observer { chatItems ->
            // 채팅 내용 리스트가 null이 아닌 경우에만 업데이트
            if (chatItems.isNotEmpty()) {
                // 전달된 데이터를 어댑터에 전달해 리사이클러뷰를 업데이트. toList()는 MutableList를 List로 변환하여, 데이터 불변성을 유지
                messengerUserChatRecyclerViewAdapter.updateList(chatItems.toList())
                scrollToBottom()   // 채팅창에서 항상 최신 메시지를 보여주기 위해 함수 호출
            }
        })
    }

    // 메시지 수신
    fun runStomp(roomId: Long, userId: Long) {

        if (productId == null || roomId == null) {   // 넘겨 받은 채팅방 아이디와 사용자 아이디 유효성 체크
            Log.e(TAG, "productId or roomId is null")
            return     // null이라면 함수 종료
        }

        stompClient.connect()   // 채팅 서버 연결

        stompClient.topic("/sub/chat/room/${roomId}").subscribe { topicMessage ->
            Log.d(TAG, "runStomp: ${topicMessage.payload} 입니다!!")    // 메시지가 도착할 때마다 로그를 출력

            // 도착한 메시지(topicMessage.payload)에서 roomId와 message 내용을 추출
            val roomId = JSONObject(topicMessage.payload).getLong("roomId")
            val content = JSONObject(topicMessage.payload).getString("message")


            // 현재 시간을 LocalDateTime 객체로 가져오기
            val currentDateTime = LocalDateTime.now()

            // LocalDateTime 객체를 원하는 형식의 문자열로 포맷팅
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
            val formattedDateTime = currentDateTime.format(formatter)

            // 사용자 아이디, 채팅방 아이디, 채팅 내용, 현재 시각을 ChatItem 객체로 하나의 메시지 생성
//            var newChat = ChatItem(userId, roomId, content, formattedDateTime)
//
//            //UI 스레드에서 새 메시지를 리사이클러뷰 어댑터에 추가하고, 화면을 최신 메시지 위치로 스크롤
//            runOnUiThread {
//                messengerUserChatRecyclerViewAdapter.addItem(newChat)
//                messengerUserChatRecyclerViewAdapter.notifyItemInserted(messengerUserChatRecyclerViewAdapter.itemCount - 1)
//                scrollToBottom()
//            }

            val senderId = JSONObject(topicMessage.payload).getLong("sender")

            // 메시지가 현재 사용자에 의해 전송된 경우 UI에 표시된 임시 메시지를 갱신
            if (senderId == userId) {
                runOnUiThread {
                    messengerUserChatRecyclerViewAdapter.updateLastItem(ChatItem(senderId, roomId, content, formattedDateTime))
                    scrollToBottom()
                }
            } else {
                // 수신된 메시지가 다른 사용자로부터 온 경우 새 메시지로 추가
                val newChat = ChatItem(senderId, roomId, content, formattedDateTime)
                runOnUiThread {
                    messengerUserChatRecyclerViewAdapter.addItem(newChat)
                    messengerUserChatRecyclerViewAdapter.notifyItemInserted(messengerUserChatRecyclerViewAdapter.itemCount - 1)
                    scrollToBottom()
                }
            }
        }

        // 여기서 메시지 수신 처리 가능 상대방이 보낸건지 내가 보낸건지에 따라 리사이클러뷰 조정
        stompClient.lifecycle().subscribe{  lifecycleEvent ->
            // stompClient의 lifeCycle에 따라 필요한 조건이 있을 경우 선언 -> 로그 출력
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

    // 메시지 전송
    fun sendStomp(msg: String, userId: Long) {

        if (productId == null || roomId == null) {
            Log.e(TAG, "productId or roomId is null")
            return
        }

        // 채팅 전송 시에 필요한 데이터를 JSON 객체로 변환
        val data = JSONObject()
        data.put("messageType", "CHAT")
        data.put("sender", userId)   // 송신자 아이디
        data.put("message", msg)     // 채팅 내용
        data.put("roomId", roomId)   // 채팅방 아이디
        data.put("productId", productId)  // 제품 아이디

        // 현재 시간을 LocalDateTime 객체로 가져오기
        val currentDateTime = LocalDateTime.now()

        // LocalDateTime 객체를 원하는 형식의 문자열로 포맷팅
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val formattedDateTime = currentDateTime.format(formatter)

        // UI에 임시로 송신 메시지 추가
        val tempChat = ChatItem(userId, roomId, msg, formattedDateTime)
        runOnUiThread {
            messengerUserChatRecyclerViewAdapter.addItem(tempChat)
            messengerUserChatRecyclerViewAdapter.notifyItemInserted(messengerUserChatRecyclerViewAdapter.itemCount - 1)
            scrollToBottom()
        }

        // 구성된 JSON 데이터를 STOMP 서버로 전송
        stompClient.send("/pub/chat/message", data.toString()).subscribe({
            Log.d(TAG, "채팅 전송 성공: $data")
        }, { throwable ->
            Log.e(TAG, "채팅 전송 에러", throwable)
        })
        Log.d(TAG, "sendStomp: $msg")

    }


    // 채팅창에서 항상 최신 메시지를 보여주기 위해 스크롤을 맨 아래로 이동시키는 함수
    fun scrollToBottom() {
        binding.messengerChatRecyclerview.scrollToPosition(messengerUserChatRecyclerViewAdapter.itemCount - 1)
    }
}
