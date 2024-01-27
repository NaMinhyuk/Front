package com.example.lifesharing.home.home_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R

class NewRegistItemAdapter (private val items: ArrayList<NewRegistItemData>) : RecyclerView.Adapter<NewRegistItemAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.new_regist_item, parent, false)
        return ViewHolder(inflatedView)
    }

    fun setItems(newItems: ArrayList<NewRegistItemData>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    // 각 항목에 필요한 기능을 구현
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image : ImageView = itemView.findViewById(R.id.item_img)
        private val location : TextView = itemView.findViewById(R.id.location_tv)
        private val review : TextView = itemView.findViewById(R.id.review_num_tv)
        private val name : TextView = itemView.findViewById(R.id.new_item_name_tv)
        private val deposit : TextView = itemView.findViewById(R.id.deposit_tv)
        private val a_day_fee : TextView = itemView.findViewById(R.id.day_fee_tv)

        fun bind(item: NewRegistItemData) {
            Glide.with(itemView).load(item.img).into(image)
            location.text = item.location
            review.text = item.review
            name.text = item.name
            deposit.text = item.deposit.toString()
            a_day_fee.text = item.a_day_fee.toString()
        }
    }
}