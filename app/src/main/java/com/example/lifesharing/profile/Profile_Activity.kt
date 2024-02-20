package com.example.lifesharing.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Profile_Activity:AppCompatActivity() {

    private val tabTextList = listOf("등록제품 99+","대여중 99+","리뷰 99+")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager1.adapter = Profile_ViewPagerAdapter(this)

        TabLayoutMediator(binding.profileBodyTl,binding.viewPager1) { tab,pos->
            tab.text = tabTextList[pos]
        }.attach()



        binding.profileHeaderBackbtn.setOnClickListener {
            finish()
        }

    }
}