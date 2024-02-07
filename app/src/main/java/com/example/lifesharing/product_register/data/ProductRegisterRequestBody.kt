package com.example.lifesharing.product_register.data

data class ProductRegisterRequestBody(
    var categoryId : Int?=null,
    var name : String?=null,
    var content : String?=null,
    var dayPrice : Int?=null,
    var hourPrice : Int?=null,
    var deposit : Int?=null,
    var lendingPeriod : String?=null,
)
