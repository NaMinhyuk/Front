package com.example.lifesharing.messenger.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.messenger.model.ChatItem
import com.example.lifesharing.messenger.model.response_body.ChatList
import com.example.lifesharing.messenger.model.response_body.ChatListResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 채팅 내역 요청 API 뷰모델 */
class ChatListViewModel(application: Application) : AndroidViewModel(application) {

    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()
    val chatMessages : MutableLiveData<List<ChatItem>> = MutableLiveData()    // 채팅 내역 관찰 객체

    val TAG : String = "채팅 내용 로그"

    // 채팅방 아이디를 받아와서 해당 채팅방의 채팅 내역 요청
    fun loadChatContent(roomId : Long){
        service.loadChatContent(roomId).enqueue(object : retrofit2.Callback<ChatListResponse>{
            // 서버 응답을 받았을 때 onResponse 호출
            override fun onResponse(
                call: Call<ChatListResponse>,
                response: Response<ChatListResponse>
            ) {
                // 채팅 내역 조회 요청 성공 시
                if (response.isSuccessful) {
                    Log.d(TAG, "채팅 내용 : ${response.body()?.result}")   // 채팅 내용 확인용 로그 출력
                    response.body()?.result?.let { chatLists ->         // 서버에서 받은 채팅 리스트에서 채팅 내용을 각각의 ChatItem으로 변환
                        val chatItems = chatLists.map { ChatItem(it.senderId, it.roomId, it.message, it.createdAt) }
                        chatMessages.postValue(chatItems)    // 변환된 객체를 LiveData에 게시 -> UI업데이트에 사용
                    }
                }
                // 채팅 내역 조회 요청 실패 시
                else {
                    Log.e(TAG, "채팅 내용 로드 오류: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChatListResponse>, t: Throwable) {
                Log.e(TAG, "채팅 내용 로딩 실패 : ${t.message}")
            }

        })
    }
}