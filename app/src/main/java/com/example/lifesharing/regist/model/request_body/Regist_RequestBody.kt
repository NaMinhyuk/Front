package com.example.lifesharing.regist.model.request_body

import okhttp3.MultipartBody

data class Regist_RequestBody(
    var categoryId : Int,
    var name : String,
    var content : String,
    var dayPrice : Int,
    var hourPrice : Int,
    var deposit : Int,
    var lendingPeriod : String,
    var imageUrl : MultipartBody.Part
)
