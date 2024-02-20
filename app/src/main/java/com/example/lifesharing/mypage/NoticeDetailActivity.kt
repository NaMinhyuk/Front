package com.example.lifesharing.mypage

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.R

class NoticeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_detail)

        // Intent로부터 데이터 받기
        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val content = intent.getStringExtra("content")

        // 받아온 데이터를 화면에 표시
        findViewById<TextView>(R.id.notice_detail_title_tv).text = title
        findViewById<TextView>(R.id.notice_detail_date_tv).text = date
        findViewById<TextView>(R.id.notice_detail_content_tv).text = content
    }
}