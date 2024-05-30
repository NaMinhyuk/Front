package com.example.lifesharing.product

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.product.Product_Detail_Activity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityProductDetailReserveBinding
import com.example.lifesharing.product.data.DetailMonthAdapter
import java.text.SimpleDateFormat
import java.util.*
class Product_Detail_Reserve_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailReserveBinding
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var currentMonthYearTextView: TextView
    private lateinit var prevMonthButton: ImageView
    private lateinit var nextMonthButton: ImageView
    private var currentCalendar: Calendar = Calendar.getInstance()
    private lateinit var calendarAdapter: DetailMonthAdapter

    private var selectedStartDate: Date? = null
    private var selectedEndDate: Date? = null
    private var selectedStartTime: String? = null
    private var selectedEndTime: String? = null

    private var selectedMorningButton: Pair<ImageView, TextView>? = null
    private var selectedAfternoonButton: Pair<ImageView, TextView>? = null
    private var selectedTimeButton: Pair<ImageView, TextView>? = null

    // 예시 예약금 데이터 (더미 데이터)
    private val reservationPrices = mapOf(
        "2024-05-24" to 68000,
        "2024-05-25" to 70000,
        "2024-05-26" to 72000,
        "2024-05-27" to 75000,
        "2024-05-28" to 78000,
        "2024-05-29" to 80000,
        "2024-05-30" to 82000,
        "2024-05-31" to 85000,
        "2024-06-01" to 10000,
        "2024-06-02" to 30000,
        "2024-06-03" to 5000,
        "2024-06-04" to 10000,
        "2024-06-05" to 15000,
        "2024-06-06" to 23000,
        "2024-06-07" to 11000,
        "2024-06-08" to 10000,
        "2024-06-09" to 10000,
        "2024-06-10" to 100000,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailReserveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews() // 뷰 초기화
        setupCalendar() // 달력 설정
        setupTimeButtons() // 시간 버튼 설정

        // 뒤로 가기 버튼
        binding.reserveBackBtn.setOnClickListener {
            goToProductDetailActivity()
        }

        // 대여 요청 버튼
        binding.reserveBottomTextbox.setOnClickListener {
            if (selectedStartDate != null && selectedEndDate != null && selectedStartTime != null && selectedEndTime != null) {
                onReserveRequest()
            } else {
                Toast.makeText(this, "날짜와 시간을 모두 선택해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 뒤로 가기 버튼 클릭 시 ProductDetailActivity로 이동
    private fun goToProductDetailActivity() {
        val intent = Intent(this, Product_Detail_Activity::class.java)
        startActivity(intent)
        finish()
    }

    // 뷰 초기화
    private fun initViews() {
        calendarRecyclerView = findViewById(R.id.detail_cal_rl)
        currentMonthYearTextView = findViewById(R.id.detail_current_month)
        prevMonthButton = findViewById(R.id.detail_cal_prev_btn)
        nextMonthButton = findViewById(R.id.detail_cal_next_btn)

        prevMonthButton.setOnClickListener {
            currentCalendar.add(Calendar.MONTH, -1)
            updateCalendar()
        }

        nextMonthButton.setOnClickListener {
            currentCalendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }
    }

    // 달력 설정
    private fun setupCalendar() {
        calendarAdapter = DetailMonthAdapter(this) { date, isSelected ->
            if (selectedStartDate == null || (selectedStartDate != null && selectedEndDate != null)) {
                selectedStartDate = date
                selectedEndDate = null
                selectedStartTime = null
                selectedEndTime = null
                updateDateTimeDisplay()
                enableTimeButtons()
            } else {
                selectedEndDate = date
                updateDateTimeDisplay()
                enableTimeButtons()
            }
        }
        calendarRecyclerView.layoutManager = GridLayoutManager(this, 7) // 7열 그리드 레이아웃으로 변경
        calendarRecyclerView.adapter = calendarAdapter
        updateCalendar()
    }

    // 달력 데이터 갱신 및 UI 업데이트
    private fun updateCalendar() {
        calendarAdapter.updateData(getCalendarData(currentCalendar), reservationPrices)
        updateMonthYearTextView()
    }

    // 현재 연도와 월 텍스트뷰 업데이트
    private fun updateMonthYearTextView() {
        val year = currentCalendar.get(Calendar.YEAR)
        val month =
            currentCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        val yearMonthText = year.toString() + "년 " + month
        currentMonthYearTextView.text = yearMonthText
    }


    // 현재 달의 날짜 데이터 생성
    private fun getCalendarData(calendar: Calendar): List<Date> {
        val dates = mutableListOf<Date>()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
        calendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth)

        while (dates.size < 42) {
            dates.add(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return dates
    }

    // 시간 버튼 설정
    private fun setupTimeButtons() {
        val morningButton = binding.reserveMorningBtn
        val morningText = binding.reserveMorningTv
        val afternoonButton = binding.reserveAfternoonBtn
        val afternoonText = binding.reserveAfternoonTv
        val timeButtons = listOf(
            Pair(binding.reserveHour1Btn, binding.reserveHour1Tv),
            Pair(binding.reserveHour2Btn, binding.reserveHour2Tv),
            Pair(binding.reserveHour3Btn, binding.reserveHour3Tv),
            Pair(binding.reserveHour4Btn, binding.reserveHour4Tv),
            Pair(binding.reserveHour5Btn, binding.reserveHour5Tv),
            Pair(binding.reserveHour6Btn, binding.reserveHour6Tv),
            Pair(binding.reserveHour7Btn, binding.reserveHour7Tv),
            Pair(binding.reserveHour8Btn, binding.reserveHour8Tv),
            Pair(binding.reserveHour9Btn, binding.reserveHour9Tv),
            Pair(binding.reserveHour10Btn, binding.reserveHour10Tv),
            Pair(binding.reserveHour11Btn, binding.reserveHour11Tv),
            Pair(binding.reserveHour12Btn, binding.reserveHour12Tv)
        )

        // 시간 버튼 활성화/비활성화 설정 함수
        val enableTimeButtons: (Boolean) -> Unit = { enable ->
            morningButton.isEnabled = enable
            afternoonButton.isEnabled = enable
            timeButtons.forEach { (button, _) -> button.isEnabled = false }
        }

        // 오전 버튼 클릭 리스너 설정
        morningButton.setOnClickListener {
            // 선택되지 않은 오후 버튼 초기화
            if (selectedAfternoonButton != null) {
                selectedAfternoonButton?.first?.setImageResource(R.drawable.reserve_time_btn)
                selectedAfternoonButton?.second?.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
                selectedAfternoonButton = null
            }

            // 오전 버튼 선택 상태 설정
            timeButtons.forEach { (button, _) -> button.isEnabled = true }
            afternoonButton.isEnabled = false
            morningButton.setImageResource(R.drawable.reserve_selected_btn)
            morningText.setTextColor(ContextCompat.getColor(this, R.color.blue_300))
            selectedMorningButton = Pair(morningButton, morningText)
        }

        // 오후 버튼 클릭 리스너 설정
        afternoonButton.setOnClickListener {
            // 선택되지 않은 오전 버튼 초기화
            if (selectedMorningButton != null) {
                selectedMorningButton?.first?.setImageResource(R.drawable.reserve_time_btn)
                selectedMorningButton?.second?.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
                selectedMorningButton = null
            }

            // 오후 버튼 선택 상태 설정
            timeButtons.forEach { (button, _) -> button.isEnabled = true }
            morningButton.isEnabled = false
            afternoonButton.setImageResource(R.drawable.reserve_selected_btn)
            afternoonText.setTextColor(ContextCompat.getColor(this, R.color.blue_300))
            selectedAfternoonButton = Pair(afternoonButton, afternoonText)
        }

        // 시간 버튼 클릭 리스너 설정
        timeButtons.forEach { (button, textView) ->
            button.setOnClickListener {
                val time = textView.text.toString()
                if (selectedStartDate != null && selectedEndDate == null) {
                    selectedStartTime = time
                } else {
                    selectedEndTime = time
                }
                updateDateTimeDisplay()
                checkIfComplete()

                // 이전 선택된 시간 버튼 초기화
                if (selectedTimeButton != null) {
                    selectedTimeButton?.first?.setImageResource(R.drawable.reserve_time_btn)
                    selectedTimeButton?.second?.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
                }

                // 선택된 시간 버튼 상태 설정
                button.setImageResource(R.drawable.reserve_selected_btn)
                textView.setTextColor(ContextCompat.getColor(this, R.color.blue_300))
                selectedTimeButton = Pair(button, textView)
            }
        }
    }

    // 시간 버튼 활성화 설정
    private fun enableTimeButtons() {
        binding.reserveMorningBtn.isEnabled = true
        binding.reserveAfternoonBtn.isEnabled = true
        binding.reserveHour1Btn.isEnabled = false
        binding.reserveHour2Btn.isEnabled = false
        binding.reserveHour3Btn.isEnabled = false
        binding.reserveHour4Btn.isEnabled = false
        binding.reserveHour5Btn.isEnabled = false
        binding.reserveHour6Btn.isEnabled = false
        binding.reserveHour7Btn.isEnabled = false
        binding.reserveHour8Btn.isEnabled = false
        binding.reserveHour9Btn.isEnabled = false
        binding.reserveHour10Btn.isEnabled = false
        binding.reserveHour11Btn.isEnabled = false
        binding.reserveHour12Btn.isEnabled = false
    }

    // 선택된 날짜 및 시간 표시 업데이트
    private fun updateDateTimeDisplay() {
        val selectedDates = calendarAdapter.getSelectedDates().toMutableList()
        if (selectedDates.isEmpty()) {
            binding.detailSelectedBeforeMonth.text = ""
            binding.detailSelectedBeforeTime.text = ""
            binding.detailSelectedAfterMonth.text = ""
            binding.detailSelectedAfterTime.text = ""
            return
        }

        val dateFormat = SimpleDateFormat("MM.dd (E)", Locale.getDefault())
        val earliestDate = selectedDates.minOrNull()
        val latestDate = selectedDates.maxOrNull()

        if (earliestDate != null && latestDate != null) {
            binding.detailSelectedBeforeMonth.text = dateFormat.format(earliestDate)
            binding.detailSelectedBeforeTime.text = selectedStartTime ?: ""
            binding.detailSelectedAfterMonth.text = dateFormat.format(latestDate)
            binding.detailSelectedAfterTime.text = selectedEndTime ?: ""
        }
    }

    // 예약 요청 처리 함수 (데이터 전송 이용 시 수정)
    private fun onReserveRequest() {
        if (selectedStartDate != null && selectedEndDate != null && selectedStartTime != null && selectedEndTime != null) {
            if (selectedStartDate == selectedEndDate && selectedStartTime!!.toInt() >= selectedEndTime!!.toInt()) {
                Toast.makeText(this, "시작 시간은 종료 시간보다 이른 시간이어야 합니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 예약 처리를 진행하는 로직 추가
                Toast.makeText(this, "예약 요청이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                // Product_Reserve_Finish_Activity로 이동
                val intent = Intent(this, Product_Detail_Reserve_Finish_Activity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            Toast.makeText(this, "날짜와 시간을 모두 선택해 주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    // 완료 상태 확인 함수
    private fun checkIfComplete() {
        if (selectedStartDate != null && selectedEndDate != null && selectedStartTime != null && selectedEndTime != null) {
            binding.reserveBottomTextbox.isEnabled = true
        } else {
            binding.reserveBottomTextbox.isEnabled = false
        }
    }
}


//    // 대여 요청 처리 (데이터용)
//    private fun onReserveRequest() {
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        val startDateString = dateFormat.format(selectedStartDate)
//        val endDateString = dateFormat.format(selectedEndDate)
//        val selectedDates = calendarAdapter.getSelectedDates()
//        val intermediateDates = selectedDates.filter { it.after(selectedStartDate) && it.before(selectedEndDate) }
//
//        // 대여 요청 데이터 처리 로직
//
//        Toast.makeText(this, "대여 요청: 시작 날짜 = $startDateString, 시작 시간 = $selectedStartTime, 종료 날짜 = $endDateString, 종료 시간 = $selectedEndTime", Toast.LENGTH_LONG).show()
//        Toast.makeText(this, "중간 날짜: $intermediateDates", Toast.LENGTH_LONG).show()
//    }