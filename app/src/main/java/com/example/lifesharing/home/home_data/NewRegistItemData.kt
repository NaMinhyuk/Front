package com.example.lifesharing.home.home_data

data class NewRegistItemData(
    val productId : Long,
    val img: String,
    val location: String,
    val score: Int,
    val reviewCount: Int,
    val name: String,
    val deposit: Int,
    val dayPrice: Int
)
