package com.example.lifesharing.messenger.model.response_body

import com.google.gson.annotations.SerializedName

data class MessengerRoomListResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: List<MessengerRoomListResult>?=null
)


data class MessengerRoomListResult(
    @SerializedName("roomId") var roomId: Int?=null,
    @SerializedName("senderId") var senderId: Int?=null,
    @SerializedName("receiverId") var receiverId: Int?=null
)
