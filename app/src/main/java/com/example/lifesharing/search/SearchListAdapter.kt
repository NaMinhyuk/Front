package com.example.lifesharing.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.databinding.SearchListItemBinding
import com.example.lifesharing.mypage.model.response_body.HeartList
import com.example.lifesharing.search.model.response_body.SearchResult
import java.text.NumberFormat
import java.util.Locale

class SearchListAdapter (val searchListItems: ArrayList<SearchResult>, private val listener: SearchClickListener) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>(){

    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        // 바인딩을 사용하여 뷰에 데이터 로드
        private val binding  = SearchListItemBinding.bind(itemView)

        // 각 뷰에 데이터 로드
        fun bind(item: SearchResult){
            Glide.with(itemView).load(item.imageUrl).into(binding.searchImage)

            binding.searchProductName.text = item.name
            binding.searchLocation.text = item.location

            if (item.review_count > 99){   // 리뷰 개수가 99개 이상일 때
                binding.searchReviewCount.text = "(" + item.review_count + " +)"
            } else{
                binding.searchReviewCount.text = "(" + item.review_count + ")"
            }

            // 찜 여부
            if (item.isLiked) {
                binding.searchHeart.setImageResource(R.drawable.full_heart)
            } else{
                binding.searchHeart.setImageResource(R.drawable.detail_heart)
            }

            // 가격 천 단위로 끊기
            val formatter = NumberFormat.getNumberInstance(Locale.US)
            binding.searchDayPrice.text = formatter.format(item.day_price)
            binding.searchHourPrice.text = formatter.format(item.hour_price)

        }

        // 아이템 클릭 리스너 초기화
        init{
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(searchListItems[position])
                }
            }
        }
    }

    // search_list_item 레이아웃을 인플레이트하고 새 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchListItems.size
    }

    //지정된 위치의 데이터를 ViewHolder에 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = searchListItems[position]
        item?.let { holder.bind(it) } // 여기에서 `bind` 함수 호출 전에 `item`이 `null`인지 확인
    }


    // 어댑터에 새로운 아이템 목록을 설정 및 업데이트
    fun setItems(newItems: ArrayList<SearchResult>?) {
        searchListItems.clear()
        searchListItems.addAll(newItems!!)
        notifyDataSetChanged()
    }
}

interface SearchClickListener{
    fun onItemClick(searchItem : SearchResult)
}