package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.lifesharing.R

class MyPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        // 각 ConstraintLayout의 ID와 대상 액티비티를 매핑한 맵
        val layoutToActivityMap = mapOf(
            R.id.list1 to MyProfileActivity::class.java,
            R.id.list2 to WishListActivity::class.java,
            /*R.id.list3 to 대상액티비티1::class.java,
            R.id.list4 to 대상액티비티1::class.java,
            R.id.list5 to 대상액티비티1::class.java,*/
            R.id.list6 to NoticeActivity::class.java,
            R.id.list7 to FAQ_Activity::class.java,
            R.id.list8 to QnA_Ativity::class.java,
            R.id.list9 to Service_ToS_Activity::class.java,
            R.id.list10 to ReviewOperationPolicyActivity::class.java,
            R.id.list11 to PrivacyPolicyActivity::class.java

        )

        // 각 ConstraintLayout에 대한 클릭 이벤트 리스너 설정
        layoutToActivityMap.forEach { (layoutId, activityClass) ->
            findViewById<ConstraintLayout>(layoutId).setOnClickListener {
                val intent = Intent(this, activityClass)
                startActivity(intent)
            }
        }
    }
}