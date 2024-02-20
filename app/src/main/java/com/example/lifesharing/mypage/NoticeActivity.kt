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
class NoticeActivity  : AppCompatActivity(), NoticeListAdapter.OnItemClickListener {

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
        adapter = NoticeListAdapter(getSampleNoticeData(), this) // 클릭 리스너 전달
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // 더미 데이터
    private fun getSampleNoticeData(): List<NoticeListData> {
        val sampleData = mutableListOf<NoticeListData>()
        sampleData.add(NoticeListData("첫 번째 공지 예시 타이틀", "24.02.01", "이것은 첫 번째 공지입니다."))
        sampleData.add(NoticeListData("두 번째 공지 예시 타이틀", "24.02.02", "이것은 두 번째 공지입니다."))
        sampleData.add(NoticeListData("세 번째 공지 예시 타이틀", "24.02.03", "이것은 세 번째 공지입니다."))
        return sampleData
    }

    override fun onItemClick(position: Int) {
        // 선택한 항목의 데이터를 가져옴
        val selectedItem = adapter.getItemAtPosition(position)

        // NoticeDetailActivity로 이동하는 인텐트 생성
        val intent = Intent(this, NoticeDetailActivity::class.java)
        intent.putExtra("title", selectedItem.title)
        intent.putExtra("date", selectedItem.date)
        intent.putExtra("content", selectedItem.content)
        startActivity(intent)
    }
}
