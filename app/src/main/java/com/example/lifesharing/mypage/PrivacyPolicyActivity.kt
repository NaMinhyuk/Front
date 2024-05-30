package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityPrivacyPolicyBinding

// Privacy Policy (개인정보 처리지침)
class PrivacyPolicyActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기
        binding.privacyBackIv.setOnClickListener {
            finish()
        }
    }
}