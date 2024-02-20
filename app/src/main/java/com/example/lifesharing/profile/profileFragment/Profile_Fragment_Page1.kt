package com.example.lifesharing.profile.profileFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifesharing.databinding.FragmentProfilePage1Binding

class Profile_Fragment_Page1 : Fragment() {
    lateinit var binding : FragmentProfilePage1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePage1Binding.inflate(inflater,container,false)
        return binding.root
    }
}