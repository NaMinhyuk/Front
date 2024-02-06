package com.example.lifesharing.messenger.model.response_body

import com.google.gson.annotations.SerializedName

data class MessengerRoomListTempResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: ArrayList<MessengerRoomListTempResult>
)

data class MessengerRoomListTempResult(
    @SerializedName("roomId") var roomId: Int?=null,
    @SerializedName("senderId") var senderId: Int?=null,
    @SerializedName("receiverId") var receiverId: Int?=null,
    @SerializedName("productId") var productId: Int?=null,
    @SerializedName("opponentName") var opponentName: String?=null,
    @SerializedName("opponentAddress") var opponentAddress: String?=null,
    @SerializedName("lastMessage") var lastMessage: String?=null,
    @SerializedName("updatedAt") var updatedAt: String?=null
)
