package com.example.lifesharing.mypage.review.registerReview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.databinding.MyReviewItemBinding
import com.example.lifesharing.mypage.review.model.response_body.ReviewListDTOList

class MyReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val TAG: String = "로그"

    var binding = MyReviewItemBinding.bind(itemView)

    private val userProfile = binding.myReviewItemProfile
    private val nickname = binding.myReviewNickname
    private val date = binding.myReviewDate
    private val rentDay = binding.myReviewRentDay
    private val itemImg = binding.myReviewItemImage
    private val startCount = binding.myReviewStarCount
    private val content = binding.myReviewContent

    fun bind(reviewListDTOList: ReviewListDTOList) {
        nickname.text = reviewListDTOList.nickName
        date.text = reviewListDTOList.createdAt
        rentDay.text = reviewListDTOList.lentDay
        // score 에 따라 다르게 해야할듯
        content.text = reviewListDTOList.content

        Glide
            .with(GlobalApplication.instance)
            .load(reviewListDTOList.profileUrl)
            .centerCrop()
            .placeholder(R.drawable.profile)
            .into(userProfile)

        Glide
            .with(GlobalApplication.instance)
            .load(reviewListDTOList.imageList)
            .placeholder(R.drawable.camera_dummy)
            .into(itemImg)
    }

}