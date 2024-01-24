package com.example.lifesharing.product_register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityRegistAddBinding

class Regist_Add_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegistAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}