package com.example.lifesharing.reservation.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.reservation.api.ReservationList

/** 예약 목록 리사이클러 뷰 어댑터 */

class ReservationListAdapter(
    private val items: ArrayList<ReservationList>,
    private val listener: ReservationClickListener
) : RecyclerView.Adapter<ReservationListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_list_item, parent, false)
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

    fun setItems(newItems: List<ReservationList>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): ReservationList {
        return items[position]
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val productImage: ImageView = view.findViewById(R.id.res_list_image)
        private val location: TextView = view.findViewById(R.id.res_list_location)
        private val productName: TextView = view.findViewById(R.id.res_list_product_name)
        private val startDate: TextView = view.findViewById(R.id.res_list_start_date)
        private val endDate: TextView = view.findViewById(R.id.res_list_end_date)
        private val status: ImageView = view.findViewById(R.id.res_list_status)

        fun bind(item: ReservationList) {
            Glide.with(productImage.context).load(item.productImage).into(productImage)
            location.text = item.location
            productName.text = item.productName
            startDate.text = item.startDate
            endDate.text = item.endDate

            when (item.status) {
                "대여" -> status.setImageResource(R.drawable.reservation_bluebtn)
                "MY" -> {
                    status.setImageResource(R.drawable.res_list_borrow_ic)
                    endDate.setTextColor(ContextCompat.getColor(endDate.context, R.color.red_100))
                }
                else -> status.setImageResource(R.drawable.reservation_bluebtn)
            }
        }
    }
}

interface ReservationClickListener {
    fun onItemClick(position: Int)
}
