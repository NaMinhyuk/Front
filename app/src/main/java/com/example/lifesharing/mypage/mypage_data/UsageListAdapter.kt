package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R

class UsageListAdapter (private val usageList : List<UsageListData>) : RecyclerView.Adapter<UsageListAdapter.UsageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsageListAdapter.UsageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_usage_list, parent, false)
        return UsageViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsageListAdapter.UsageViewHolder, position: Int) {
        val item = usageList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return usageList.size
    }

    inner class UsageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usageProduct : TextView = itemView.findViewById(R.id.usage_list_product_tv)
        private val usageTime : TextView = itemView.findViewById(R.id.usage_list_tv1)
        private val usageDayFee : TextView = itemView.findViewById(R.id.usage_list_tv2)
        private val usageDeposit : TextView = itemView.findViewById(R.id.usage_list_tv5)
        fun bind(usageItem: UsageListData) {
            usageProduct.text = usageItem.product
            usageTime.text = usageItem.time
            usageDayFee.text = usageItem.dayFee
            usageDeposit.text = usageItem.deposit
        }
    }
}