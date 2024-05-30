package com.example.lifesharing.reservation.api

import com.google.gson.annotations.SerializedName

data class ReservationResponseBody(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("result") var result: ReservationResult,
)

data class ReservationResult(
    @SerializedName("appliedFilter") val appliedFilter: String?,
    @SerializedName("reservationList") var reservationList: List<ReservationList>,
)

data class ReservationList(
    @SerializedName("reservationId") var reservationId: Long,
    @SerializedName("productId") var productId: Long,
    @SerializedName("productName") var productName: String,
    @SerializedName("productImage") var productImage: String,
    @SerializedName("filter") var filter: String,
    @SerializedName("startDate") var startDate: String,
    @SerializedName("endDate") var endDate: String,
    @SerializedName("location") var location: String,
    @SerializedName("totalTime") var totalTime: String,
    @SerializedName("amount") var amount: Long,
    @SerializedName("deposit") var deposit: Long,
    @SerializedName("status") var status: String,
)
