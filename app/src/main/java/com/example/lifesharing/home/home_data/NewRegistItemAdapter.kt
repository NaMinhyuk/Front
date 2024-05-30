package com.example.lifesharing.home.home_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import java.text.NumberFormat
import java.util.Locale

/** 홈 제품 리사이클러 뷰 어댑터 */
class NewRegistItemAdapter(private val items: ArrayList<NewRegistItemData>, private val listener: HomeProductClickListener) : RecyclerView.Adapter<NewRegistItemAdapter.ViewHolder>() {

    // 제품 리스트의 수를 반환
    override fun getItemCount(): Int = items.size

    // ViewHolder에 데이터를 바인딩하는 메서드
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]    // 현재 position 의 데이터 항목 가져오기
        holder.bind(item)   // ViewHolder에 데이터 바인딩
        holder.itemView.setOnClickListener {   // 리스트 아이템 클릭 이벤트 처리
            listener.onItemClick(position)
        }
    }

    // 새로운 ViewHolder를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.home_new_regist_item, parent, false)
        //val item = items[viewType] // Get item by viewType
        return ViewHolder(inflatedView)
    }

    // 새로운 제품 데이터로 어댑터를 업데이트하는 메서드
    fun setItems(newItems: ArrayList<NewRegistItemData>) {
        items.clear()             // 기존 데이터 제거
        items.addAll(newItems)    // 새로운 데이터 추가
        notifyDataSetChanged()    // RecyclerView에 데이터 변경 알림
    }

    // 특정 위치의 데이터를 반환하는 메서드
    fun getItem(position: Int): NewRegistItemData {
        return items[position]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = itemView.findViewById(R.id.item_img)
        private val location: TextView = itemView.findViewById(R.id.location_tv)
        private val review: TextView = itemView.findViewById(R.id.review_num_tv)
        private val name: TextView = itemView.findViewById(R.id.new_item_name_tv)
        private val deposit: TextView = itemView.findViewById(R.id.deposit_tv)
        private val dayPrice: TextView = itemView.findViewById(R.id.day_price_tv)
        private val ratingBar : RatingBar = itemView.findViewById(R.id.home_ratingBar)

        // 데이터를 뷰에 바인딩하는 메서드
        fun bind(item: NewRegistItemData) {
            // 가격 천 단위로 끊기
            val formatter = NumberFormat.getNumberInstance(Locale.US)

            Glide.with(itemView).load(item.img).into(image)
            location.text = item.location
            review.text = "(" + item.reviewCount.toString() + ")"
            name.text = item.name
            deposit.text = formatter.format(item.deposit)
            dayPrice.text = formatter.format(item.dayPrice)
            ratingBar.rating = item.score.toFloat()
        }
    }
}

interface HomeProductClickListener{
    fun onItemClick(position: Int)
}

