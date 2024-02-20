package com.example.lifesharing.product.model.response_body

import com.google.gson.annotations.SerializedName

data class ProductMenuResponseBody (
    @SerializedName("isSuccess") val isSuccess: Boolean?=null,
    @SerializedName("code") val code: String?=null,
    @SerializedName("message") val message: String?=null,
    @SerializedName("result") val ProductMenuResultDTOList: ProductMenuResultDTOList?=null,
)

data class ProductMenuResultDTOList(
    @SerializedName("productResultDTOList") val ProductMenuResultDTO: ArrayList<ProductMenuResultDTO>?=null,
)

data class ProductMenuResultDTO(
    @SerializedName("productId") val productId: Int?=null,
    @SerializedName("name") val name: String?=null,
    @SerializedName("location") val location: String?=null,
    @SerializedName("deposit") val deposit: Int?=null,
    @SerializedName("dayPrice") val dayPrice: Int?=null,
    @SerializedName("score") val score: Int?=null,
    @SerializedName("reviewCount") val reviewCount: Int?=null,
    @SerializedName("image_url") val imageUrl: String?=null
)
