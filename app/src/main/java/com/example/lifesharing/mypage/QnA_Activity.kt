package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityQnaBinding

/** QnA (1:1 문의) */
class QnA_Activity  : AppCompatActivity() {

    private lateinit var binding: ActivityQnaBinding

    private lateinit var waitingFragment: QnAWaitingFragment    // 문의한 목록 프래그먼트
    private lateinit var completeFragment: QnACompleteFragment  // 문의 답변 완료 목록 프래그먼트
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQnaBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기
        binding.qnaBackIv.setOnClickListener {
            finish()
        }

        // 문의하기
         binding.btnGoQna.setOnClickListener {
             val intent = Intent(this, WriteQnAActivity::class.java)
             startActivity(intent)
         }

        // tab layout 초기화
        val tabLayout = findViewById<TabLayout>(R.id.qna_tab)

        // tabLayout의 아이템으로 들어갈 프래그먼트 할당
        waitingFragment = QnAWaitingFragment()
        completeFragment = QnACompleteFragment()

        tabLayout.getTabAt(0)?.select()    // 첫번째 탭 선택 (default)

        // FragmentManager를 통해 액티비티 진입 시 기본 프래그먼트 설정
        supportFragmentManager.beginTransaction()
            .replace(R.id.qna_frame, waitingFragment)
            .commit()

        // tabLayout 아이템 클릭 리스너 설정
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val position = it.position     // 선택 항목 position에 따라 표시할 프래그먼트 설정
                    val selected: Fragment? = when (position) {
                        0 -> waitingFragment
                        1 -> completeFragment
                        else -> null
                    }
                    selected?.let { fragment ->
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.qna_frame, fragment)
                            .commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Not needed
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Not needed
            }
        })
    }
}