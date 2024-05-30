package com.example.lifesharing.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifesharing.databinding.FragmentReservationListBinding
import com.example.lifesharing.reservation.data.ReservationClickListener
import com.example.lifesharing.reservation.data.ReservationListAdapter
import com.example.lifesharing.reservation.viewModel.ReservationListViewModel

class ReservationListFragment : Fragment() {

    private lateinit var binding: FragmentReservationListBinding
    private lateinit var reservationListAdapter: ReservationListAdapter
    private lateinit var reservationViewModel: ReservationListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationListBinding.inflate(inflater, container, false)

        reservationViewModel = ViewModelProvider(requireActivity()).get(ReservationListViewModel::class.java)

        reservationListAdapter = ReservationListAdapter(arrayListOf(), object : ReservationClickListener {
            override fun onItemClick(position: Int) {
                val selectedReservation = reservationListAdapter.getItem(position)
            }
        })
        binding.resFilterRl.adapter = reservationListAdapter
        binding.resFilterRl.layoutManager = LinearLayoutManager(context)

        reservationViewModel.filteredReservations.observe(viewLifecycleOwner, Observer { reservationList ->
            reservationListAdapter.setItems(reservationList)
        })

        setupFilterSpinner()

        reservationViewModel.loadReservations()

        return binding.root
    }

    private fun setupFilterSpinner() {
        val filterSpinner = binding.resListSpinner
        val options = arrayOf("전체", "MY", "대여")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = adapter

        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = options[position]
                handleFilterOption(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun handleFilterOption(selectedOption: String) {
        when (selectedOption) {
            "전체" -> reservationViewModel.getReservationList("all")
            "MY" -> reservationViewModel.getReservationList("my")
            "대여" -> reservationViewModel.getReservationList("rent")
        }
    }
}




