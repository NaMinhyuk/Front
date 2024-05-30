package com.example.lifesharing.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lifesharing.BuildConfig
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityQnaDetailBinding
import com.example.lifesharing.mypage.viewModel.QnaDetailViewModel
import com.example.lifesharing.mypage.viewModel.QnaDetailViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class QnaDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQnaDetailBinding
    private lateinit var viewModel: QnaDetailViewModel    // 문의 상세 정보 조회 ViewModel

    private val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL    // 문의 이미지를 로딩하기 위한 aws baseUrl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityQnaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기
        binding.btnQnaDetailBack.setOnClickListener {
            finish()
        }

        // Fragment에서 문의 항목 클릭 시 넘겨준 문의ID를 변수에 저장
        val inquiryId = intent.getLongExtra("inquiryId", 0L)
        val createdAt = intent.getStringExtra("createdAt")
        val factory = QnaDetailViewModelFactory(application)
        // ViewModelProvider를 이용하여 문의 상세 조회 ViewModel을 가져옴
        viewModel = ViewModelProvider(this, factory).get(QnaDetailViewModel::class.java)

        viewModel.qnaDetail.observe(this , Observer { response ->   // 응답 결과를 관찰
            response.result.inquiry.let { inquiry ->   // 응답에 담긴 결과를 뷰에 바인딩(로드)

                binding.qnaDetailBodyTv.text = inquiry.body    // 문의 내용 로드

                // 서버에서 받은 날짜 형식
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
                // 변환하고 싶은 날짜 형식
                val outputFormat = SimpleDateFormat("yy.MM.dd", Locale.getDefault())
                binding.qndDetailDateTv.text = outputFormat.format(inputFormat.parse(createdAt))

                // 이미지 리스트 초기화
                val imageViews = listOf(binding.qnaDetailImg1, binding.qnaDetailImg2, binding.qnaDetailImg3, binding.qnaDetailImg4, binding.qnaDetailImg5)
                imageViews.forEach { it.visibility = View.GONE }  // 모든 이미지 뷰를 먼저 숨깁니다.

                // 이미지가 있는 경우만 뷰를 업데이트
                inquiry.imageList.forEachIndexed { index, image ->   // 이미지 리스트를 이미지 뷰에 바인딩
                    if (index < imageViews.size) {  // 인덱스 검사를 통해 오류 방지
                        displayImage(index, image.imageUrl)
                    }
                }
            }

            // 답변 visibility
            response.result.reply?.let { reply ->   // 답변이 있는 경우
                binding.qnaReplyBody.text = reply.replyBody
            } ?: run {            // 답변이 없는 경우
                binding.qnaReplyBody.text = getString(R.string.qna_reply_no)
                binding.qnaReplyBody.gravity = Gravity.CENTER
            }
        })

        // 문의 상세 정보 요청
        viewModel.getQnaDetail(inquiryId)

    }

    private fun displayImage(index: Int, imageUrl: String) {
        val imageView = when(index) {
            0 -> binding.qnaDetailImg1
            1 -> binding.qnaDetailImg2
            2 -> binding.qnaDetailImg3
            3 -> binding.qnaDetailImg4
            4 -> binding.qnaDetailImg5
            else -> null
        }
        imageView?.let { view ->
            if (imageUrl != null) {
                view.visibility = View.VISIBLE
                Glide.with(this).load(IMAGE_BASE_URL + imageUrl).into(view)
            } else {
                view.visibility = View.GONE
            }
        }
    }
}