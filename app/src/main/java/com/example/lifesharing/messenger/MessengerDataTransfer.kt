package com.example.lifesharing.messenger

interface MessengerDataTransfer {

    fun onDataTransfer(data: String)

}

data class ProductResultDTO(
    val productId: Int,
    val name: String,
    val location: String,
    val deposit: Int,
    val dayPrice: Int,
    val score: Int?,
    val reviewCount: Int,
    val imageUrl: String?
)

data class Result(
    val productResultDTOList: List<ProductResultDTO>
)

data class MessengerRoomItem(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
)