package com.example.lifesharing.messenger.model

data class MessengerRoomItem(
    // username, profileUrl, chattime, ItemImageUrl
    var username: String? = null,
    var location: String? = null,
    var profile: String? = null,
    var chattime: String? = null,
    var itemImageUrl: String? = null
)
