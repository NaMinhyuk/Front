package com.example.lifesharing.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentBannerBinding


class BannerFragment() : Fragment() {

    lateinit var binding : FragmentBannerBinding

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannerBinding.inflate(inflater, container, false)
        //binding.bannerIv.setImageResource(imgRes)

        val imgRes = arguments?.getInt(IMG_RES_KEY) ?: R.drawable.home_banner_bg_img
        binding.bannerIv.setImageResource(imgRes)

        return binding.root
    }

    companion object {
        private const val IMG_RES_KEY = "image_resource"

        fun newInstance(imgRes: Int): BannerFragment {
            val fragment = BannerFragment()
            val args = Bundle()
            args.putInt(IMG_RES_KEY, imgRes)
            fragment.arguments = args
            return fragment
        }
    }
}