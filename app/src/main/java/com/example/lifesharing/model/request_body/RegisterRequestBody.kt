package com.example.lifesharing.model.request_body

data class RegisterRequestBody(
    val email: String?,
    val password: String?,
    val name: String?,
    val phone: String?
)
