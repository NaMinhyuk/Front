package com.example.lifesharing.product.data

data class UpdateRequestData (
    var categoryId : String,
    var name : String,
    var content : String,
    var dayPrice : Int,
    var hourPrice : Int,
    var deposit : Int,
    var lendingPeriod : String
)