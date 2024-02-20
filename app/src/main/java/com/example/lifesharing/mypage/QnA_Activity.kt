package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lifesharing.R

// QnA (1:1 문의)
class QnA_Activity  : AppCompatActivity() {

    private lateinit var waitingFragment: QnAWaitingFragment
    private lateinit var completeFragment: QnACompleteFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna)

        val backIv = findViewById<ImageView>(R.id.qna_back_iv)

        backIv.setOnClickListener {
            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        val writeBtn = findViewById<Button>(R.id.go_write_qna_btn)

        writeBtn.setOnClickListener{

            val intent = Intent(this, WriteQnAActivity ::class.java)
            startActivity(intent)
        }

        // tab layout

        val tabLayout = findViewById<TabLayout>(R.id.qna_tab)

        waitingFragment = QnAWaitingFragment()
        completeFragment = QnACompleteFragment()

        tabLayout.getTabAt(0)?.select()

        supportFragmentManager.beginTransaction()
            .replace(R.id.qna_frame, waitingFragment)
            .commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val position = it.position
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