package com.example.lifesharing.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifesharing.databinding.FragmentSecondCategoryBinding

class Second_CategoryFragment() : Fragment() {

    lateinit var binding : FragmentSecondCategoryBinding

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }
}