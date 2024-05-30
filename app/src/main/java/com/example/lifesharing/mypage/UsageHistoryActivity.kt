package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityUsageHistoryBinding
import com.example.lifesharing.mypage.interfaces.ReviewWriteClickListener
import com.example.lifesharing.mypage.interfaces.UsageItemClickListener
import com.example.lifesharing.mypage.model.response_body.UsageHistoryResult
import com.example.lifesharing.mypage.mypage_data.UsageHistory
import com.example.lifesharing.mypage.mypage_data.UsageHistoryAdapter
import com.example.lifesharing.mypage.review.registerReview.RegisterReviewActivity
import com.example.lifesharing.mypage.viewModel.UsageHistoryViewModel


// Usage History (이용내역)
class UsageHistoryActivity : AppCompatActivity(), ReviewWriteClickListener, UsageItemClickListener {

    private lateinit var binding: ActivityUsageHistoryBinding
    private lateinit var adapter: UsageHistoryAdapter
    private lateinit var viewModel : UsageHistoryViewModel

    // 더미데이터
    private var historyList = listOf(
        UsageHistory(false, "https://lifesharing.s3.ap-northeast-2.amazonaws.com/product/8e4f2c95-64ea-404c-a7c2-f0b7376d9e9e_img.png", "울산 무거동", "Canon [렌즈 포함] EOS 8CanonCanon \n" +
                "[렌즈 포함] EOS R8CanonCanon ", "3일 17시간", "250,000", "500,000"),
        UsageHistory(true, "https://lifesharing.s3.ap-northeast-2.amazonaws.com/product/8e4f2c95-64ea-404c-a7c2-f0b7376d9e9e_img.png", "울산 무거동", "Canon [렌즈 포함] EOS 8CanonCanon \n" +
                "[렌즈 포함] EOS R8CanonCanon ", "4일 3시간", "250,000", "500,000")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUsageHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val searchIv = findViewById<ImageView>(R.id.usage_his_search_iv)

        setupRecyclerView()

        // 뒤로가기 버튼
        binding.usageHisBackIv.setOnClickListener {
           finish()
        }

        // 검색 버튼
        searchIv.setOnClickListener {
            val registerReviewActivity = Intent(this, RegisterReviewActivity::class.java)
            startActivity(registerReviewActivity)
        }

        // 예약 목록 viewModel 초기화
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(UsageHistoryViewModel::class.java)
        viewModel.loadUsageHistory()
        viewModel.usageHistoryList.observe(this, Observer { usageHistoryItem ->
            // RecyclerView 어댑터 업데이트
            adapter.setItems(ArrayList(usageHistoryItem))

            // 예약 목록 개수 조회
            val listSize = adapter.itemCount
            binding.getCount.text = listSize.toString()
        })
    }

    private fun setupRecyclerView() {
        adapter = UsageHistoryAdapter(ArrayList(emptyList()), this , this)
        binding.userListView.layoutManager = LinearLayoutManager(this)
        binding.userListView.adapter = adapter
    }

    // 리뷰 작성하기 클릭 이벤트 리스너
    override fun onReviewWrite(usageHistoryItem: UsageHistoryResult) {
        val intent = Intent(this, RegisterReviewActivity::class.java)
        intent.putExtra("reservationId", usageHistoryItem.reservationId)
        startActivity(intent)
    }

    override fun onItemClick(usageHistoryItem: UsageHistoryResult) {
        //val intent = Intent(this, )
        /**
         * 예약 리스트 클릭 시 결제 상세 화면으로 넘어가도록 구현하기
         */
    }
}