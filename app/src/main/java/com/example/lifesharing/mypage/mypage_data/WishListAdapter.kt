package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R

class WishListAdapter (val WishListItem: ArrayList<WishListData>) : RecyclerView.Adapter<WishListAdapter.WishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_wish_item, parent, false)
        return WishViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        val item = WishListItem[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return WishListItem.count()
    }


    inner class WishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.wish_item_img)
        private val location: TextView = itemView.findViewById(R.id.wish_location_tv)
        private val review: TextView = itemView.findViewById(R.id.wish_review_num_tv)
        private val name: TextView = itemView.findViewById(R.id.wish_item_name_tv)
        private val deposit: TextView = itemView.findViewById(R.id.wish_deposit_tv)
        private val day_price: TextView = itemView.findViewById(R.id.wish_day_price_tv)

        fun bind(item: WishListData) {
            Glide.with(itemView).load(item.img).into(image)
            location.text = item.location
            review.text = item.reviewCount.toString()
            name.text = item.name
            deposit.text = item.deposit.toString()
            day_price.text = item.dayPrice.toString()
        }
    }
}