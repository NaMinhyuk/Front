package com.example.lifesharing.mypage.interfaces

import com.example.lifesharing.mypage.model.response_body.UsageHistoryResult

// 리뷰 작성하기 클릭 리스너
interface ReviewWriteClickListener {
    fun onReviewWrite(reservationItem: UsageHistoryResult)
}