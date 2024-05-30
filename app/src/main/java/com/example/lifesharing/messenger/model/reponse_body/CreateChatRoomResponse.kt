package com.example.lifesharing.messenger.model.response_body

import com.google.gson.annotations.SerializedName

data class CreateChatRoomResponse(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: CreateChatRoomResult
)

data class CreateChatRoomResult (
    @SerializedName("roomId") var roomId: Long,
    @SerializedName("senderId") var senderId: Long,
    @SerializedName("receiverId") var receiverId: Long,
)
