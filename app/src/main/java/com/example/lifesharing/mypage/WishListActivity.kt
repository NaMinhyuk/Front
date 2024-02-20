package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.Product_Detail_Activity
import com.example.lifesharing.databinding.ActivityWishListBinding
import com.example.lifesharing.search.SearchActivity

class WishListActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWishListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wishlistImg1.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }
        binding.wishlistImg2.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }
        binding.wishlistImg3.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }
        binding.wishlistImg4.setOnClickListener {
            val intent = Intent(this, Product_Detail_Activity::class.java)
            startActivity(intent)
        }







        binding.mypickSearchbtn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.mypickBackbtn.setOnClickListener {
            finish()
        }

    }

}