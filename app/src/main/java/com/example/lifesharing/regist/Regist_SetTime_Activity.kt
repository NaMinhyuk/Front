package com.example.lifesharing.regist

import android.app.Activity
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
import java.util.concurrent.TimeUnit

/** 대여날짜 지정 액티비티 */
class Regist_SetTime_Activity :AppCompatActivity() {

    val TAG = "대여기간 선택 로그"
    private var selectingStartDate = true  // 시작 날짜 선택 중이면 true, 아니면 false

    // 시작 및 종료 날짜를 저장하는 변수를 추가
    private var startDate: Date? = null
    private var endDate: Date? = null
    private var selectedTime: Int? = null   // 선택된 시간

    val morning_afternoon_Array = arrayOf(0, 0)   // 오전, 오후 선택 상태 관리용

    private lateinit var binding: ActivityRegistSettimeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistSettimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 시작 날짜 초기화 없애기 (처음에는 시작 날짜를 설정하지 않음)
        startDate = null
        endDate = null

        // 시작 날짜를 오늘로 구하고, 텍스트 뷰에 설정
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        updateDateText(today)  // 상단의 시작 날짜 텍스트 뷰를 오늘 날짜로 업데이트


        // 캘린더에 월별로 뷰 설정
        setMonthView()


        // 이전 달 보기 버튼 클릭 리스너
        binding.calendarPreBtn.setOnClickListener {
            CalendarClick.selectDate.add(Calendar.MONTH, -1)
            setMonthView()   // 해당 월 리스트 업데이트
        }
        // 다음 달 보기 버튼 클릭 리스너
        binding.calendarNextBtn.setOnClickListener {
            CalendarClick.selectDate.add(Calendar.MONTH, +1)
            setMonthView()   // 해당 월 리스트 업데이트
        }


        val topview_array = arrayOf(0, 0)   // 상단 뷰(시작날짜, 종료날짜) 지정 산태 관찰용

        // 상단의 날짜 둘중 하나만 클릭해도 색이 변경되어야 함.
        binding.registSettimeTopBeforeMonth.setOnClickListener {
            selectingStartDate = true // 사용자가 시작 날짜를 선택하고 있음으로 설정
            topview_array[0] = 1      // 시작 날짜가 선택되면 배열 값 1로 설정
            color_before_time_blue()  // 선택되면 상단 텍스트뷰 색상 변경

            // 종료 날짜가 선택된 경우
            if (topview_array[1] == 1) {
                topview_array[1] = 0     // 시작 날짜 배열 값 0으로 설정
                color_after_time_gray()
            }
        }
        binding.registSettimeTopBeforeTime.setOnClickListener {
            selectingStartDate = false // 종료 날짜 선택으로 설정
            topview_array[0] = 1
            color_before_time_blue()
            if (topview_array[1] == 1) {
                topview_array[1] = 0
                color_after_time_gray()
            }
        }
        binding.registSettimeTopAfterMonth.setOnClickListener {
            topview_array[1] = 1
            color_after_time_blue()
            if (topview_array[0] == 1) {
                topview_array[0] = 0
                color_before_time_gray()
            }
        }
        binding.registSettimeTopAfterTime.setOnClickListener {
            topview_array[1] = 1
            color_after_time_blue()
            if (topview_array[0] == 1) {
                topview_array[0] = 0
                color_before_time_gray()
            }
        }

        // 오전 버튼 클릭 리스너
        binding.registSettimeMorningBtn.setOnClickListener {
            // 배열 인덱스(오전 = 0)의 값을 1로 설정하여 '오전'이 선택되었음을 표시
            morning_afternoon_Array[0] = 1
            // 그전에 '오후'가 선택되었는지 확인
            if (morning_afternoon_Array[1] == 1) {   // '오후'가 선택된 상태라면
                // 배열 인덱스(오후 = 1)의 값을 0으로 설정하여 '오후' 선택이 해제됨을 표시
                morning_afternoon_Array[1] = 0

                // 뷰에 '오후' 버튼이 선택 해제됨을 표시
                binding.registSettimeAfternoonBtn.setImageResource(R.drawable.reserve_time_btn)
                binding.registSettimeAfternoonTv.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
            }

            // '오전' 버튼 선택됨을 뷰에 표시
            binding.registSettimeMorningBtn.setImageResource(R.drawable.regist_add_clickbtn)
            binding.registSettimeMorningTv.setTextColor(Color.parseColor("#1277ED"))

            // 현재 선택된 시간을 변수에 저장
            val text = binding.registSettimeTopBeforeTime.text.toString()
            Log.d(TAG, "${text}")   // 선택된 시간 확인용 로그
            text.replace("오후", "오전")   // 현재 선택된 시간의 '오후'를 '오전'으로 변경
            binding.registSettimeTopBeforeTime.text = text  // 상단 시작 날짜 대여시간 부분에 선택한 시간 설정
        }

