package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.mypage.mypage_data.FAQListAdapter
import com.example.lifesharing.mypage.mypage_data.FAQListData
import com.example.lifesharing.mypage.mypage_data.UsageListAdapter
import com.example.lifesharing.mypage.mypage_data.UsageListData
import com.example.lifesharing.mypage.review.registerReview.RegisterReviewActivity


// Usage History (이용내역)
class UsageHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usage_history)

        val backIv = findViewById<ImageView>(R.id.usage_his_back_iv)

        backIv.setOnClickListener {
            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.my_page_usage_rv)
        adapter = UsageListAdapter(getSampleUsageData()) // 더미 데이터로 어댑터 초기화
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun getSampleUsageData() : List<UsageListData> {
        val sampleData = mutableListOf<UsageListData>()
        sampleData.add(UsageListData("Canon [렌즈 포함] EOS 8 Canon...", "3일 17시간", "250,000", "500,000"))
        sampleData.add(UsageListData("Nike X Stussy Full Zip Washed ...", "5일 9시간", "35,000", "80,000"))
        sampleData.add(UsageListData("Blitzway Space Astro Boy Jet ...", "2일 10시간", "80,000", "150,000"))
        sampleData.add(UsageListData("Adidas Gazelle Germany Off White...", "7일 18시간", "20,000", "50,000"))
        sampleData.add(UsageListData("CDG Logo Coach Jacket Black", "3일 2시간", "20,000", "65,000"))
        sampleData.add(UsageListData("Bottega Veneta Bi-Fold Wallet...", "5일 20시간", "80,000", "250,000"))
        sampleData.add(UsageListData("Supreme Backpack Black - 24SS", "11일 13시간", "45,000", "180,000"))

        return sampleData
    }

}