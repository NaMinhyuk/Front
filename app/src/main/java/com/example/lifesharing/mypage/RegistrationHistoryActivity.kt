package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.Product_Detail_Activity
import com.example.lifesharing.databinding.ActivityRegistrationHistoryBinding
import com.example.lifesharing.search.SearchActivity

class RegistrationHistoryActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegistrationHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrationHistory1.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }
        binding.registrationHistory2.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }
        binding.registrationHistory3.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }
        binding.registrationHistory4.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }
        binding.registrationHistory5.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }
        binding.registrationHistory6.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }
        binding.registrationHistory7.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }

        binding.useageBackbtn.setOnClickListener {
            finish()
        }
        binding.useageSearchbtn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}