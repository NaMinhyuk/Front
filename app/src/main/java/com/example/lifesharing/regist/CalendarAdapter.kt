package com.example.lifesharing.regist

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.adapters.ToolbarBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

/** 캘린더 리사이클러 뷰 어댑터 */
class CalendarAdapter(
    private val dayList: ArrayList<Date>,
    private val dateSelectListener: DateSelectListener,
    // 시작 및 종료 날짜를 어댑터에 전달받을 수 있도록 추가
    var startDate: Date?,
    var endDate: Date?
): RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayText : TextView = itemView.findViewById(R.id.calendar_day_text)
    }

    val TAG = "로그"

    // 화면 설정 - 새로운 뷰 홀더를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // XML 레이아웃을 인플레이트하여 뷰를 생성
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day, parent, false)
        return ItemViewHolder(view)
    }


    override fun getItemCount(): Int {
        return dayList.size
    }

    // 데이터 설정 - 각 아이템 뷰에 데이터를 바인딩
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        //날짜 변수에 담기
        var monthDate = dayList[holder.adapterPosition]
//        val date = dayList[position]
//        val day = Calendar.getInstance()

        // monthDate를 기준으로 Calendar 객체를 생성
        var dateCalendar = Calendar.getInstance().apply { time = monthDate }

        // 캘린더 값 날짜 변수에 담기
        var dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH)  // 해당 날짜의 '일(day)'을 가져옴
        holder.dayText.text = dayNo.toString()               // 일(day) 값을 텍스트로 설정

        // 기본 배경 색상 설정
        holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        // 해당 월의 날짜들이 제대로 들어오는지 확인용 로그
        Log.d(TAG, "Binding view at position $position with date: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(monthDate)}")


        //넘어온 날짜
//        val iYear = dateCalendar.get(Calendar.YEAR)
//        val iMonth = dateCalendar.get(Calendar.MONTH) + 1
//        val iDay = dateCalendar.get(Calendar.DAY_OF_MONTH)
//
//        //현재 날짜
//        var selectYear = CalendarClick.selectDate.get(Calendar.YEAR)
//        var selectMonth = CalendarClick.selectDate.get(Calendar.MONTH) + 1
//        var selectDay = CalendarClick.selectDate.get(Calendar.DAY_OF_MONTH)
//
//        //넘어온 날짜와 현재 날짜 비교
//        if(iYear == selectYear && iMonth == selectMonth) { //같으면 진하게
//            holder.dayText.setTextColor(Color.parseColor("#1F2021"))
//
//            //현재 일자 비교해서 배경색 변경 배경색이 회색이 된다.
//            if(selectDay ==dayNo){
//                holder.itemView.setBackgroundColor(Color.LTGRAY)
//            }
//        } else {
//            holder.dayText.setTextColor(Color.parseColor("#BDBDBD"))
//        }
//
//
//        //현재 날짜 배경색상 변경
//        if (CalendarClick.selectDate.get(Calendar.DAY_OF_MONTH) == dayNo) {
//            holder.itemView.setBackgroundColor(Color.parseColor("#60A2EF"))
//        }

        // 오늘 날짜를 00:00:00으로 설정
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }


        // 오늘 이전의 날짜는 클릭 리스너를 비활성화하여 선택할 수 없게 함
        if (dateCalendar.before(today)) {
            holder.itemView.isEnabled = false  // 오늘 이전의 날짜는 선택 불가
            holder.dayText.alpha = 0.5f        // 과거 날짜는 흐리게 표시
            holder.itemView.setOnClickListener(null) // 클릭 리스너 제거
        }
        else {
            holder.itemView.isEnabled = true
            holder.dayText.alpha = 1.0f       // 선택 가능한 날짜는 명확하게 표시
            holder.itemView.setOnClickListener {
                // 오늘 이후의 날짜를 선택했을 때만 인터페이스를 통해 날짜를 넘김
                dateSelectListener.onDateSelected(monthDate)

                // 시작 날짜가 선택 안 된 경우 or 시작 날짜, 종료 날짜 모두 선택된 경우
                if (startDate == null || (endDate != null && startDate != null)) {
                    // 새로 선택
                    startDate = monthDate    // 현재 날짜를 시작 날짜로 지정
                    endDate = null           // 종료 날짜 reset
                }
                // 종료 날짜가 선택 안 된 경우 and 시작 날짜가 선택 안 된 경우 and 선택 날짜가 시작 날짜 이후인 경우
                else if (endDate == null && startDate != null && monthDate.after(startDate)) {
                    endDate = monthDate   // 현재 날짜를 종료 날짜로 지정
                }

                notifyDataSetChanged()  // 갱신
            }
        }

        // 날짜 색칠 로직 1
//        if (startDate != null && endDate != null) {
//            if (!monthDate.before(startDate) && !monthDate.after(endDate)) {
//                // 선택된 기간 동안 색칠
//                holder.itemView.setBackgroundColor(Color.parseColor("#1460A2EF"))
//                holder.dayText.setTextColor(Color.parseColor("#1277ED"))
//            } else {
//                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
//                holder.dayText.setTextColor(Color.BLACK)  // Default text color
//            }
//        } else if (startDate != null && monthDate.compareTo(startDate) == 0) {
//            // 시작 날짜만 선택된 경우 색칠
//            holder.itemView.setBackgroundColor(Color.parseColor("#1277ED"))
//            holder.dayText.setTextColor(Color.WHITE)
//        } else {
//            // 그 외에는 색칠하지 않음
//            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
//            holder.dayText.setTextColor(Color.BLACK)
//        }


        // 날짜 색칠 로직 3 (24.05.22)
        when {
            // 시작 날짜와 종료 날짜가 설정된 경우 and 현재 날짜가 시작 날짜나 종료 날짜와 같은 경우 배경 & 텍스트 색 변경
            startDate != null && endDate != null && (monthDate == startDate || monthDate == endDate) -> {
                holder.itemView.setBackgroundColor(Color.parseColor("#1277ED")) // 시작 & 종료 날짜
                holder.dayText.setTextColor(Color.WHITE)
            }
            // 시작 날짜 사이와 종료 날짜 사이의 날짜 배경 & 텍스트 색 변경
            startDate != null && endDate != null && !monthDate.before(startDate) && !monthDate.after(endDate) -> {
                holder.itemView.setBackgroundColor(Color.parseColor("#1460A2EF")) // 사이 날짜
                holder.dayText.setTextColor(Color.parseColor("#1277ED"))
            }
            // 시작 날짜 & 종료 날짜 모두 선택 안된 경우
            else -> {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff")) // 기본
                holder.dayText.setTextColor(Color.BLACK)
            }
        }
    }

    /**
     * 날짜 갱신 및 어댑터 갱신을 담당하는 메소드
     */
    fun updateSelectedDates(startDate: Date?, endDate: Date?) {
        this.startDate = startDate
        this.endDate = endDate
        notifyDataSetChanged()    // 데이터 변경을 어댑터에 알리고 UI를 업데이트
    }
}