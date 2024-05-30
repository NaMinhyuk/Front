package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.MyPageQnaCompleteListBinding

/** 문의 답변 완료 리스트 리사이클러뷰 어댑터 */
class QnACompleteListAdapter(private val qnaList: List<QnAListData>) : RecyclerView.Adapter<QnACompleteListAdapter.QnACompleteListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QnACompleteListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_qna_complete_list, parent, false)
        return QnACompleteListViewHolder(view)
    }

    // 특정위치의 데이터를ViewHolder에 바인딩하는 메서드
    override fun onBindViewHolder(holder: QnACompleteListViewHolder, position: Int) {
        val item = qnaList[position]
        holder.bind(item)
    }

    // 답변 완료 리스트 아이템 개수 반환
    override fun getItemCount(): Int {
        return qnaList.size
    }

    inner class QnACompleteListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 바인딩을 사용하여 뷰에 데이터 로드
        private val binding = MyPageQnaCompleteListBinding.bind(itemView)
        fun bind(qnaItem: QnAListData) {
            binding.qnaCompleteListTitleName.text = qnaItem.title
            binding.qnaCompleteListDateTv.text = qnaItem.date.toString()
            binding.qnaCompleteListContentTv.text = qnaItem.content
        }
    }
}