package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityQnaBinding
import com.example.lifesharing.mypage.mypage_data.FAQListAdapter
import com.example.lifesharing.mypage.mypage_data.FAQListData

class FAQ_Activity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FAQListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        val backIv = findViewById<ImageView>(R.id.faq_back_iv)

        backIv.setOnClickListener {
            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
//            val intent = Intent(this, MyPageActivity::class.java)
//            startActivity(intent)

            finish()
        }

        recyclerView = findViewById(R.id.my_page_faq_rv)
        adapter = FAQListAdapter(getSampleFAQData()) // 더미 데이터로 어댑터 초기화
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // 더미데이터
    private fun getSampleFAQData() : List<FAQListData> {
        val sampleData = mutableListOf<FAQListData>()
        sampleData.add(FAQListData("첫번째 FAQ 타이틀 예시 텍스트 입니다."))
        sampleData.add(FAQListData("두번째 FAQ 타이틀 예시 텍스트 입니다."))
        sampleData.add(FAQListData("세번째 FAQ 타이틀 예시 텍스트 입니다."))
        sampleData.add(FAQListData("네번째 FAQ 타이틀 예시 텍스트 입니다."))
        sampleData.add(FAQListData("다섯번째 FAQ 타이틀 예시 텍스트 입니다."))
        sampleData.add(FAQListData("여섯번째 FAQ 타이틀 예시 텍스트 입니다."))
        sampleData.add(FAQListData("일곱번째 FAQ 타이틀 예시 텍스트 입니다."))

        return sampleData
    }
}