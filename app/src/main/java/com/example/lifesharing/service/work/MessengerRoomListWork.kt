package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListResponseBody
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 사용자의 채팅방 목록 조회 API */
class MessengerRoomListWork(private val userId: Long) {

    val TAG: String = "로그"

    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()

    fun getMessengerRoomList() {

        Log.d(TAG, "getMessengerRoomList: 사용자 아이디 $userId")

        // enqueue 메소드를 사용하여 비동기적으로 네트워크 요청을 수행, 응답을 처리하기 위한 콜백을 제공
        service.getMessengerRoomListTemp(userId).enqueue(object : retrofit2.Callback<MessengerRoomListTempResponseBody> {
            // 서버 응답을 받았을 때 onResponse 호출
            override fun onResponse(call: Call<MessengerRoomListTempResponseBody>, response: Response<MessengerRoomListTempResponseBody>) {
                if (response.isSuccessful && response.body() != null) {   // 응답이 성공적 & 응답 body가 존재할 때

                    val roomList = response.body()!!.result   // 응답 내용(채팅방 리스트)을 객체에 저장
                    Log.d(TAG, "채팅방 리스트: $roomList")   // 제대로 들어왔는지 로그로 확인

                    if (roomList.isNullOrEmpty()) {
                        Log.d(TAG, "사용자 ID에 해당하는 채팅방 리스트가 없습니다.: $userId")
                    }
                    // 채팅방 목록이 비어 있지 않은 경우
                    else {
                        GlobalApplication.setData(roomList)      // GlobalApplication 컨텍스트에 목록을 저장
                        GlobalApplication.prefs.setString("user_chat_list", roomList.toString())   // 로컬 저장소에 채팅방 목록을 문자열 형태로 저장
                        Log.d(TAG, "로컬 저장소에 채팅방 리스트 저장")
                    }
                } else {
                    Log.e(TAG, "Failed to fetch chat room list: ${response.errorBody()?.string() ?: "Unknown error"}")
                }
            }

            override fun onFailure(call: Call<MessengerRoomListTempResponseBody>, t: Throwable) {
                Log.e(TAG, "Failed to fetch chat room list: ${t.message}")
            }
        })
    }

}
