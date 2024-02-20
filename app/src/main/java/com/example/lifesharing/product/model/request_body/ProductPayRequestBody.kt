package com.example.lifesharing.product.model.request_body

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ProductPayRequestBody(
    @SerializedName("method") val method : String?=null,
    @SerializedName("paymentType") val paymentType : String?=null,
    @SerializedName("amount") val amount : Long?=null,
    @SerializedName("deposit") val deposit : Long?=null,
    @SerializedName("orderName") val orderName : String?=null,
    @SerializedName("yourSuccessUrl") val yourSuccessUrl : String?=null,
    @SerializedName("yourFailUrl") val yourFailUrl : String?=null,
    @SerializedName("startDate") val startDate : LocalDateTime?= null,
    @SerializedName("endDate") val endDate : LocalDateTime?=null,
    @SerializedName("totalTime") val totalTime :String?=null,
    @SerializedName("succeed") val succeed :String?=null,

    )
