package com.example.lifesharing.profile.adapter

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
import com.example.lifesharing.profile.model.response_body.ReviewData

class SellerReviewAdapter(private val items: ArrayList<ReviewData>) : RecyclerView.Adapter<SellerReviewAdapter.ViewHolder> (){

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        private val image : ImageView = itemView.findViewById(R.id.my_review_item_profile)
        private val nickName : TextView = itemView.findViewById(R.id.my_review_nickname)
        private val reviewDate : TextView = itemView.findViewById(R.id.my_review_date)
        private val lentDay : TextView = itemView.findViewById(R.id.my_review_rent_day)
        private val reviewImage : ImageView = itemView.findViewById(R.id.my_review_item_image)
        private val ratingBar : RatingBar = itemView.findViewById(R.id.review_ratingBar)
        private val content : TextView = itemView.findViewById(R.id.my_review_content)

        private val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL  // 프로필 이미지를 로딩하기 위한 aws baseUrl

        fun bind(item : ReviewData){
            Glide.with(itemView).load(IMAGE_BASE_URL + item.profileUrl).into(image)
            nickName.text = item.nickName
            reviewDate.text = item.createdAt
            lentDay.text = item.lentDay
            if(!item.imageList.isNullOrEmpty()) {
                Glide.with(itemView).load(item.imageList?.get(0)).into(reviewImage)
            } else {
                reviewImage.visibility = View.GONE
            }
            ratingBar.rating = item.score.toFloat()
            content.text = item.content
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SellerReviewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_review_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SellerReviewAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: ArrayList<ReviewData>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}