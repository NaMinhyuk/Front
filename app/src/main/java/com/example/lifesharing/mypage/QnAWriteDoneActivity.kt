package com.example.lifesharing.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityQnAwriteDoneBinding

class QnAWriteDoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQnAwriteDoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQnAwriteDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

         // 뒤로가기
         binding.btnWriteDoneBack.setOnClickListener {
             finish()
         }

        // 홈으로
        binding.btnGoHome.setOnClickListener {
            finish()
        }
    }
}