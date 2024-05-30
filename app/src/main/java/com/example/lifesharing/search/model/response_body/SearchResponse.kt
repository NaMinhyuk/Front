package com.example.lifesharing.search.model.response_body

import com.example.lifesharing.product.api.CategoryResult
import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("code") var code: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("result") var result: List<SearchResult>?
)

data class SearchResult(
    @SerializedName("product_id") var productId: Long,
    @SerializedName("name") var name: String,
    @SerializedName("location") var location: String,
    @SerializedName("deposit") var deposit: Int,
    @SerializedName("day_price") var day_price: Int,
    @SerializedName("hour_price") var hour_price: Int,
    @SerializedName("score") var score: Int,
    @SerializedName("review_count") var review_count: Int,
    @SerializedName("isLiked") var isLiked: Boolean,
    @SerializedName("image_url") var imageUrl: String
)