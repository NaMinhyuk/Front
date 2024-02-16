package com.example.lifesharing.reservation.data

data class NewReservationItemData(
    val img: String,
    val location: String,
    val reviewCount: Int,
    val name: String,
    val deposit: Int,
    val dayPrice: Int
)
