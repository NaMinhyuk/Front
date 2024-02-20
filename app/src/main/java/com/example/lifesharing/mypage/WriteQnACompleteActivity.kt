package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.R

class WriteQnACompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_qna_complete)

        val backIv = findViewById<ImageView>(R.id.write_qna_complete_back_iv)
        val goQnaBtn = findViewById<Button>(R.id.go_qna_list_btn)

        backIv.setOnClickListener {
            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
            val intent = Intent(this, QnA_Activity::class.java)
            startActivity(intent)
        }

        goQnaBtn.setOnClickListener{
            val intent = Intent(this, QnA_Activity::class.java)
            startActivity(intent)
        }
    }
}