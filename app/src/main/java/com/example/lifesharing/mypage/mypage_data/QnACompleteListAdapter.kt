package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R

class QnACompleteListAdapter(private val qnaList: List<QnAListData>) : RecyclerView.Adapter<QnACompleteListAdapter.QnACompleteListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QnACompleteListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_qna_complete_list, parent, false)
        return QnACompleteListViewHolder(view)
    }

    override fun onBindViewHolder(holder: QnACompleteListViewHolder, position: Int) {
        val item = qnaList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return qnaList.size
    }

    inner class QnACompleteListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val qnaListTitle: TextView = itemView.findViewById(R.id.qna_complete_list_title_name)
        private val qnaListDate: TextView = itemView.findViewById(R.id.qna_complete_list_date_tv)
        private val qnaListContent: TextView = itemView.findViewById(R.id.qna_complete_list_content_tv)

        fun bind(qnaItem: QnAListData) {
            qnaListTitle.text = qnaItem.title
            qnaListDate.text = qnaItem.date.toString()
            qnaListContent.text = qnaItem.content
        }
    }
}