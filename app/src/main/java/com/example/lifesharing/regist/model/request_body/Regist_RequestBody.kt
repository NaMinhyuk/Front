package com.example.lifesharing.regist.model.request_body

import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody

data class ProductRegisterRequestBody(
    var categoryId : String?=null,
    var name : String?=null,
    var content : String?=null,
    var dayPrice : Int?=null,
    var hourPrice : Int?=null,
    var deposit : Int?=null,
    var lendingPeriod : String?=null,
)

