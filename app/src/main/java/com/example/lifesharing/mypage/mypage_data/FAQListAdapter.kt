package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R

class FAQListAdapter(private val faqList: List<FAQListData>) : RecyclerView.Adapter<FAQListAdapter.FAQViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_faq_list, parent, false)
        return FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        val item = faqList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            item.isExpanded = !item.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return faqList.size
    }

    inner class FAQViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val faqTitle: TextView = itemView.findViewById(R.id.faq_list_tv2)
        private val faqDetailImageView: ImageView = itemView.findViewById(R.id.faq_list_detail_iv)
        private val faqDetailTextView: TextView = itemView.findViewById(R.id.faq_list_detail_tv)

        fun bind(faqItem: FAQListData) {
            faqTitle.text = faqItem.title
            if (faqItem.isExpanded) {
                faqDetailImageView.visibility = View.VISIBLE
                faqDetailTextView.visibility = View.VISIBLE
            } else {
                faqDetailImageView.visibility = View.GONE
                faqDetailTextView.visibility = View.GONE
            }
        }
    }
}