package com.example.lifesharing.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifesharing.MainActivity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityEditDoneBinding

class EditDoneActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditDoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditDoneBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            // 메인 액티비티로 인텐트 보내기
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("navigateToHome", true)  // 메인 액티비티에서 처리할 수 있도록
            startActivity(intent)
            finish()
        }
    }
}