package com.example.mypage.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R

class MyPageMainListAdapter(val itemList: ArrayList<MyPageMainList>) :
    RecyclerView.Adapter<MyPageMainListAdapter.MyPageMainListViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    // 아이템 클릭 콜백 인터페이스 정의
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // 아이템 클릭 리스너 설정 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageMainListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_main_list, parent, false)
        return MyPageMainListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPageMainListViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.list_name.text = currentItem.list

        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyPageMainListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val list_name: TextView = itemView.findViewById(R.id.list_name)
    }
}