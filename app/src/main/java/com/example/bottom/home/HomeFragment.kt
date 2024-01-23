package com.example.bottom.home

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

        // banner

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.home_banner_img))
        bannerAdapter.addFragment(BannerFragment(R.drawable.home_banner_img))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // category

        val categoryAdapter = CategoryVPAdapter(this)
        categoryAdapter.addFragment(CategoryFragment(R.layout.fragment_category_a))
        categoryAdapter.addFragment(CategoryFragment(R.layout.fragment_category_b))

        binding.homeCategoryVp.adapter = categoryAdapter
        binding.homeCategoryVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        return binding.root
    }
/*
    // category indicator

    home_category_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener) {
        override fun onPageScrollStateChanged(p0: Int) {
        }

        override fun onPageScrolled(p0: Int, p1: Float) {
        }

        override fun onPageSelected(p0: Int) {
            indicator0_home_iv.setImageDrawable(getDrawable(R.drawable.indicator_shape_gray))
            indicator1_home_iv.setImageDrawable(getDrawable(R.drawable.indicator_shape_gray))

            when(p0){
                0 -> indicator0_home_iv.setImageDrawable(getDrawable(R.drawable.indicator_shape_blue))
                1 -> indicator1_home_iv.setImageDrawable(getDrawable(R.drawable.indicator_shape_blue))

            }
        }
    }*/
}