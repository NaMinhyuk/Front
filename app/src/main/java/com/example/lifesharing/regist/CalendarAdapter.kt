package com.example.lifesharing.regist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.adapters.ToolbarBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import org.w3c.dom.Text
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class CalendarAdapter(private val dayList: ArrayList<Date>):
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

        class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val dayText : TextView = itemView.findViewById(R.id.calendar_day_text)
        }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day, parent, false)
        return ItemViewHolder(view)
    }


    override fun getItemCount(): Int {
        return dayList.size
    }

    //데이터설정
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        //날짜 변수에 담기
        var monthDate = dayList[holder.adapterPosition]

        //초기화
        var dateCalendar = Calendar.getInstance()

        //날짜 켈린더에 담기
        dateCalendar.time = monthDate

        //켈린더 값 날짜변수에 담기
        var dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)

        holder.dayText.text = dayNo.toString()

        //넘어온 날짜
        val iYear = dateCalendar.get(Calendar.YEAR)
        val iMonth = dateCalendar.get(Calendar.MONTH) + 1
        val iDay = dateCalendar.get(Calendar.DAY_OF_MONTH)

        //현재 날짜
        var selectYear = CalendarClick.selectDate.get(Calendar.YEAR)
        var selectMonth = CalendarClick.selectDate.get(Calendar.MONTH) + 1
        var selectDay = CalendarClick.selectDate.get(Calendar.DAY_OF_MONTH)

        //넘어온 날짜와 현재 날짜 비교
        if(iYear == selectYear && iMonth == selectMonth) { //같으면 진하게
            holder.dayText.setTextColor(Color.parseColor("#1F2021"))

//            //현재 일자 비교해서 배경색 변경 배경색이 회색이 된다.
//            if(selectDay ==dayNo){
//                holder.itemView.setBackgroundColor(Color.LTGRAY)
//            }
        } else {
            holder.dayText.setTextColor(Color.parseColor("#BDBDBD"))
        }


//        //현재 날짜 배경색상 변경
//        if (CalendarClick.selectDate.get(Calendar.DAY_OF_MONTH) == dayNo) {
//            holder.itemView.setBackgroundColor(Color.LTGRAY)
//        }




        holder.itemView.setOnClickListener {
            // 인터페이스를 통해 날짜를 넘김

            val yearMonDay = "$iYear 년 $iMonth 월 $iDay 일"

//            //Toast 메세지를 통해 현재 날짜를 출력
//            Toast.makeText(holder.itemView.context, yearMonDay, Toast.LENGTH_SHORT).show()
        }
    }

}