package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.mypage.mypage_data.NoticeListAdapter
import com.example.lifesharing.mypage.mypage_data.NoticeListData

// Notice (공지사항)
class NoticeActivity  : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoticeListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        val backIv = findViewById<ImageView>(R.id.notice_back_iv)

        backIv.setOnClickListener {
            // 뒤로가기 클릭 시 MyPageActivity로 이동하는 코드
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.my_page_notice_rv)
        adapter = NoticeListAdapter(getSampleNoticeData()) // 더미 데이터로 어댑터 초기화
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // 더미 데이터
    private fun getSampleNoticeData(): List<NoticeListData> {
        val sampleData = mutableListOf<NoticeListData>()
        sampleData.add(NoticeListData("공지1", 20240216, "이것은 첫 번째 공지입니다."))
        sampleData.add(NoticeListData("공지2", 20240217, "이것은 두 번째 공지입니다."))
        sampleData.add(NoticeListData("공지3", 20240218, "이것은 세 번째 공지입니다."))
        return sampleData
    }
}