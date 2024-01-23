package com.example.newpractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.bottom.R
import com.example.bottom.databinding.FragmentHomeBinding

class HomeFragment: Fragment(){

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_banner_home))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_banner_home))

        val categoryAdapter = CategoryVPAdapter(this)
        categoryAdapter.addFragment(CategoryFragment(R.drawable.img_banner_home))
        categoryAdapter.addFragment(CategoryFragment(R.drawable.img_banner_home))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}