        // 오후 버튼 클릭 리스너
        binding.registSettimeAfternoonBtn.setOnClickListener {
            morning_afternoon_Array[1] = 1
            if (morning_afternoon_Array[0] == 1) {
                morning_afternoon_Array[0] = 0
                binding.registSettimeMorningBtn.setImageResource(R.drawable.reserve_time_btn)
                binding.registSettimeMorningTv.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
            }
            binding.registSettimeAfternoonBtn.setImageResource(R.drawable.regist_add_clickbtn)
            binding.registSettimeAfternoonTv.setTextColor(Color.parseColor("#1277ED"))
            val text = binding.registSettimeTopBeforeTime.text.toString()
            Log.d(TAG, "${text}")
            text.replace("오전", "오후")
            binding.registSettimeTopBeforeTime.text = text
        }

        // 오전/오후 시간 선택 상태 관리용 배열
        val time_Array = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

        // 각 시간 버튼을 배열에 저장
        val time_btn_array = arrayOf(
            binding.registSettimeHour1Btn,
            binding.registSettimeHour2Btn,
            binding.registSettimeHour3Btn,
            binding.registSettimeHour4Btn,
            binding.registSettimeHour5Btn,
            binding.registSettimeHour6Btn,
            binding.registSettimeHour7Btn,
            binding.registSettimeHour8Btn,
            binding.registSettimeHour9Btn,
            binding.registSettimeHour10Btn,
            binding.registSettimeHour11Btn,
            binding.registSettimeHour12Btn
        )

        // 각 시간에 해당하는 텍스트 뷰를 배열에 저장 - 사용자가 시간을 선택할 때 해당 시간의 텍스트를 업데이트 하는데 사용
        val time_text_array = arrayOf(
            binding.registSettime1Tv,
            binding.registSettimeHour2Tv,
            binding.registSettimeHour3Tv,
            binding.registSettimeHour4Tv,
            binding.registSettimeHour5Tv,
            binding.registSettimeHour6Tv,
            binding.registSettimeHour7Tv,
            binding.registSettimeHour8Tv,
            binding.registSettimeHour9Tv,
            binding.registSettimeHour10Tv,
            binding.registSettimeHour11Tv,
            binding.registSettimeHour12Tv
        )

        // 1시부터 12시까지 각 버튼에 대해 반복문 수행
        for (i in 0..11) {
            // 시간 버튼 클릭 시
            time_btn_array[i].setOnClickListener {
                selectedTime = i + 1   // 선택된 시간 저장
                updateRentButtonText() // 버튼 텍스트 업데이트
                updateTopTimeText(selectedTime)   // 상단 텍스트 뷰 업데이트

                // 선택된 시간을 time_Array에 저장, 다른 시간의 선택 상태를 0으로 초기화
                time_Array[i] = 1
                for (j in 0..11) {
                    if (i == j) continue
                    else time_Array[j] = 0
                }

                // 모든 시간 버튼에 반복문 수행
                for (j in 0..11) {
                    // 선택된 시간의 버튼은 활성화 이미지와 색상으로 변경
                    if (time_Array[j] == 1) {
                        time_btn_array[j].setImageResource(R.drawable.regist_add_clickbtn)
                        time_text_array[j].setTextColor(Color.parseColor("#1277ED"))
                    }
                    // 선택되지 않은 시간의 버튼은 기본 이미지와 색상으로 변경
                    else {
                        time_btn_array[j].setImageResource(R.drawable.reserve_time_btn)
                        time_text_array[j].setTextColor(ContextCompat.getColor(this, R.color.gray_800))
                    }
                }
            }
        }

        // 뒤로가기 버튼
        binding.registSettimeBackBtn.setOnClickListener {
            finish()
        }

