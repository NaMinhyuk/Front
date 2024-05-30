package com.example.lifesharing.mypage.review.registerReview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.mypage.review.model.response_body.ReviewListDTOList

class MyReviewListAdapter : RecyclerView.Adapter<MyReviewViewHolder>() {

    private var reviewItemList = ArrayList<ReviewListDTOList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        return MyReviewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_review_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.bind(this.reviewItemList[position])
    }

    override fun getItemCount(): Int {
        return this.reviewItemList.size
    }

    fun submitList(itemList : ArrayList<ReviewListDTOList>) {
        this.reviewItemList = itemList
    }

}