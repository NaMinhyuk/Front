package com.example.lifesharing.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.GridLayoutManager
import com.example.lifesharing.databinding.FragmentReservationCalendarBinding
import com.example.lifesharing.reservation.data.ReserveMonthAdapter
import com.example.lifesharing.reservation.interfaces.CalendarDayClickListener
import com.example.lifesharing.reservation.viewModel.ReservationListViewModel
import java.util.Calendar
class ReservationCalendarFragment : Fragment() {

    private lateinit var binding: FragmentReservationCalendarBinding
    private lateinit var reserveMonthAdapter: ReserveMonthAdapter
    private lateinit var reservationViewModel: ReservationListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationCalendarBinding.inflate(inflater, container, false)

        reservationViewModel = ViewModelProvider(requireActivity()).get(ReservationListViewModel::class.java)

        reservationViewModel.dateWiseReservationCount.observe(viewLifecycleOwner, Observer { countMap ->
            initRecycler(countMap)
        })

        return binding.root
    }

    private fun initRecycler(countMap: Map<String, Int>) {
        reserveMonthAdapter = ReserveMonthAdapter(generateDays(), countMap, getCurrentDate(), object : CalendarDayClickListener {
            override fun onDayClick(day: String) {
                reserveMonthAdapter.setSelectedDate(day)
            }
        })
        binding.resCalRl.adapter = reserveMonthAdapter
        binding.resCalRl.layoutManager = GridLayoutManager(context, 7)
    }

    private fun generateDays(): List<String> {
        // 현재 달의 날짜 리스트를 생성합니다.
        val days = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..maxDay) {
            days.add(i.toString())
        }
        return days
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_MONTH).toString()
    }
}

