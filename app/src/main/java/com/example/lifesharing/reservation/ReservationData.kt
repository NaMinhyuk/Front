package com.example.lifesharing.reservation

import android.widget.ImageView
import com.example.lifesharing.product.model.response_body.DetailResult
import com.google.gson.annotations.SerializedName

data class ReservationData(
    val title: String,
    val location: String,
    val subtitle1: String,
    val subtitle2: String,
)

data class ReservationResponseBody(
    @SerializedName("isSuccess")val isSuccess: Boolean?,
    @SerializedName("code")var code:String?,
    @SerializedName("message") var message : String?,
    @SerializedName("result") var result : ReservationResult? = ReservationResult(),
)

data class ReservationResult(
    @SerializedName("appliedFilter") var appliedFilter:String?=null,
    @SerializedName("reservationList") var reservationList : List<reservationdataList>? = null,
)

data class reservationdataList(
    @SerializedName("reservationd") var reservationd : Long?=null,
    @SerializedName("productId") var productId : Long?=null,
    @SerializedName("productName") var productName: Long?= null,
    @SerializedName("ProductImage") var ProductImage:String?=null,
    @SerializedName("filter") var filter:String?=null,
    @SerializedName("startDate") var startDate:String?=null,
    @SerializedName("endDate") var endDate:String?=null,
    @SerializedName("location") var location:String?=null

)



