package com.example.bottom.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bottom.databinding.FragmentNewRegistItemBinding

class NewRegistItemFragment : Fragment() {
    lateinit var binding: FragmentNewRegistItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRegistItemBinding.inflate(inflater, container, false)

        return binding.root
    }
}