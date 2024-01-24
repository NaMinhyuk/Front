package com.example.lifesharing.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // banner

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.home_banner_img))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // category

        val categoryAdapter = CategoryVPAdapter(this)
        categoryAdapter.addFragment(CategoryFragment(R.layout.fragment_category_a))
        categoryAdapter.addFragment(CategoryFragment(R.layout.fragment_category_b))

        binding.homeCategoryVp.adapter = categoryAdapter
        binding.homeCategoryVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}