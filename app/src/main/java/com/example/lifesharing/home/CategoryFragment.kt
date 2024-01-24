package com.example.bottom.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bottom.databinding.FragmentCategoryBinding


class CategoryFragment(val imgRes : Int) : Fragment() {

    lateinit var binding : FragmentCategoryBinding

    override fun OnCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.bannerIv.setImageResource(imgRes)

        return binding.root
    }
}