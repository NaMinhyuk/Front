package com.example.lifesharing.mypage.interfaces

import com.example.lifesharing.mypage.model.response_body.UsageHistoryResult

interface UsageItemClickListener {
    fun onItemClick(reservationItem: UsageHistoryResult)
}