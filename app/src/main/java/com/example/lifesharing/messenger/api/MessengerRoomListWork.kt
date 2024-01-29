package com.example.lifesharing.messenger.api

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class MessengerRoomListWork(private val senderId: Int) {

    val TAG: String = "로그"

    private val service = RetrofitAPIwithToken.retrofit()

    fun getMessengerRoomList() {

        Log.d(TAG, "getMessengerRoomList:$senderId")

        service.getMessengerRoomList(senderId)
            .enqueue(object : retrofit2.Callback<MessengerRoomListResponseBody> {
                override fun onResponse(
                    call: Call<MessengerRoomListResponseBody>,
                    response: Response<MessengerRoomListResponseBody>
                ) {
                    val result = response.body()

                    val userId = GlobalApplication.prefs.getString("user_id", "")

                    Log.d(TAG, "onResponse: $userId")

                    if (response.isSuccessful) {
                        Log.d(TAG, " chat room list $result")
                    } else {
                        Log.e(TAG, "chat room list error $result ")
                    }
                }

                override fun onFailure(call: Call<MessengerRoomListResponseBody>, t: Throwable) {
                    Log.e(TAG, "can't get chat room list ${t.message}")
                }
            })
    }

}