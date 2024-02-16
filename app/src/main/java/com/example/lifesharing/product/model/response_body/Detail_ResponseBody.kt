package com.example.lifesharing.product.model.response_body

import android.provider.ContactsContract.CommonDataKinds.Nickname
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Detail_ResponseBody(
    @SerializedName("isSuccess")val isSuccess: Boolean?,
    @SerializedName("code")var code:String?,
    @SerializedName("message") var message : String?,
    @SerializedName("result") var result : DetailResult? = DetailResult(),
)
data class DetailResult(
    @SerializedName("productId") var productId:Long?=null,
    @SerializedName("userId") var userId:Long?=null,
    @SerializedName("categoryList") var categoryList: List<String>?=null,
    @SerializedName("location") var location: String?=null,
    @SerializedName("detailLocation") var detailLocation: String?=null,
    @SerializedName("imageUrl") var imageUrl:List<String>? =null,
    @SerializedName("name") var name:String? = null,
    @SerializedName("score") var score:Int? =null,
    @SerializedName("reviewCount") var reviewCount:Int?=null,
    @SerializedName("deposit") var deposit:Int?=null,
    @SerializedName("dayPrice") var dayPrice:Int?=null,
    @SerializedName("hourPrice") var hourPrice:Int?=null,
    @SerializedName("isLiked") var isLiked:Boolean?=null,
    @SerializedName("content") var content:String?=null,
    @SerializedName("userNickname") var userNickname: String?=null,
    @SerializedName("userImage") var userImage:String?=null,
    @SerializedName("reviewList") var userList: List<DetailReviewList>? = null,
)

data class DetailReviewList(
    @SerializedName("reviewId") var reviewId:Long? = null,
    @SerializedName("userId") var userId:Long? = null,
    @SerializedName("nickName") var nickName:String?=null,
    @SerializedName("profileUrl") var profileUrl:String?=null,
    @SerializedName("createdAt") var createdAt: LocalDateTime?=null,
    @SerializedName("lentDay") var lentDay:String?=null,
    @SerializedName("imageList") var imageList: List<Any>?=null,
    @SerializedName("score") var score :Int?=null,
    @SerializedName("content") var content:String? = null
)