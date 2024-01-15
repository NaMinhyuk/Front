package com.example.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bottom.databinding.FragmentRegistraionBinding

class RegistrationFragment : Fragment() {

    lateinit var binding: FragmentRegistraionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistraionBinding.inflate(inflater, container, false)

        return binding.root
    }
}