package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.BuildConfig
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMyPageBinding
import com.example.lifesharing.mypage.work.MyPageUserInfo
import com.example.lifesharing.mypage.mypage_data.UserInfoResultDTO
import com.example.lifesharing.mypage.mypage_data.MyPageMainList
import com.example.lifesharing.mypage.mypage_data.MyPageMainListAdapter
import com.example.lifesharing.mypage.review.myReviewList.MyReviewActivity
import com.example.lifesharing.payments.TossPaymentsActivity
import com.example.lifesharing.service.work.GetReviewListWork
import java.text.NumberFormat
import java.util.Locale

/** 마이페이지 View */
class MyPageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyPageMainListAdapter      // 마이페이지 메뉴 목록 리사이클러뷰 어댑터 인스턴스 생성

    private lateinit var userInfoData : UserInfoResultDTO    // 유저 정보를 담을 객체 생성
    private lateinit var userInfoUpdater : MyPageUserInfo    // 유저 정보를 업데이트를 위해 선언

    private lateinit var binding: ActivityMyPageBinding

    val TAG = "로그"

    private val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL  // 프로필 이미지를 로딩하기 위한 aws baseUrl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GetReviewListWork().getReviewList()   // 리뷰 목록을 가져오는 함수

        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userInfoData = GlobalApplication.getUserInfoData()   // GlobalApplication에서 사용자 정보를 가져옴
        userInfoUpdater = MyPageUserInfo()     // MyPageUserInfo() 인스턴스 객체 초기 -> 이 객체를 이용해 사용자 정보를 업데이트

        Log.d(TAG, "userdata: ${userInfoData.nickname}")

        // 충전 버튼
        binding.myPageProfileBt2.setOnClickListener {
            // userInfoData가 null이 아닌 경우에만 TossPaymentsActivity 시작
            if (userInfoData != null) {
                startActivity(Intent(this, TossPaymentsActivity::class.java))
            } else {
                Toast.makeText(this, "사용자 정보가 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }


        refreshUserInfo()   // 마이페이지에 필요한 데이터 로딩(사용자 정보를 새로고침)

        // recyclerView
        recyclerView = findViewById(R.id.my_page_main_rv)

        val itemList = ArrayList<MyPageMainList>() // 마이페이지 메뉴에 들어갈 데이터 리스트 준비

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

        // 리사이클러 뷰 어댑터 생성 및 설정
        adapter = MyPageMainListAdapter(itemList)
        recyclerView.adapter = adapter

        // 리사이클러뷰 아이템 클릭 리스너 설정
        adapter.setOnItemClickListener(object : MyPageMainListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = itemList[position]
                // 클릭된 아이템에 따라 다른 액티비티 시작
                when (item.list) {
                    "내 정보" -> startActivity(Intent(this@MyPageActivity, MyProfileActivity::class.java))
                    "찜 목록" -> startActivity(Intent(this@MyPageActivity, WishListActivity::class.java))
                    "이용내역" -> startActivity(Intent(this@MyPageActivity, UsageHistoryActivity::class.java))
                    "등록내역" -> startActivity(Intent(this@MyPageActivity, RegistHistoryActivity::class.java))
                    "내가 쓴 리뷰" -> startActivity(Intent(this@MyPageActivity, MyReviewActivity::class.java))
                    "공지사항" -> startActivity(Intent(this@MyPageActivity, NoticeActivity::class.java))
                    "FAQ" -> startActivity(Intent(this@MyPageActivity, FAQ_Activity::class.java))
                    "1:1 문의" -> startActivity(Intent(this@MyPageActivity, QnA_Activity::class.java))
                    "서비스 이용약관" -> startActivity(Intent(this@MyPageActivity, ToS_Activity::class.java))
                    "리뷰운영정책" -> startActivity(Intent(this@MyPageActivity, ROP_Activity::class.java))
                    "개인정보 처리방침" -> startActivity(Intent(this@MyPageActivity, PrivacyPolicyActivity::class.java))
                }
            }
        })

        // 리사이클러뷰 레이아웃 매니저 설정
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 어댑터에 데이터 변경을 알리고 갱신
        adapter.notifyDataSetChanged()
    }

    // 액티비티 시작 시 사용자 정보를 업데이트
    override fun onStart() {
        super.onStart()
        userInfoUpdater.getMyPageUserInfo()
        refreshUserInfo()  // 화면으로 돌아올 때마다 사용자 정보를 새로고침
    }

    override fun onResume() {
        super.onResume()
        refreshUserInfo()  // 화면으로 돌아올 때마다 사용자 정보를 새로고침
    }

    // 서버로부터 받은 데이터를 바인딩하는 메서드
    private fun refreshUserInfo() {
        val data = GlobalApplication.getUserInfoData()
        binding.myPageNickname.text = data.nickname
        binding.myPageUserLocation.text = data.area
        if (userInfoData.profileUrl != null){
            Log.d(TAG, "UserProfile URL: ${data.profileUrl}")  // 로그로 URL 출력
            Glide.with(this)
                .load(IMAGE_BASE_URL + data.profileUrl)
                .placeholder(R.drawable.defaultprofile) // 로딩 중 이미지
                .error(R.drawable.defaultprofile)       // 로딩 실패 시 이미지
                .into(binding.myPageImage)
        }
        else{
            Glide.with(this)
                .load(R.drawable.defaultprofile)
                .into(binding.myPageImage)
        }

        // 포인트 천 단위로 끊기
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        binding.myPageUserPoint.text = formatter.format(data.point)
        binding.ratingBar.rating = data.score.toFloat()
        binding.myPageStarNum.text = "(" + data.score + ")"

    }
}