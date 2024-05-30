package com.example.lifesharing.product.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.product.interfaces.OnItemClickListener
import java.text.NumberFormat
import java.util.Locale

class MyItemAdapter(private val items: ArrayList<MyItemData>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MyItemAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val item: MyItemData, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = itemView.findViewById(R.id.item_img)
        private val location: TextView = itemView.findViewById(R.id.location_tv)
        private val review: TextView = itemView.findViewById(R.id.review_num_tv)
        private val name: TextView = itemView.findViewById(R.id.new_item_name_tv)
        private val deposit: TextView = itemView.findViewById(R.id.deposit_tv)
        private val dayPrice: TextView = itemView.findViewById(R.id.day_price_tv)
        private val ratingBar : RatingBar = itemView.findViewById(R.id.home_ratingBar)

        fun bind() {

            // 가격 천 단위로 끊기
            val formatter = NumberFormat.getNumberInstance(Locale.US)

            Glide.with(itemView).load(item.img).into(image)
            location.text = item.location
            review.text = item.reviewCount.toString()
            name.text = item.name
            deposit.text = formatter.format(item.deposit)
            dayPrice.text = formatter.format(item.dayPrice)
            ratingBar.rating = item.score.toFloat()
        }

        // 아이템 클릭 리스너
        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(position, item)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemAdapter.ViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.home_new_regist_item, parent, false)
        val item = items[viewType] // Get item by viewType
        return ViewHolder(inflatedView, item, listener)
    }

    override fun onBindViewHolder(holder: MyItemAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: ArrayList<MyItemData>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}