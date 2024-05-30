package com.example.lifesharing.messenger.model.response_body

import com.google.gson.annotations.SerializedName

data class ChatListResponse(
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: ArrayList<ChatList>
)

data class ChatList(
    @SerializedName("roomId") var roomId: Long,
    @SerializedName("senderId") var senderId: Long,
    @SerializedName("message") var message: String,
    @SerializedName("createdAt") var createdAt: String,
)
