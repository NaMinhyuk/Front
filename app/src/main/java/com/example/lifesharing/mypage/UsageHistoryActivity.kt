package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.R
import com.example.lifesharing.mypage.review.registerReview.RegisterReviewActivity


// Usage History (이용내역)
class UsageHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usage_history)

        val backIv = findViewById<ImageView>(R.id.usage_his_back_iv)

        val searchIv = findViewById<ImageView>(R.id.usage_his_search_iv)

        backIv.setOnClickListener {
            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
            val intent = Intent(this, MyPageActivity::class.java)

            startActivity(intent)

        }

        searchIv.setOnClickListener {
            val registerReviewActivity = Intent(this, RegisterReviewActivity::class.java)
            startActivity(registerReviewActivity)
        }
    }
}