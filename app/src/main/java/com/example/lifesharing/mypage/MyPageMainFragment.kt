package com.example.bottom.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bottom.databinding.FragmentMyPageMainBinding

class MyPageMainFragment : Fragment() {

    lateinit var binding: FragmentMyPageMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageMainBinding.inflate(inflater, container, false)

        return binding.root
    }
}