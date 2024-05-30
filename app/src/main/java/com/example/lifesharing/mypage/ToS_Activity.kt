package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityTosBinding

// Terms of Service (서비스 이용약관)
class ToS_Activity  : AppCompatActivity() {

    private lateinit var binding: ActivityTosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTosBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기
        binding.tosBackIv.setOnClickListener {
            finish()
        }
    }
}