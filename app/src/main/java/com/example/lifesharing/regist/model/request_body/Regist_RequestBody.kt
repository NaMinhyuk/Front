package com.example.lifesharing.regist.model.request_body

import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody

data class ProductRegisterRequestBody(
    var categoryId : Int?=null,
    var name : String?=null,
    var content : String?=null,
    var dayPrice : Int?=null,
    var hourPrice : Int?=null,
    var deposit : Int?=null,
    var lendingPeriod : String?=null,
)


var productName: MutableLiveData<String> = MutableLiveData("")
var day_price: MutableLiveData<String> = MutableLiveData("")
var time_price: MutableLiveData<String> = MutableLiveData("")
var deposit : MutableLiveData<String> = MutableLiveData("")
var lendingPeriod : MutableLiveData<String> = MutableLiveData("3.31(월)-4.6(금)(4일)")
var product_text : MutableLiveData<String> = MutableLiveData("")
