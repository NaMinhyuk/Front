package com.example.lifesharing.mypage.review.myReviewList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMyReviewBinding
import com.example.lifesharing.mypage.MyPageActivity
import com.example.lifesharing.mypage.review.model.response_body.ReviewListDTOList
import com.example.lifesharing.mypage.review.registerReview.MyReviewListAdapter
import com.example.lifesharing.service.work.GetReviewListWork

// My Reviews (내가 쓴 리뷰)
class MyReviewActivity : AppCompatActivity() {

    val TAG: String = "로그"

    lateinit var binding: ActivityMyReviewBinding

    private var myReviewList : ArrayList<ReviewListDTOList>?=null

    private var myReviewCount: Int?=null

    private lateinit var myReviewListAdapter: MyReviewListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        myReviewList = GlobalApplication.getMyReviewData()

        myReviewCount = GlobalApplication.getMyReviewCount()

        myReviewListAdapter = MyReviewListAdapter()
        myReviewListAdapter.submitList(myReviewList!!)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_review)

        binding.activity = this
        binding.lifecycleOwner = this

        binding.myReviewCount.text = myReviewCount.toString()

        binding.reviewListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MyReviewActivity, LinearLayoutManager.VERTICAL, false)
            adapter = myReviewListAdapter
        }

        binding.myReviewBackIv.setOnClickListener {
            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }
}