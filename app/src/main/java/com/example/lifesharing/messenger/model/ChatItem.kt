package com.example.lifesharing.messenger.model

data class ChatItem(
    var userId: Long?=null,
    var roomId: Long?=null,
    var message: String?=null,
    var createdAt: String
)
