package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.mypage.model.response_body.HeartList
import java.text.NumberFormat
import java.util.Locale

/** 찜 목록 리사이클러뷰 어댑터 */
class WishListAdapter (val heartListItems: ArrayList<HeartList>, private val listener: OnItemClickListener) : RecyclerView.Adapter<WishListAdapter.WishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_wish_item, parent, false)
        return WishViewHolder(view)
    }

    // 특정위치의 데이터를ViewHolder에 바인딩하는 메서드
    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        val item = heartListItems[position]
        holder.bind(item)
    }

    // 찜 목록 아이템 개수 반환
    override fun getItemCount(): Int {
        return heartListItems.count()
    }

    // 어댑터에 새로운 아이템 목록을 설정 및 업데이트
    fun setItems(newItems: ArrayList<HeartList>) {
        heartListItems.clear()
        heartListItems.addAll(newItems)
        notifyDataSetChanged()
    }

    // 뷰에 데이터 로드
    inner class WishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.wish_item_img)
        private val location: TextView = itemView.findViewById(R.id.wish_location_tv)
        private val review: TextView = itemView.findViewById(R.id.wish_review_num_tv)
        private val name: TextView = itemView.findViewById(R.id.wish_item_name_tv)
        private val deposit: TextView = itemView.findViewById(R.id.wish_deposit_tv)
        private val day_price: TextView = itemView.findViewById(R.id.wish_day_price_tv)

        fun bind(item: HeartList) {
            // 가격 천 단위로 끊기
            val formatter = NumberFormat.getNumberInstance(Locale.US)

            Glide.with(itemView).load(item.imageUrl).into(image)
            location.text = item.location
            review.text = item.reviewCount.toString()
            name.text = item.name
            deposit.text = formatter.format(item.deposit)
            day_price.text = formatter.format(item.dayPrice)
        }

        // 아이템 클릭 리스너 초기화
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(heartListItems[position])
                }
            }
        }
    }
}

// 리사이클러 뷰 아이템 클릭
interface OnItemClickListener {
    fun onItemClick(heartList: HeartList)
}