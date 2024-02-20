package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityMypageUseageBinding
import com.example.lifesharing.databinding.MyPageMainListBinding
import com.example.lifesharing.search.SearchActivity

class UseageActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMypageUseageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.useageImg1Review.setOnClickListener {
//            val intent = Intent(this, 리뷰::class.jave)
            startActivity(intent)
        }
        binding.useageImg2Review.setOnClickListener {
//            val intent = Intent(this, 리뷰::class.jave)
            startActivity(intent)
        }
        binding.useageImg3Review.setOnClickListener {
//            val intent = Intent(this, 리뷰::class.jave)
            startActivity(intent)
        }
        binding.useageImg4Review.setOnClickListener {
//            val intent = Intent(this, 리뷰::class.jave)
            startActivity(intent)
        }
        binding.useageImg5Review.setOnClickListener {
//            val intent = Intent(this, 리뷰::class.jave)
            startActivity(intent)
        }
        binding.useageImg6Review.setOnClickListener {
//            val intent = Intent(this, 리뷰::class.jave)
            startActivity(intent)
        }
        binding.useageImg7Review.setOnClickListener {
//            val intent = Intent(this, 리뷰::class.jave)
            startActivity(intent)
        }

        binding.useageBackbtn.setOnClickListener {
            finish()
        }
        binding.useageSearchbtn.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }
    }
}