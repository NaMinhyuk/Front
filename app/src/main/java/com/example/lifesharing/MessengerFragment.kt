package com.example.bottom.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifesharing.databinding.FragmentMessengerBinding

class MessengerFragment : Fragment() {

    lateinit var binding: FragmentMessengerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessengerBinding.inflate(inflater, container, false)

        return binding.root
    }
}