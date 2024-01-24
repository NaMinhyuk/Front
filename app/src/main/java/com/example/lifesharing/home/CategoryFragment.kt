package com.example.lifesharing.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifesharing.databinding.FragmentCategoryABinding


class CategoryFragment(val imgRes : Int) : Fragment() {

    lateinit var binding : FragmentCategoryABinding

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryABinding.inflate(inflater, container, false)
        //binding.bannerIv.setImageResource(imgRes)

        return binding.root
    }
}