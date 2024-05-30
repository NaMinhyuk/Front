package com.example.lifesharing.reservation.data

data class ReservationList(
    var reservationId: Long,
    var productId: Long,
    var productName: String,
    var productImage: String,
    var filter: String,
    var startDate: String,
    var endDate: String,
    var location: String,
    var totalTime: String,
    var amount: Long,
    var deposit: Long,
    var status: String // 예약 상태 필드 추가
)