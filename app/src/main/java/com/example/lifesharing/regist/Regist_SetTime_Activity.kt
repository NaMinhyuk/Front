package com.example.lifesharing.regist

import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityRegistSettimeBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Regist_SetTime_Activity :AppCompatActivity() {
    val TAG = "로그"

    private lateinit var binding: ActivityRegistSettimeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistSettimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //화면설정
        setMonthView()

        binding.calendarPreBtn.setOnClickListener {
            CalendarClick.selectDate.add(Calendar.MONTH, -1)
            setMonthView()
        }
        binding.calendarNextBtn.setOnClickListener {
            CalendarClick.selectDate.add(Calendar.MONTH, +1)
            setMonthView()
        }

        val topview_array = arrayOf(0,0)
        //상단의 날짜 둘중 하나만 클릭해도 색이 변경되어야 함.
        binding.registSettimeTopBeforeMonth.setOnClickListener {
            topview_array[0] = 1
            color_before_time_blue()
            if (topview_array[1] == 1){
                topview_array[1] = 0
                color_after_time_gray()
            }
        }
        binding.registSettimeTopBeforeTime.setOnClickListener {
            topview_array[0] = 1
            color_before_time_blue()
            if (topview_array[1] == 1){
                topview_array[1] = 0
                color_after_time_gray()
            }
        }
        binding.registSettimeTopAfterTime.setOnClickListener {
            topview_array[1] = 1
            color_after_time_blue()
            if(topview_array[0] ==1){
                topview_array[0] = 0
                color_before_time_gray()
            }
        }
        binding.registSettimeTopAfterMonth.setOnClickListener {
            topview_array[1] = 1
            color_after_time_blue()
            if(topview_array[0] ==1){
                topview_array[0] = 0
                color_before_time_gray()
            }
        }

        val morning_afternoon_Array = arrayOf(0,0)
        binding.registSettimeMorningBtn.setOnClickListener {
            morning_afternoon_Array[0] = 1
            if(morning_afternoon_Array[1] == 1){
                morning_afternoon_Array[1] = 0
                binding.registSettimeAfternoonBtn.setImageResource(R.drawable.reserve_time_btn)
                binding.registSettimeAfternoonTv.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
            }
            binding.registSettimeMorningBtn.setImageResource(R.drawable.regist_add_clickbtn)
            binding.registSettimeMorningTv.setTextColor(Color.parseColor("#1277ED"))

            val text = binding.registSettimeTopBeforeTime.text.toString()
            Log.d(TAG, "${text}")
            text.replace("오후","오전")
            binding.registSettimeTopBeforeTime.text = text
        }
        binding.registSettimeAfternoonBtn.setOnClickListener {
            morning_afternoon_Array[1] = 1
            if(morning_afternoon_Array[0] == 1){
                morning_afternoon_Array[0] = 0
                binding.registSettimeMorningBtn.setImageResource(R.drawable.reserve_time_btn)
                binding.registSettimeMorningTv.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
            }
            binding.registSettimeAfternoonBtn.setImageResource(R.drawable.regist_add_clickbtn)
            binding.registSettimeAfternoonTv.setTextColor(Color.parseColor("#1277ED"))
            val text = binding.registSettimeTopBeforeTime.text.toString()
            Log.d(TAG, "${text}")
            text.replace("오전","오후")
            binding.registSettimeTopBeforeTime.text = text
        }

        val time_Array = arrayOf(0,0,0,0,0,0,0,0,0,0,0,0)

        val time_btn_array = arrayOf(binding.registSettimeHour1Btn,binding.registSettimeHour2Btn,binding.registSettimeHour3Btn,
            binding.registSettimeHour4Btn,binding.registSettimeHour5Btn,binding.registSettimeHour6Btn,binding.registSettimeHour7Btn,
            binding.registSettimeHour8Btn,binding.registSettimeHour9Btn,binding.registSettimeHour10Btn,binding.registSettimeHour11Btn,
            binding.registSettimeHour12Btn)

        val time_text_array = arrayOf(binding.registSettime1Tv,binding.registSettimeHour2Tv,binding.registSettimeHour3Tv,
            binding.registSettimeHour4Tv,binding.registSettimeHour5Tv,binding.registSettimeHour6Tv,binding.registSettimeHour7Tv,
            binding.registSettimeHour8Tv,binding.registSettimeHour9Tv,binding.registSettimeHour10Tv,binding.registSettimeHour11Tv,
            binding.registSettimeHour12Tv)


        for (i in 0..11) {
            time_btn_array[i].setOnClickListener {
                time_Array[i] = 1
                for (j in 0..11){
                    if(i==j) continue
                    else time_Array[j] = 0
                }
                for (j in 0..11) {
                    if (time_Array[j] == 1) {
                        time_btn_array[j].setImageResource(R.drawable.regist_add_clickbtn)
                        time_text_array[j].setTextColor(Color.parseColor("#1277ED"))
                    } else {
                        time_btn_array[j].setImageResource(R.drawable.reserve_time_btn)
                        time_text_array[j].setTextColor(ContextCompat.getColor(this,R.color.gray_800))
                    }
                }
            }
        }

        binding.registSettimeBackBtn.setOnClickListener {
            finish()
        }
        binding.registSettimeBottomTextbox.setOnClickListener {
            // default 세팅
            var time = 0
            var set = "오전"

            // 설정과 시간에 대한 정보 가져오기
            if (morning_afternoon_Array[0] != 1) {
                set = "오후"
            }

            for (i in 0..11) {
                if (time_Array[i] == 1) {
                    time = i+1
                    break // 원하는 시간을 찾으면 루프를 중지.
                }
            }

            // 시간과 설정을 Intent에 추가하여 전달
            val intent = Intent(this, Regist_Add_Activity::class.java)
            intent.putExtra("시간", time)
            intent.putExtra("설정", set)

            startActivity(intent)
            finish()
        }

    }

    //화면에 날짜 보여주기
    private fun setMonthView() {
        binding.calendarMonthyear.text = monthYearFromData(CalendarClick.selectDate)

        //날짜 생성해서 리스트에 담기
        val dayList = dayMonthArray()

        //어댑터 초기화
        val adapter = CalendarAdapter(dayList)

        //레이아웃 설정 (열 7개)
        var manager : RecyclerView.LayoutManager = GridLayoutManager(applicationContext,7)

        //레이아웃 적용
        binding.calendarRv.layoutManager = manager

        //어뎁터 적용
        binding.calendarRv.adapter = adapter

    }

    //날짜 타입 (월,년))
    private fun monthYearFromData(calendar: Calendar): String {
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) +1
        return "$month 월 $year"
    }


    //날짜 생성
    private fun dayMonthArray(): ArrayList<Date> {
        val dayList: ArrayList<Date> = ArrayList()

        var monthCalendar = CalendarClick.selectDate.clone() as Calendar

        //1일로 세팅
        monthCalendar[Calendar.DAY_OF_MONTH] =1

        //해당 달의 1일의 요일 [1:일요일... 7:토요일]
        val firstDayofMonth = monthCalendar[Calendar.DAY_OF_WEEK] -1

        //요일 숫자 만큼 이전날짜 추가 *6월 1일이 수요일이면 3만큼 이전날짜 세팅
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayofMonth)

        while (dayList.size<42){
            dayList.add(monthCalendar.time)

            //1일식 늘린다
            monthCalendar.add(Calendar.DAY_OF_MONTH,1)
        }

        return dayList
    }

    private fun getLastDayOfMonth(calendar: Calendar): Int {
        val tempCalendar = calendar.clone() as Calendar
        tempCalendar.set(Calendar.DAY_OF_MONTH, tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return tempCalendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun getFirstDayOfMonth(calendar: Calendar): Calendar {
        val tempCalendar = calendar.clone() as Calendar
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1)
        return tempCalendar
    }

    fun color_before_time_blue () {
        binding.registSettimeTopBeforeMonth.setTextColor(Color.parseColor("#1277ED"))
        binding.registSettimeTopBeforeTime.setTextColor(Color.parseColor("#1277ED"))
    }
    fun color_after_time_blue () {
        binding.registSettimeTopAfterMonth.setTextColor(Color.parseColor("#1277ED"))
        binding.registSettimeTopAfterTime.setTextColor(Color.parseColor("#1277ED"))
    }

    fun color_before_time_gray () {
        binding.registSettimeTopBeforeMonth.setTextColor(ContextCompat.getColor(this,R.color.gray_900))
        binding.registSettimeTopBeforeTime.setTextColor(ContextCompat.getColor(this,R.color.gray_600))
    }
    fun color_after_time_gray () {
        binding.registSettimeTopAfterMonth.setTextColor(ContextCompat.getColor(this,R.color.gray_900))
        binding.registSettimeTopAfterTime.setTextColor(ContextCompat.getColor(this,R.color.gray_600))
    }

}