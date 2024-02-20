package com.example.lifesharing.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentReservationBinding

class ReservationMenuAdapter(private val reservationDataList: List<ReservationData>) : RecyclerView.Adapter<ReservationMenuAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.reservation_item_image)
        val locationTextView: TextView = itemView.findViewById(R.id.reservation_item_location_tv)
        val titleTextView: TextView = itemView.findViewById(R.id.reservation_item_title)
        val subtitle1TextView: TextView = itemView.findViewById(R.id.reservation_item_subtitle1_tv)
        val subtitle2TextView: TextView = itemView.findViewById(R.id.reservation_item_subtitle2_tv)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservation_reservelist,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = reservationDataList[position]

        // 예시로 각 TextView에 데이터를 설정합니다.
        holder.titleTextView.text = currentItem.title
        holder.locationTextView.text = currentItem.location
        holder.subtitle1TextView.text = currentItem.subtitle1
        holder.subtitle2TextView.text = currentItem.subtitle2

        // 이미지 리소스를 설정합니다.
        holder.imageView.setImageResource(R.drawable.camara)
    }

    override fun getItemCount(): Int {
        return reservationDataList.size
    }


}