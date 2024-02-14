package com.example.lifesharing.product.data

import java.time.LocalDateTime

data class DetailProductItemData(
    val productId: Long?=null,
    val categoryId: Long?=null,
    val userId: Long?=null,
    val categoryName: String?=null,
    val imageUrl: String?=null,
    val name: String?=null,
    val score: Int?=null,
    val reviewCount: Int?=null,
    val deposit: Int?=null,
    val dayPrice: Int?=null,
    val hourPrice: Int?=null,
    val isLiked: Boolean?=null,
    val content: String?=null,
    val userNickname: String?=null,
    val userImage: String?=null
)

data class DeetailReviewItemData(
    val reviewId: Long?=null,
    val userId: Long?=null,
    val nickName: String?=null,
    val profileUrl: String?=null,
    val createdAt: LocalDateTime?=null,
    val lentDay: String?=null,
    val imageList: List<Any>?=null,
    val score: Int?=null,
    val content: String?=null,
)
