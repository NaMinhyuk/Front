package com.example.lifesharing.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentReservationBinding


class ReservationFragment : Fragment() {

    private lateinit var binding: FragmentReservationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationBinding.inflate(inflater, container, false)

        childFragmentManager.beginTransaction().replace(R.id.res_cal_fl, ReservationCalendarFragment()).commit()
        childFragmentManager.beginTransaction().replace(R.id.res_list_fl, ReservationListFragment()).commit()

        return binding.root
    }
}
