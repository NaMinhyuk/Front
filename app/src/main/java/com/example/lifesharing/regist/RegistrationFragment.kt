package com.example.lifesharing.regist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registbtn = binding.registHomeRegistbtn
        registbtn.setOnClickListener {
            // Intent를 사용하여 Activity로 화면 전환
            val intent = Intent(requireActivity(), Regist_Add_Activity::class.java)
            startActivity(intent)
        }
    }
}