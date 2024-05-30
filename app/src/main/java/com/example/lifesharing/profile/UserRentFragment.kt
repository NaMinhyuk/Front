package com.example.lifesharing.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifesharing.databinding.FragmentUserRentBinding
import com.example.lifesharing.profile.adapter.SellerRentAdapter
import com.example.lifesharing.profile.viewModel.SellerRentViewModel

/**
 * 대여자 프로필 - 대여중
 */
class UserRentFragment : Fragment() {

    private lateinit var binding : FragmentUserRentBinding
    private lateinit var recyclerAdapter : SellerRentAdapter
    private lateinit var viewModel : SellerRentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRentBinding.inflate(inflater, container, false)

        val sellerId = arguments?.getLong("sellerId", -1)

        recyclerAdapter = SellerRentAdapter(ArrayList())
        binding.sellerProductRentRv.adapter = recyclerAdapter

        viewModel = ViewModelProvider(this).get(SellerRentViewModel::class.java)

        // 대여자가 현재 대여중인 물품 조회 요청
        viewModel.loadSellerRentProducts(sellerId!!)

        // 요청 결과를 관찰하여 제품 목록 로딩
        viewModel.itemResult.observe(viewLifecycleOwner, Observer { products ->
            if (products.isNullOrEmpty()) {   // 대여중인 물품이 없을 때
                Log.d("UserRentFragment", "대여중인 물품 목록이 없습니다.")
                binding.rentNotificationTv.visibility = View.VISIBLE
                binding.sellerProductRentRv.visibility = View.GONE
            } else {
                Log.d("UserRentFragment", "대여중인 물품 있음. 리스트로 보여줘라")
                binding.rentNotificationTv.visibility = View.GONE
                binding.sellerProductRentRv.visibility = View.VISIBLE
                recyclerAdapter.setItems(ArrayList(products))
                recyclerAdapter.notifyDataSetChanged()
            }
        })
        return binding.root
    }
}