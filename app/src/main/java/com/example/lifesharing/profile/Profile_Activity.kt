package com.example.lifesharing.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityProductDetailBinding
import com.example.lifesharing.databinding.ActivityProfileBinding

class Profile_Activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}