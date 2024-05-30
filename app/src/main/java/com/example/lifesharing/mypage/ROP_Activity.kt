package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityRopBinding

// Review Operation Policy (리뷰 운영 정책)
class ROP_Activity  : AppCompatActivity() {

    private lateinit var binding: ActivityRopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRopBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기
        binding.ropBackIv.setOnClickListener {
            finish()
        }
    }
}