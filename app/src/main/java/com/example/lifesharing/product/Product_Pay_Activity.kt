package com.example.lifesharing.product

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.charge.Charge_Activity
import com.example.lifesharing.databinding.ActivityProductPayBinding

class Product_Pay_Activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityProductPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.payChargeIv.setOnClickListener {
            val intent = Intent(this, Charge_Activity::class.java)
            startActivity(intent)
        }

        binding.payChargeTv.setOnClickListener {
            val intent = Intent(this, Charge_Activity::class.java)
            startActivity(intent)
        }
    }
}