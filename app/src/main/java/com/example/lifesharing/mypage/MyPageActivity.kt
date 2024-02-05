package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.mypage_data.MyPageMainList
import com.example.lifesharing.mypage_data.MyPageMainListAdapter

class MyPageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyPageMainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        recyclerView = findViewById(R.id.my_page_main_rv)

        val itemList = ArrayList<MyPageMainList>() // 데이터 리스트 준비

        // 데이터 추가
        itemList.add(MyPageMainList("내 정보"))
        itemList.add(MyPageMainList("찜 목록"))
        itemList.add(MyPageMainList("이용내역"))
        itemList.add(MyPageMainList("등록내역"))
        itemList.add(MyPageMainList("내가 쓴 리뷰"))
        itemList.add(MyPageMainList("공지사항"))
        itemList.add(MyPageMainList("FAQ"))
        itemList.add(MyPageMainList("1:1 문의"))
        itemList.add(MyPageMainList("서비스 이용약관"))
        itemList.add(MyPageMainList("리뷰운영정책"))
        itemList.add(MyPageMainList("개인정보 처리방침"))

        // 어댑터 생성 및 설정
        adapter = MyPageMainListAdapter(itemList)
        recyclerView.adapter = adapter

        // 리사이클러뷰 아이템 클릭 리스너 설정
        adapter.setOnItemClickListener(object : MyPageMainListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = itemList[position]
                when (item.list) {
                    "내 정보" -> startActivity(Intent(this@MyPageActivity, MyProfileActivity::class.java))
                    "찜 목록" -> startActivity(Intent(this@MyPageActivity, WishListActivity::class.java))
                    /*"이용내역" -> startActivity(Intent(this@MyPageActivity, UsageHistoryActivity::class.java))
                    "등록내역" -> startActivity(Intent(this@MyPageActivity, RegistrationHistoryActivity::class.java))
                    "내가 쓴 리뷰" -> startActivity(Intent(this@MyPageActivity, MyReviewsActivity::class.java))*/
                    "공지사항" -> startActivity(Intent(this@MyPageActivity, NoticeActivity::class.java))
                    "FAQ" -> startActivity(Intent(this@MyPageActivity, FAQ_Activity::class.java))
                    "1:1 문의" -> startActivity(Intent(this@MyPageActivity, QnA_Activity::class.java))
                    "서비스 이용약관" -> startActivity(Intent(this@MyPageActivity, Service_ToS_Activity::class.java))
                    "리뷰운영정책" -> startActivity(Intent(this@MyPageActivity, ReviewOperationPolicyActivity::class.java))
                    "개인정보 처리방침" -> startActivity(Intent(this@MyPageActivity, PrivacyPolicyActivity::class.java))
                }
            }
        })

        // 리사이클러뷰 레이아웃 매니저 설정
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 어댑터에 데이터 변경을 알리고 갱신
        adapter.notifyDataSetChanged()
    }
}