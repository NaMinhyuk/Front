package com.example.lifesharing.product

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityProductDetailBinding

class Product_Detail_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailBackBtn.setOnClickListener{
            val intent = Intent(this, Product_Menu_Activity::class.java)
            startActivity(intent)
        }

        binding.detailBottomReserveBtn.setOnClickListener{
            val intent = Intent(this, Product_Detail_Reserve_Activity::class.java)
            startActivity(intent)
        }


//        vpMainBanner.adapter = RecyclerViewAdapter()
//        TabLayoutMediator(
//            tabMainBanner,
//            vpMainBanner
//        )
//        { tab, position ->
//            vpMainBanner.setCurrentItem(tab.position)
//        }.attach()
    }
}
