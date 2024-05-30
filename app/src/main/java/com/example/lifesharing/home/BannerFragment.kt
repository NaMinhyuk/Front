package com.example.lifesharing.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentBannerBinding

class BannerFragment(val imgRes: Int) : Fragment() {

    lateinit var binding: FragmentBannerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBannerBinding.inflate(inflater, container, false)
        binding.bannerIv.setImageResource(imgRes)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_banner, container, false)
    }
}