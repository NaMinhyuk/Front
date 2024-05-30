package com.example.lifesharing.product.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.home.home_data.NewRegistItemData
import com.example.lifesharing.product.api.CategoryItem
import com.example.lifesharing.product.interfaces.CategoryItemClickListener
import java.text.NumberFormat
import java.util.Locale

/** 카테고리별 제품 리사이클러 뷰 어댑터 */
class MenuAdapter(private val items: ArrayList<CategoryItem>, private val listener : CategoryItemClickListener) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {



    // 새로운 ViewHolder를 생성하는 메서드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_re_product_menu,parent,false)
        return ViewHolder(view)
    }

    // 제품 리스트의 수를 반환
    override fun getItemCount(): Int = items.size

    // ViewHolder에 데이터를 바인딩하는 메서드
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])    // 현재 position 의 데이터 항목 가져와 ViewHolder에 데이터 바인딩(로드)
    }

    // 새로운 제품 데이터로 어댑터를 업데이트하는 메서드
    fun setItems(newItems: ArrayList<CategoryItem>) {
        items.clear()            // 기존 데이터 제거
        items.addAll(newItems)   // 새로운 데이터 추가
        notifyDataSetChanged()   // RecyclerView에 데이터 변경 알림
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image : ImageView = itemView.findViewById(R.id.product_image)
        private val location : TextView = itemView.findViewById(R.id.location_text_tv)
        private val review : TextView = itemView.findViewById(R.id.review_tv)
        private val name : TextView = itemView.findViewById(R.id.name_tv)
        private val money_keep : TextView = itemView.findViewById(R.id.money_keep_tv)
        private val money_day : TextView = itemView.findViewById(R.id.money_day_tv)

        // 데이터를 뷰에 바인딩하는 메서드
        fun bind(item: CategoryItem) {

            // 가격 천 단위로 끊기
            val formatter = NumberFormat.getNumberInstance(Locale.US)

            Glide.with(itemView).load(item.imageUrl).into(image)
            location.text = item.location
            review.text = item.reviewCount.toString()
            name.text = item.name
            money_keep.text = formatter.format(item.deposit)
            money_day.text = formatter.format(item.dayPrice)
        }

        // 리사이클러뷰 아이템 클릭 리스너 초기화
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(items[position])
                }
            }
        }
    }
}

