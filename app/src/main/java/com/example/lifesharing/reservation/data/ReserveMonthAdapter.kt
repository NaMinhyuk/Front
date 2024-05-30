package com.example.lifesharing.reservation.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.reservation.interfaces.CalendarDayClickListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReserveMonthAdapter(
    private val days: List<String>,
    private val reservationCount: Map<String, Int>,
    private val currentDate: String,
    private val listener: CalendarDayClickListener
) : RecyclerView.Adapter<ReserveMonthAdapter.ViewHolder>() {

    private var selectedDate: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_reserve_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day)
    }

    override fun getItemCount(): Int = days.size

    fun setSelectedDate(date: String) {
        selectedDate = date
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dayText: TextView = view.findViewById(R.id.res_cal_day_text)
        private val dayNum: TextView = view.findViewById(R.id.res_cal_day_num)
        private val selectedImage: ImageView = view.findViewById(R.id.res_item_Iv)

        fun bind(day: String) {
            dayText.text = day

            val count = reservationCount[day] ?: 0
            if (count > 0) {
                dayNum.visibility = View.VISIBLE
                dayNum.text = count.toString()
                dayText.setTextColor(ContextCompat.getColor(dayText.context, R.color.blue_300))
            } else {
                dayNum.visibility = View.INVISIBLE
            }

            if (day == currentDate) {
                dayText.setTextColor(ContextCompat.getColor(dayText.context, R.color.blue_300))
                if (count == 0) {
                    dayNum.visibility = View.INVISIBLE
                }
            }

            itemView.setOnClickListener {
                selectedDate = day
                selectedImage.visibility = View.VISIBLE
                listener.onDayClick(day)
            }

            selectedImage.visibility = if (selectedDate == day) View.VISIBLE else View.GONE
        }
    }
}