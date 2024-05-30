package com.example.lifesharing.product.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DetailDayAdapter(
    val tempMonth: Int,
    val dayList: MutableList<Date>,
    private val onDateSelected: (Date) -> Unit // 날짜 선택 콜백 함수 추가
) : RecyclerView.Adapter<DetailDayAdapter.DayView>() {

    private val ROW = 6 // 주 수 (6주)
    private val dateFormat = SimpleDateFormat("d", Locale.getDefault()) // 날짜 포맷 설정

    // ViewHolder 클래스 정의
    class DayView(val layout: View) : RecyclerView.ViewHolder(layout)

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day, parent, false)
        return DayView(view)
    }

    // ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: DayView, position: Int) {
        // 날짜 텍스트뷰 초기화
        val dayText: TextView = holder.layout.findViewById(R.id.calendar_day_text)

        // 날짜 표시
        dayText.text = dateFormat.format(dayList[position])
        val calendar = Calendar.getInstance()
        calendar.time = dayList[position]

        // 이전 달과 다음 달의 날짜는 반투명 처리
        if (tempMonth != calendar.get(Calendar.MONTH)) {
            dayText.alpha = 0.4f
        } else {
            dayText.alpha = 1.0f
        }

        // 날짜 클릭 이벤트 처리
        holder.layout.setOnClickListener {
            onDateSelected(dayList[position])
        }
    }

    // 아이템 수 반환 (6주 * 7일 = 42)
    override fun getItemCount(): Int {
        return ROW * 7
    }
}