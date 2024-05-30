package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.Product_Detail_Activity
import com.example.lifesharing.databinding.ActivityWishListBinding
import com.example.lifesharing.mypage.model.response_body.HeartList
import com.example.lifesharing.mypage.mypage_data.OnItemClickListener
import com.example.lifesharing.mypage.mypage_data.WishListAdapter
import com.example.lifesharing.mypage.viewModel.HeartListViewModel


/** Wish List (찜 목록) */
class WishListActivity  : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityWishListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WishListAdapter    // 찜 목록 리사이클러뷰 어댑터

    private lateinit var viewModel: HeartListViewModel  // 찜 목록 조회 ViewModal
    var productId: Long ?= null   // 아이템 클릭 시 필요한 제품 ID 변수 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWishListBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기
        binding.wishBackIv.setOnClickListener {
            finish()
        }

//        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 무거동", reviewCount = "(100)", name = "카메라" , deposit = 500000, dayPrice = 10000))
//        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 삼산", reviewCount = "(0)", name = "카메라" , deposit = 500000, dayPrice = 10000))

        // 리사이클러 뷰 할당
        recyclerView = binding.wishItemRv

        val heartListItem = ArrayList<HeartList>() // 데이터 리스트 준비

        // 어댑터 생성 및 설정
        adapter = WishListAdapter(heartListItem, this)
        recyclerView.adapter = adapter

        // 리사이클러뷰 레이아웃 매니저 설정
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // 어댑터에 데이터 변경을 알리고 갱신
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(HeartListViewModel::class.java)
        viewModel.getHeartList()   // 찜 목록 조회 요청
        viewModel.heartListItem.observe(this, Observer { heartItems ->  // 찜 목록 관찰
//            val item = heartItems.map { heartItem ->
//                WishListData(
//                    img = heartItem.imageUrl.toString(),
//                    location = heartItem.location,
//                    reviewCount = heartItem.reviewCount.toString(),
//                    name = heartItem.name,
//                    deposit = heartItem.deposit,
//                    dayPrice = heartItem.dayPrice
//                )
//            }
            adapter.setItems(ArrayList(heartItems))   // 어댑터에 새로운 찜 리스트로 업데이트
        })
    }

    override fun onItemClick(heartList: HeartList) {
        val intent = Intent(this, Product_Detail_Activity::class.java)
        intent.putExtra("productId", heartList.productId) // 상세 화면 전환 시 필요한 제품 식별자
        startActivity(intent)
    }
}