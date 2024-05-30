package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.databinding.RegisterHistoryItemBinding
import com.example.lifesharing.mypage.model.response_body.MyRegProductList

/** 제품 등록 내역 리스트 리사이클러뷰 어댑터 */
class RegistHistoryAdapter (val registListItems: ArrayList<MyRegProductList>, private val listener: ProductClickListener): RecyclerView.Adapter<RegistHistoryAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View, private val listener: ProductClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        // 바인딩을 사용하여 뷰에 데이터 로드
        private val binding = RegisterHistoryItemBinding.bind(itemView)


        fun bind(item: MyRegProductList) {
            Glide.with(itemView).load(item.imageUrl).into(binding.productImg)
            binding.registProductLocation.text = item.location
            binding.registProductNameTv.text = item.name
            binding.startDate.text = item.startDate
            binding.endDate.text = item.endDate
        }

        // 아이템 클릭 리스너 초기화
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(registListItems[position])
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RegistHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.register_history_item, parent, false)
        return ViewHolder(view, listener)
    }

    // 특정위치의 데이터를ViewHolder에 바인딩하는 메서드
    override fun onBindViewHolder(holder: RegistHistoryAdapter.ViewHolder, position: Int) {
        val item = registListItems[position]
        holder.bind(item)
    }

    // 제품 리스트의 수를 반환
    override fun getItemCount(): Int {
        return registListItems.size
    }

    // 어댑터에 새로운 아이템 목록을 설정 및 업데이트환
    fun setItem(items : ArrayList<MyRegProductList>){
        registListItems.clear()
        registListItems.addAll(items)
        notifyDataSetChanged()
    }
}

interface ProductClickListener {
    fun onItemClick(registerList: MyRegProductList)
}

