package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R

/** 마이페이지 메뉴 리사이클러 뷰 어댑터 */
class MyPageMainListAdapter(val itemList: ArrayList<MyPageMainList>) :
    RecyclerView.Adapter<MyPageMainListAdapter.MyPageMainListViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null   // 클릭 이벤트를 처리할 리스너 객체 초기화

    // 아이템 클릭 콜백 인터페이스 정의
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // 클래스 외부에서 사용할 수 있도록 아이템 클릭 리스너 설정 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageMainListViewHolder {
        // 뷰 홀더에 사용될 뷰를 생성하고 초기화
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_main_list, parent, false)
        return MyPageMainListViewHolder(view)
    }

    // 특정위치의 ViewHolder에 데이터를 바인딩하는 메서드
    override fun onBindViewHolder(holder: MyPageMainListViewHolder, position: Int) {
        val currentItem = itemList[position]    // itemList에서 해당 위치의 아이템을 가져옴
        holder.list_name.text = currentItem.list  // 아이템의 이름을 설정

        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
    }

    // 메뉴 리스트의 수를 반환
    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyPageMainListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val list_name: TextView = itemView.findViewById(R.id.list_name)   // 메뉴 이름 설정을 하기 위해 뷰 참조
    }
}