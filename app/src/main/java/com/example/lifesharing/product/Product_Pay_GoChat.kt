package com.example.lifesharing.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityProductPayGochatBinding


class Product_Pay_GoChat:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProductPayGochatBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}