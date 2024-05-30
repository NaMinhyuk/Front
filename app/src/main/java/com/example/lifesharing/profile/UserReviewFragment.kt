package com.example.lifesharing.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentUserRentBinding
import com.example.lifesharing.databinding.FragmentUserReviewBinding
import com.example.lifesharing.profile.adapter.SellerReviewAdapter
import com.example.lifesharing.profile.viewModel.SellerReviewViewModel

class UserReviewFragment : Fragment() {

    private lateinit var binding : FragmentUserReviewBinding
    private lateinit var recyclerAdapter : SellerReviewAdapter
    private lateinit var viewModel : SellerReviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserReviewBinding.inflate(inflater, container, false)

        val sellerId = arguments?.getLong("sellerId", -1)

        recyclerAdapter = SellerReviewAdapter(ArrayList())
        binding.sellerReviewRv.adapter = recyclerAdapter

        viewModel = ViewModelProvider(this).get(SellerReviewViewModel::class.java)

        // 대여자에 달린 리뷰 목록 조회 요청
        viewModel.loadSellerReviews(sellerId!!)

        // 요청 결과를 관찰하여 리뷰 목록 로딩
        viewModel.itemResult.observe(viewLifecycleOwner, Observer { reviews ->
            if (reviews.isNullOrEmpty()) {   // 대여중인 물품이 없을 때
                Log.d("ReviewFragment", "리뷰목록이 없습니다.")
                binding.rentNotificationTv.visibility = View.VISIBLE
                binding.sellerReviewRv.visibility = View.GONE
            } else {
                Log.d("ReviewFragment", "리뷰목록을 보여주세요.")
                binding.rentNotificationTv.visibility = View.GONE
                binding.sellerReviewRv.visibility = View.VISIBLE
                recyclerAdapter.setItems(ArrayList(reviews))
                recyclerAdapter.notifyDataSetChanged()
            }
        })
        return binding.root
    }

}