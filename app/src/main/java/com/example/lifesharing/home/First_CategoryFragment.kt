package com.example.lifesharing.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifesharing.Product_Menu_Activity
import com.example.lifesharing.databinding.FragmentFirstCategoryBinding
import com.example.lifesharing.service.work.MenuProduct

class First_CategoryFragment() : Fragment() {

    lateinit var binding: FragmentFirstCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstCategoryBinding.inflate(inflater, container, false)
        MenuProduct().menuProductAPI()
        
        binding.homeCategoryDigitalIv.setOnClickListener{

            val intent = Intent(requireContext(), Product_Menu_Activity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}