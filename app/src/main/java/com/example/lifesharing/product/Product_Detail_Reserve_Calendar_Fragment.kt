package com.example.lifesharing.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.databinding.FragmentCalendarBinding

class Product_Detail_Reserve_Calendar_Fragment : Fragment() {
    private var _binding: FragmentCalendarBinding ?= null
    private lateinit var recyclerView: RecyclerView

    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater,container,false)

        val view = binding?.root

        initView(_binding!!)

        create_data()
        return view
    }

    private fun initView(binding: FragmentCalendarBinding) {
        recyclerView = binding.calRv
        val position: Int = Int.MAX_VALUE / 2

        binding.calRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.calRv.adapter = Month_adapter()

        //아이템 위치지정
        binding.calRv.scrollToPosition(position)

        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calRv)

    }
    private fun create_data() {
        binding?.calRv?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}