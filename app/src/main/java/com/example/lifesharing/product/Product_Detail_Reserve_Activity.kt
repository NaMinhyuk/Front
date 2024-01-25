package com.example.lifesharing.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.BuildConfig
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityDetailReserveBinding

class Product_Detail_Reserve_Activity : AppCompatActivity() {

    private val BASEURLS = BuildConfig.BASE_URLS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_reserve)

        val binding = ActivityDetailReserveBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}