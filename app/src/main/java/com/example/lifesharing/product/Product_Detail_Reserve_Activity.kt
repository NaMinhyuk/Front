package com.example.lifesharing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityDetailReserveBinding
import com.example.lifesharing.databinding.ActivityProductDetailBinding

class Product_Detail_Reserve_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_reserve)

        val binding = ActivityDetailReserveBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}