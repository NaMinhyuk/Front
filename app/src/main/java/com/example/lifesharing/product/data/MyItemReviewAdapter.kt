package com.example.lifesharing.product.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.BuildConfig
import com.example.lifesharing.R
import com.example.lifesharing.product.api.ProductReview

class MyItemReviewAdapter(val reviews: ArrayList<ProductReview>) : RecyclerView.Adapter<MyItemReviewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_review_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 해당 위치의 리뷰 데이터를 ViewHolder에 바인딩
        val review = reviews[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int {
        // 리뷰 목록의 크기 반환
        return reviews.size
    }

    // 어댑터에 새로운 아이템 목록을 설정 및 업데이트
    fun setItems(newItems: List<ProductReview>) {
        reviews.clear()
        reviews.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        // 뷰 참조를 초기화
        val userImage : ImageView = itemView.findViewById(R.id.my_review_item_profile)
        val nickname: TextView = itemView.findViewById(R.id.my_review_nickname)
        val date: TextView = itemView.findViewById(R.id.my_review_date)
        val rentDay: TextView = itemView.findViewById(R.id.my_review_rent_day)
        val reviewImage : ImageView = itemView.findViewById(R.id.my_review_item_image)
        val rating : RatingBar = itemView.findViewById(R.id.review_ratingBar)
        val content : TextView = itemView.findViewById(R.id.my_review_content)

        private val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL  // 프로필 이미지를 로딩하기 위한 aws baseUrl

        fun bind(review: ProductReview) {
            Glide.with(itemView).load(IMAGE_BASE_URL + review.profileUrl).into(userImage)
            nickname.text = review.nickName
            date.text = review.createdAt
            rentDay.text = review.lentDay
            if (!review.imageList.isNullOrEmpty()){
                Glide.with(itemView).load(review.imageList.get(0)).into(reviewImage)
            } else {
                reviewImage.visibility = View.GONE
            }
            //Glide.with(itemView).load(review.imageList).into(reviewImage)   // 이미지 넣으면 주석 제거 하기
//            Glide.with(itemView).load(R.drawable.camera_dummy).into(reviewImage)     // 임시 코드
            rating.rating = review.score!!.toFloat()
            content.text = review.content
        }
    }
}