package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.mypage.mypage_api.ViewInquiryAnswer

class QnA_Answer_Activity : AppCompatActivity() {

    private val TAG: String = "QnA_Answer_Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna_answer)

        val backIv = findViewById<ImageView>(R.id.qna_ans_back_iv)
        val listTv = findViewById<TextView>(R.id.qna_ans_go_list_tv)

        backIv.setOnClickListener {
            val intent = Intent(this, QnA_Activity::class.java)
            startActivity(intent)
        }

        listTv.setOnClickListener {
            val intent = Intent(this, QnA_Activity::class.java)
            startActivity(intent)
        }

        val viewInquiryAnswer = ViewInquiryAnswer()

        viewInquiryAnswer.getViewInquiryAnswer {
            val inquiry = it?.result?.inquiry?.firstOrNull()
            findViewById<TextView>(R.id.qna_ans_content_tv).text = inquiry?.body ?: ""

            val imageUrl = inquiry?.InquiryImageList
            if (!imageUrl.isNullOrEmpty()) {
                val imageView = findViewById<ImageView>(R.id.qna_ans_img1)
                Glide.with(this)
                    .load(imageUrl)
                    .into(imageView)
            }
        }
    }
}
