package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.QnaWaitListItemBinding
import com.example.lifesharing.mypage.model.response_body.InquiryItem
import java.text.SimpleDateFormat
import java.util.Locale

/** 문의 답변 대기 리스트 리사이클러뷰 어댑터 */
class QnaWaitListAdapter (val qnaListItems: ArrayList<InquiryItem>, private val listener: QnaListClickListener): RecyclerView.Adapter<QnaWaitListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View, private val listener: QnaListClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        // 바인딩을 사용하여 뷰에 데이터 로드
        private val binding = QnaWaitListItemBinding.bind(itemView)

        fun bind(item: InquiryItem){
            binding.qnaWaitTitleTv.text = item.title
            binding.qnaWaitBodyTv.text = item.body

            // 서버에서 받은 날짜 형식 (예: "2024-02-07T17:30:33.143212")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
            // 변환하고 싶은 날짜 형식 (예: "24.04.04")
            val outputFormat = SimpleDateFormat("yy.MM.dd", Locale.getDefault())

            // String을 Date 객체로 파싱
            binding.writedAt.text = outputFormat.format(inputFormat.parse(item.createdAt))
        }

        // 아이템 클릭 리스너 초기화
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(qnaListItems[position])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.qna_wait_list_item, parent, false)
        return ViewHolder(view, listener)
    }

    // 답변 대기 리스트의 수를 반환
    override fun getItemCount(): Int {
        return qnaListItems.size
    }

    // 특정위치의 데이터를ViewHolder에 바인딩하는 메서드
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = qnaListItems[position]
        holder.bind(item)
    }

    // 어댑터에 새로운 아이템 목록을 설정 및 업데이트
    fun setItem(items : ArrayList<InquiryItem>){
        qnaListItems.clear()
        qnaListItems.addAll(items)
        notifyDataSetChanged()
    }
}

interface QnaListClickListener{
    fun onItemClick(qnaList : InquiryItem)
}