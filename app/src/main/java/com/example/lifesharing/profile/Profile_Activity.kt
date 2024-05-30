package com.example.lifesharing.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lifesharing.databinding.ActivityProfileBinding
import com.example.lifesharing.profile.viewModel.SellerProfileViewModel
import com.example.lifesharing.profile.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class Profile_Activity:AppCompatActivity() {

    private lateinit var viewModel: SellerProfileViewModel
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기
        binding.profileHeaderBackbtn.setOnClickListener {
            finish()
        }

        // TabLayout 아이템 초기화
        val tabTitleArray = listOf("대여물품", "대여중", "리뷰")

        // 상세 화면에서 넘겨주는 대여자 아이디 값
        val sellerId = intent.extras?.getLong("sellerId")

        // 대여자 프로필 데이터 바인딩
        sellerId?.let {
            viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(SellerProfileViewModel::class.java)
            viewModel.sellerProfile.observe(this, Observer { seller ->
                binding.userName.text = seller?.userName + "님의 대여물품"
                binding.userArea.text = seller?.location
                binding.userReviewCnt.text = "(" + seller?.reviewCount.toString() + ")"
                binding.userRatingStar.rating = seller?.score!!.toFloat()
                Glide.with(this).load(seller?.imageUrl).into(binding.profileImage)
            })
        }
        // 대여자 프로필 데이터 로딩
        viewModel.loadSellerProfile(sellerId!!)


        var viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(this, sellerId)

        TabLayoutMediator(binding.tabLayout, viewPager){
                tab, position -> tab.text = tabTitleArray[position]
        }.attach()
    }

}
