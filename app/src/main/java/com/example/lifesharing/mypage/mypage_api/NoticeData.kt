package com.example.lifesharing.mypage.mypage_api

import com.google.gson.annotations.SerializedName

data class NoticeResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("NoticeResultList") val noticeResultList: NoticeResultList,
)

data class NoticeResultList(
    @SerializedName("NoticeResultList") val noticeResult: List<NoticeResult>,
)

data class NoticeResult(
    @SerializedName("hasNext") val hasNet : Boolean,
)
