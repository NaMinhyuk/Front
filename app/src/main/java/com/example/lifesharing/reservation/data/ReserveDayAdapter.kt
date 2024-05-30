package com.example.lifesharing.reservation.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.reservation.interfaces.CalendarDayClickListener

class ReserveDayAdapter(
    private val days: List<String>,
    private val reservationCount: Map<String, Int>,
    private val currentDate: String,
    private val listener: CalendarDayClickListener
) : RecyclerView.Adapter<ReserveDayAdapter.ViewHolder>() {

    private var selectedDate: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_reserve_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day, reservationCount[day] ?: 0, day == currentDate, day == selectedDate)
        holder.itemView.setOnClickListener {
            listener.onDayClick(day)
            notifyItemChanged(days.indexOf(selectedDate))
            selectedDate = day
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = days.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dayText: TextView = view.findViewById(R.id.res_cal_day_text)
        private val dayNum: TextView = view.findViewById(R.id.res_cal_day_num)
        private val selectedImage: ImageView = view.findViewById(R.id.res_item_Iv)

        fun bind(day: String, count: Int, isCurrent: Boolean, isSelected: Boolean) {
            dayText.text = day
            dayNum.text = count.toString()
            dayNum.visibility = if (count > 0) View.VISIBLE else View.INVISIBLE

            if (isSelected) {
                selectedImage.visibility = View.VISIBLE
            } else {
                selectedImage.visibility = View.INVISIBLE
            }

            val color = if (isCurrent) R.color.blue_300 else R.color.gray_900
            dayText.setTextColor(itemView.context.getColor(color))
            dayNum.setTextColor(itemView.context.getColor(color))
        }
    }

    fun setSelectedDate(date: String) {
        selectedDate = date
        notifyDataSetChanged()
    }
}
