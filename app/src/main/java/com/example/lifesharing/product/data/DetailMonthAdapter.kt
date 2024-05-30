package com.example.lifesharing.product.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DetailMonthAdapter(private val context: Context, private val onDateSelected: (Date, Boolean) -> Unit) : RecyclerView.Adapter<DetailMonthAdapter.DayViewHolder>() {

    private var dates: List<Date> = emptyList()
    private var reservationPrices: Map<String, Int> = emptyMap()
    private val selectedDates: MutableSet<Date> = mutableSetOf()
    private val currentDate = Calendar.getInstance().time

    companion object {
        private const val VIEW_TYPE_NORMAL = 0
        private const val VIEW_TYPE_SELECTED = 1
    }

    // 아이템 뷰 타입 결정
    override fun getItemViewType(position: Int): Int {
        val date = dates[position]
        return if (selectedDates.contains(date)) VIEW_TYPE_SELECTED else VIEW_TYPE_NORMAL
    }

    // 뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_SELECTED) {
            R.layout.day_detail_selected_item
        } else {
            R.layout.day_detail_item
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return DayViewHolder(view)
    }

    // 뷰 홀더 바인딩
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val date = dates[position]
        val calendar = Calendar.getInstance()
        calendar.time = date

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateString = dateFormat.format(date)
        val price = reservationPrices[dateString]?.let { "$it" } ?: "x"

        holder.bind(date, price, date == currentDate)

        holder.itemView.setOnClickListener {
            toggleDateSelection(date)
        }
    }

    // 날짜 선택 토글
    private fun toggleDateSelection(date: Date) {
        val isSelected = if (selectedDates.contains(date)) {
            selectedDates.remove(date)
            false
        } else {
            selectedDates.add(date)
            true
        }
        onDateSelected(date, isSelected)
        notifyDataSetChanged()
    }

    // 아이템 개수 반환
    override fun getItemCount(): Int {
        return dates.size
    }

    // 데이터 업데이트
    fun updateData(newDates: List<Date>, newReservationPrices: Map<String, Int>) {
        dates = newDates
        reservationPrices = newReservationPrices
        notifyDataSetChanged()
    }

    // 선택된 날짜 반환
    fun getSelectedDates(): List<Date> {
        return selectedDates.toList()
    }

    // 뷰 홀더 클래스
    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.item_day_text) ?: itemView.findViewById(R.id.selected_item_day_text)
        private val priceTextView: TextView = itemView.findViewById(R.id.detail_cal_day_price) ?: itemView.findViewById(R.id.selected_detail_cal_day_price)
        private val selectedImageView: ImageView? = itemView.findViewById(R.id.selected_item_Iv)

        // 날짜 및 가격 텍스트뷰를 설정하고 현재 날짜 여부에 따라 텍스트 색상을 설정
        fun bind(date: Date, price: String, isCurrentDate: Boolean) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            dateTextView.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
            priceTextView.text = price

            // 현재 날짜인 경우 텍스트 색상을 파란색으로 설정
            if (isCurrentDate) {
                dateTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue_300))
                priceTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue_200))
            } else {
                dateTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray_900))
                priceTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray_400))
            }

            // 선택된 경우 흰색 텍스트와 배경을 표시하고 선택되지 않은 경우 기본값으로 설정
            if (selectedImageView != null) {
                dateTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                priceTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                selectedImageView.visibility = View.VISIBLE
            } else {
                // 선택되지 않은 상태의 기본값 설정 (현재 날짜와 중복되지 않게)
                if (!isCurrentDate) {
                    dateTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray_900))
                    priceTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray_900))
                }
                selectedImageView?.visibility = View.INVISIBLE
            }
        }
    }
}