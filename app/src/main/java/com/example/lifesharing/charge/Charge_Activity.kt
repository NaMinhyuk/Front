package com.example.lifesharing.charge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityChargeBinding

class Charge_Activity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChargeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
