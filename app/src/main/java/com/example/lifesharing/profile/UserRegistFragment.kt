package com.example.lifesharing.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifesharing.product.Product_Detail_Activity
import com.example.lifesharing.databinding.FragmentUserRegistBinding
import com.example.lifesharing.profile.adapter.SellerProductAdapter
import com.example.lifesharing.profile.adapter.SellerProductListener
import com.example.lifesharing.profile.viewModel.SellerProductViewModel

class UserRegistFragment : Fragment(), SellerProductListener {

    private lateinit var binding : FragmentUserRegistBinding
    private lateinit var recyclerAdapter : SellerProductAdapter
    private lateinit var viewModel : SellerProductViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRegistBinding.inflate(inflater, container, false)

        val sellerId = arguments?.getLong("sellerId", -1)

        // 리사이클러뷰 어댑터 초기화
        recyclerAdapter = SellerProductAdapter(ArrayList(), this)
        binding.sellerProductRv.adapter = recyclerAdapter

        viewModel = ViewModelProvider(this).get(SellerProductViewModel::class.java)

        // sellerId를 ViewModel로 전달하여 대여자가 등록한 데이터 로드
        viewModel.loadSellerProducts(sellerId!!)

        // 요청 결과를 관찰하여 제품 목록 로딩
        viewModel.itemResult.observe(viewLifecycleOwner, Observer { products ->
            if (products.isNullOrEmpty()) {
                Log.d("UserRegistFragment", "등록된 제품 목록이 없습니다.")
                binding.registNotificationTv.visibility = View.VISIBLE
                binding.sellerProductRv.visibility = View.GONE
            }
            else {
                Log.d("UserRegistFragment", "등록된 제품 있음. 리스트를 보여줘라 !!")
                binding.registNotificationTv.visibility = View.GONE
                binding.sellerProductRv.visibility = View.VISIBLE
                recyclerAdapter.setItems(ArrayList(products))
                recyclerAdapter.notifyDataSetChanged()  // 어댑터에게 데이터 변경 알림
            }
        })

        return binding.root
    }

    override fun onItemClick(position: Int) {
        val item = recyclerAdapter.getItem(position)
        val intent = Intent(requireContext(), Product_Detail_Activity::class.java).apply {
            putExtra("productId", item.productId)
        }

        startActivity(intent)
    }

}