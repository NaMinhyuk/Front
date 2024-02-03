package com.example.lifesharing.login.model.request_body

data class RegisterRequestBody(
    val email: String?,
    val password: String?,
    val name: String?,
    val phone: String?,
    val locationDTO:  LocationDTO?,
)

data class LocationDTO(
    val roadAddPart1: String?,
    val emdNm : String?,
)
