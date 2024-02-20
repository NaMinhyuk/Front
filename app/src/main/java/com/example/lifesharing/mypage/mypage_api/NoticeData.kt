package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName
import java.util.Date

data class NoticeDataResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("NoticeResultDTOList") val noticeResultDTOList: NoticeResultDTOList,
)

data class NoticeResultDTOList(
    @SerializedName("NoticeResultDTOList") val noticeResultDTO: List<NoticeResultDTO>,
)

data class NoticeResultDTO(
    @SerializedName("noticeId") val noticeId : Int,
    @SerializedName("title") val title : Boolean,
    @SerializedName("body") val body : String,
    @SerializedName("createAt") val createAt : Date,
    @SerializedName("updateAt") val updateAt : Date
)
