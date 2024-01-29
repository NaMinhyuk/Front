package com.example.lifesharing.product

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.MainActivity
import com.example.lifesharing.databinding.ActivityDetailReserveFinishBinding

class Product_Detail_Reserve_Finish_Activity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailReserveFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reserveFinishBackbtn.setOnClickListener {
            finish()
        }
        binding.reserveFinishHomebtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}