package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R

class FAQListAdapter(private val faqList : List<FAQListData>) : RecyclerView.Adapter<FAQListAdapter.FAQViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FAQListAdapter.FAQViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_faq_list, parent, false)
        return FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQListAdapter.FAQViewHolder, position: Int) {
        val item = faqList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return faqList.size
    }

    inner class FAQViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val faqTitle: TextView = itemView.findViewById(R.id.faq_list_tv2)

        fun bind(faqItem: FAQListData) {
            faqTitle.text = faqItem.title
        }
    }
}