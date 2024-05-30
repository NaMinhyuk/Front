package com.example.lifesharing.mypage.model.response_body

import com.google.gson.annotations.SerializedName

data class UserProfileInfoResponse (
    @SerializedName("isSuccess") var isSuccess: Boolean?,
    @SerializedName("code") var code: String?=null,
    @SerializedName("message") var message: String?=null,
    @SerializedName("result") var result: InfoResult
)

data class InfoResult (
    @SerializedName("userId") var userId: Int?,
    @SerializedName("email") var email: String?,
    @SerializedName("nickname") var nickname: String?,
    @SerializedName("phone") var phone: String?,
    @SerializedName("score") var score: Int?,
    @SerializedName("profileUrl") var profileUrl: String?=null,
    @SerializedName("locationDTO") var locationDTO: LocationDTO,
)

data class LocationDTO(
    @SerializedName("roadAddress") var roadAddress: String?,
    @SerializedName("dong") var dong: String?,
    @SerializedName("zipCode") var zipCode: String?,
    @SerializedName("districtCode") var districtCode: String?,
    @SerializedName("readNameCode") var readNameCode: String?,
    @SerializedName("buildingCode") var buildingCode: String?,
    @SerializedName("buildingName") var buildingName: String?,
)