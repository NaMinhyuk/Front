package com.example.lifesharing.login.model.request_body

data class RegisterRequestBody(
    val email: String?,
    val password: String?,
    val name: String?,
    val phone: String?,
    val locationDTO:  LocationDTO?,
)

data class LocationDTO(
    val roadAddrPart1: String?,   // 도로명
    val emdNm : String?,         // 읍/면/동
    val zipNo : String?,         // 우편번호
    val admCd : String?,         // 행정구역코드
//    val rnMgtSn : String?,       // 도로명코드
//    val buldMnnm : String?,      // 건물본번
//    val detBdNmList : String?    // 건물명
)

