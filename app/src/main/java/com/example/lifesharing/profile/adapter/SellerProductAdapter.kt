package com.example.lifesharing.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.profile.model.response_body.SellerProductList
import java.text.NumberFormat
import java.util.Locale

class SellerProductAdapter(private val items:ArrayList<SellerProductList>, private val listener: SellerProductListener): RecyclerView.Adapter<SellerProductAdapter.ViewHolder>() {

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view){

        private val image : ImageView = itemView.findViewById(R.id.product_image)
        private val location : TextView = itemView.findViewById(R.id.location_text_tv)
        private val review : TextView = itemView.findViewById(R.id.review_tv)
        private val name : TextView = itemView.findViewById(R.id.name_tv)
        private val deposit : TextView = itemView.findViewById(R.id.money_keep_tv)
        private val dayPrice : TextView = itemView.findViewById(R.id.money_day_tv)

        fun bind(item: SellerProductList) {
            // 가격 천 단위로 끊기
            val formatter = NumberFormat.getNumberInstance(Locale.US)

            Glide.with(itemView).load(item.imageUrl).into(image)
            location.text = item.location
            review.text = item.reviewCount.toString()
            name.text = item.name
            deposit.text = formatter.format(item.deposit)
            dayPrice.text = formatter.format(item.dayPrice)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_re_product_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: ArrayList<SellerProductList>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): SellerProductList {
        return items[position]
    }
}

interface SellerProductListener{
    fun onItemClick(position: Int)
}