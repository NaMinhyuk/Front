package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R

class NoticeListAdapter(private var noticeList: List<NoticeListData>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<NoticeListAdapter.NoticeViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun getItemAtPosition(position: Int): NoticeListData {
        return noticeList[position]
    }

    fun setData(newList: List<NoticeListData>) {
        noticeList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_notice_list, parent, false)
        return NoticeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val item = noticeList[position]
        holder.bind(item, itemClickListener)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noticeTitle: TextView = itemView.findViewById(R.id.notice_title_name)
        private val noticeDate: TextView = itemView.findViewById(R.id.notice_date_tv)
        private val noticeContent: TextView = itemView.findViewById(R.id.notice_content_tv)

        fun bind(noticeItem: NoticeListData, itemClickListener: OnItemClickListener) {
            noticeTitle.text = noticeItem.title
            noticeDate.text = noticeItem.date.toString()
            noticeContent.text = noticeItem.content

            // 아이템 클릭 시
            itemView.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
        }
    }
}