        // 대여하기 버튼
        binding.btnDone.setOnClickListener {

            // 날짜 형식을 M.dd (E)으로 변환
            val startDateStr = SimpleDateFormat("M.dd(E)", Locale.getDefault()).format(startDate)
            val endDateStr = if (endDate != null) SimpleDateFormat("M.dd(E)", Locale.getDefault()).format(endDate)
                            else startDateStr

            // 시작 날짜와 종료 날짜 사이의 기간 계산
            val period = if (endDate != null) {
                // 두 날짜 사이의 밀리초 차이를 일 수로 변환하여 계산
                val diff = endDate!!.time - startDate!!.time
                // +1을 해야 정확한 일수 계산(시작일과 종료일을 포함한 전체 일수를 계산하기 위함)
                val days = TimeUnit.MILLISECONDS.toDays(diff).toInt() + 1
                "${days}일"
            } else {
                "1일" // 종료 날짜가 설정 되지 않은 경우 1일로 처리
            }

            // 결과 인텐트 생성
            val resultIntent = Intent()
            val formattedPeriod = "$startDateStr - $endDateStr ($period)"   // 서버로 넘겨주기 위한 포맷으로 지정
            resultIntent.putExtra("selectedPeriod", formattedPeriod)

            // 액티비티의 결과로 생성된 인텐트를 설정
            setResult(Activity.RESULT_OK, resultIntent)  // RESULT_OK로 이전 액티비티에 성고적으로 결과를 반했음을 알림
            finish() // 액티비티 종료
        }

    }

    /**
     * 선택된 시간을 상단 뷰에 업데이트 하는 메서드
     */
    private fun updateTopTimeText(selectedTime: Int?) {
        // let을 사용하여 selectedTime이 null이 아닐 경우에만 코드 블록을 실행
        selectedTime?.let {
            // 시간이 오전/오후에 따라 달라질 수 있으므로 해당 로직을 포함
            val timeSuffix = if (morning_afternoon_Array[0] == 1) "오전"
                            else "오후"  // 첫 번째 요소를 검사하여 "오전(0)" 또는 "오후(1)"를 할당
            // 선택된 시간과 timeSuffix를 이용해 문자열 포맷으로 설정
            val formattedTime = String.format("%s %02d:00 까지", timeSuffix, it)

            binding.registSettimeTopAfterTime.text = formattedTime   // 상단 뷰 텍스트 업데이트

        }
    }

    /**
     * 화면에 달력을 보여주는 메서드
     */
    private fun setMonthView() {
        // CalendarClick.selectDate에서 월과 연도를 추출
        binding.calendarMonthyear.text = monthYearFromData(CalendarClick.selectDate)

        // 날짜 생성해서 리스트에 담기 - 해당 월의 모든 날짜를 배열로 가져옴
        val dayList = dayMonthArray()

        // 캘린더 리사이클러 뷰 어댑터 초기화
        val adapter = CalendarAdapter(dayList, object : DateSelectListener {
            // 캘린더의 날짜를 선택 했을 시 호출될 콜백을 정의
            override fun onDateSelected(date: Date) {
                val formattedDate = SimpleDateFormat("M.d (E)", Locale.getDefault()).format(date)

                if (selectingStartDate) {    // 시작 날짜를 선택 중인 경우
                    startDate = date   // 선택된 날짜를 시작 날짜로 지정
                    endDate = null     // 새 시작 날짜가 선택되면 종료 날짜를 리셋
                    binding.registSettimeTopBeforeMonth.text = formattedDate
                    // 다음 선택은 종료 날짜를 의미
                    selectingStartDate = false  // 선택중 표시를 false로 설정 -> 시작 날짜 선택중이 아님을 의미
                }
                // 시작 날짜를 선택 중이 아닌 경우(= 종료 날짜를 선택하는 경우)
                else {
                    // 선택된 종료 날짜가 시작 날짜 이전인 경우 경고를 표시하고, 다시 시작 날짜 선택으로 돌아감
                    if (date.before(startDate)) {
                        Toast.makeText(this@Regist_SetTime_Activity, "종료 날짜는 시작 날짜 이후여야 합니다.", Toast.LENGTH_LONG).show()
                        // 선택 로직을 변경하지 않고 다시 시작 날짜 선택을 유도
                        selectingStartDate = true   // 사용자가 다시 시작 날짜를 선택하도록 !!
                    }
                    else {
                        endDate = date   // 종료 날짜를 설정
                        binding.registSettimeTopAfterMonth.text = formattedDate
                        selectingStartDate = true   // 종료 날짜 선택 후 다시 시작 날짜 선택으로 변경
                    }
                }
                // 종료 날짜 선택 후에 버튼 텍스트 업데이트
                updateRentButtonText()
            }
        }, startDate, endDate)

        // 어댑터에 선택된 날짜를 업데이트
        adapter.updateSelectedDates(startDate, endDate)

        // 레이아웃 설정 (열 7개)
        binding.calendarRv.layoutManager = GridLayoutManager(applicationContext, 7)
        //어뎁터 적용
        binding.calendarRv.adapter = adapter
        adapter.notifyDataSetChanged()  // 갱신 강제 실행
    }


    /**
     * 상단 날짜 텍스트 뷰 업데이트 메서드
     */
    private fun updateDateText(selectedDate: Date) {
        // 표시하고자 하는 날짜 포맷 지정 및 변환
        val formattedDate = SimpleDateFormat("yyyy.MM.dd EEE", Locale.getDefault()).format(selectedDate)
        if (selectingStartDate) {
            binding.registSettimeTopBeforeMonth.text = formattedDate  // 시작 날짜 텍스트 업데이트
        } else {
            binding.registSettimeTopAfterMonth.text = formattedDate  // 종료 날짜 텍스트 업데이트
        }
    }

    /**
     * 대여 기간과 시간을 계산하고 버튼 텍스트를 업데이트하는 함수
     */
    private fun updateRentButtonText() {
        if (startDate != null && selectedTime != null) {
            // 종료 날짜가 없으면 하루 대여로 간주
            val period = if (endDate == null) {
                "1일"
            } else {
                // 기간 계산 (시작 날짜와 종료 날짜 사이의 일수)
                val diff = endDate!!.time - startDate!!.time
                val days = TimeUnit.MILLISECONDS.toDays(diff).toInt() + 1 // +1을 해야 정확한 일수 계산
                "${days}일"
            }

            val timeText = "${selectedTime}시간"
            val rentText = "$period $timeText 대여"

            binding.btnDone.text = rentText
            binding.btnDone.setTextColor(Color.parseColor("#ffffff"))
            binding.btnDone.setBackgroundResource(R.drawable.btn_done_roundcolor)
        }
    }


    /**
     * 날짜 타입 (월,년))
     */
    private fun monthYearFromData(calendar: Calendar): String {
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1
        return "${year}년  ${month}월"
    }


    /**
     * 월별 날짜 생성 메서드
     */
    private fun dayMonthArray(): ArrayList<Date> {
        val dayList: ArrayList<Date> = ArrayList()   // 월별 날짜를 저장할 준비 !!

        var monthCalendar = CalendarClick.selectDate.clone() as Calendar
        // 일(day) 필드를 1일로 설정. 즉, 해당 달의 첫 번째 날로 설정
        monthCalendar[Calendar.DAY_OF_MONTH] = 1

        // 해당 달의 1일이 무슨 요일인지 계산 [1:일요일... 7:토요일]
        val firstDayofMonth = monthCalendar[Calendar.DAY_OF_WEEK] - 1  // 0(일요일)부터 6(토요일)로 맞추기 위해 1을 뺌.

        // 요일 숫자 만큼 이전 날짜 추가 *6월 1일이 수요일이면 3만큼 이전 날짜 세팅
        // 예를 들어, 해당 달의 1일이 수요일(3)이라면, monthCalendar를 3일 전으로 설정하여 이전 달의 마지막 며칠을 포함시킴.
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayofMonth)

        // 6주(6 x 7)의 날짜를 의미, 대부분의 달력 뷰가 6주로 구성
        while (dayList.size < 42) {
            dayList.add(monthCalendar.time)

            //1일식 늘린다
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return dayList
    }


    fun color_before_time_blue() {
        binding.registSettimeTopBeforeMonth.setTextColor(Color.parseColor("#1277ED"))
        binding.registSettimeTopBeforeTime.setTextColor(Color.parseColor("#1277ED"))
    }

    fun color_after_time_blue() {
        binding.registSettimeTopAfterMonth.setTextColor(Color.parseColor("#1277ED"))
        binding.registSettimeTopAfterTime.setTextColor(Color.parseColor("#1277ED"))
    }

    fun color_before_time_gray() {
        binding.registSettimeTopBeforeMonth.setTextColor(ContextCompat.getColor(this, R.color.gray_900))
        binding.registSettimeTopBeforeTime.setTextColor(ContextCompat.getColor(this, R.color.gray_600))
    }

    fun color_after_time_gray() {
        binding.registSettimeTopAfterMonth.setTextColor(ContextCompat.getColor(this, R.color.gray_900))
        binding.registSettimeTopAfterTime.setTextColor(ContextCompat.getColor(this, R.color.gray_600))
    }
}

