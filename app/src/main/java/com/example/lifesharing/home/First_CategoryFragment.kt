package com.example.lifesharing.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifesharing.databinding.FragmentFirstCategoryBinding

class First_CategoryFragment() : Fragment() {

    lateinit var binding : FragmentFirstCategoryBinding

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }
}