package com.example.lifesharing.product.model.response_body

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ProductPayResponseBody(
    @SerializedName("isSuccess") var isSuccess: Boolean?=null,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: ProductPayResult?= ProductPayResult(),
)

data class ProductPayResult(
    @SerializedName("reservationId") var reservationId : Boolean?=null,
    @SerializedName("payType") var payType : Int?=null,
    @SerializedName("paymentType") var paymentType : String?=null,
    @SerializedName("amount") var amount : Long?=null,
    @SerializedName("orderName") var orderName : Long?=null,
    @SerializedName("orderId") var orderId : String?=null,
    @SerializedName("userEmail") var userEmail : String?=null,
    @SerializedName("successUrl") var successUrl : String?=null,
    @SerializedName("failUrl") var failUrl: String?= null,
    @SerializedName("failReason") var failReason: String?=null,
    @SerializedName("cancelYN") var cancelYN:String?=null,
    @SerializedName("cancelReason") var cancelReason:String?=null,
    @SerializedName("createdAt") var createdAt:String?=null
)

