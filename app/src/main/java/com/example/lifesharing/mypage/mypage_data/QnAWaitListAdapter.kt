package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.mypage.mypage_api.InquiryDTO

class QnAWaitListAdapter(private val qnaList: ArrayList<InquiryDTO>) : RecyclerView.Adapter<QnAWaitListAdapter.QnAListViewHolder>() {

    private var qnaListData = qnaList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QnAListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_qna_waiting_list, parent, false)
        return QnAListViewHolder(view)
    }

    override fun onBindViewHolder(holder: QnAListViewHolder, position: Int) {
        holder.bind(this.qnaListData[position])
    }

    override fun getItemCount(): Int {
        return qnaList.size
    }

    inner class QnAListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val qnaListTitle: TextView = itemView.findViewById(R.id.qna_wait_list_title_name)
        private val qnaListDate: TextView = itemView.findViewById(R.id.qna_wait_list_date_tv)
        private val qnaListContent: TextView = itemView.findViewById(R.id.qna_wait_list_content_tv)

        fun bind(qnaItem: InquiryDTO) {
            qnaListTitle.text = qnaItem.title
            qnaListDate.text = qnaItem.createdAt.toString()
            qnaListContent.text = qnaItem.body
        }
    }
}