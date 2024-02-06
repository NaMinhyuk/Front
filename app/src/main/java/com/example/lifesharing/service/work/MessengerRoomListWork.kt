package com.example.lifesharing.service.work

import android.util.Log
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListResponseBody
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class MessengerRoomListWork(private val userId: Int) {

    val TAG: String = "로그"

    private val service = RetrofitAPIwithToken.retrofit()

    fun getMessengerRoomList() {

        Log.d(TAG, "getMessengerRoomList:$userId")

        service.getMessengerRoomListTemp(userId)
            .enqueue(object : retrofit2.Callback<MessengerRoomListTempResponseBody> {
                override fun onResponse(
                    call: Call<MessengerRoomListTempResponseBody>,
                    response: Response<MessengerRoomListTempResponseBody>
                ) {
                    val result = response.body()

                    val userId = GlobalApplication.prefs.getString("user_id", "")

                    Log.d(TAG, "onResponse: $userId")

                    if (response.isSuccessful) {
                        Log.d(TAG, " chat room list $result")
                        val resultSize = result?.result?.size

                        Log.d(TAG, "onResponse: ${result?.result}")
                        Log.d(TAG, "onResponse: ${resultSize}")

                        GlobalApplication.setData(result?.result)


                        Log.d(TAG, "messengerData: ${result?.result?.component1()}")

                        val userChatList = GlobalApplication.prefs.setString("user_chat_list",result?.result.toString())


                    } else {
                        Log.e(TAG, "chat room list error $result ")
                    }
                }

                override fun onFailure(
                    call: Call<MessengerRoomListTempResponseBody>,
                    t: Throwable
                ) {
                    Log.e(TAG, "can't get chat room list ${t.message}")
                }
            })
    }

}