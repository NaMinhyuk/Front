package com.example.lifesharing.reservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.MainActivity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentReservationBinding
import com.example.lifesharing.home.home_data.NewRegistItemAdapter
import com.example.lifesharing.home.home_data.ProductViewModel
import com.example.lifesharing.regist.CalendarAdapter
import com.example.lifesharing.regist.CalendarClick
import com.example.lifesharing.reservation.data.NewReervationItemAdapter
import com.example.lifesharing.reservation.data.NewReservationItemData
import com.example.lifesharing.search.SearchActivity
import com.example.lifesharing.service.work.DetailProduct
import com.example.lifesharing.service.work.Reservation
import java.util.Calendar
import java.util.Date


class ReservationFragment : Fragment() {

    lateinit var binding: FragmentReservationBinding


    private lateinit var viewModel : Reservation
    private lateinit var NewReervationItemAdapter : NewReervationItemAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val reservationRV: RecyclerView

        binding = FragmentReservationBinding.inflate(inflater, container, false)

        val reservationDataList = listOf(
            ReservationData("제발 제목 떠라", "무거동", "3.31", "4.12"),
            ReservationData("너가 떠야된다 ㅠㅠ", "나 죽어", "4.44", "4.44"),
        )

        binding.reservationGohome.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        binding.reservationSearchbtn.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this).get(Reservation::class.java)
        viewModel.filteredProducts.observe(viewLifecycleOwner, Observer { products->
            val newReservationItems = products.map { product ->
                NewReservationItemData(
                    img = product.imageUrl ?: R.drawable.camera.toString(), // 이미지 URL이 null이면 기본 카메라 사진
                    location = product.location,
                    reviewCount = product.reviewCount,
                    name = product.name,
                    deposit = product.deposit,
                    dayPrice = product.dayPrice
                )
            }
            NewReervationItemAdapter.setItems(ArrayList(newReservationItems)) // 변환된 데이터로 RecyclerView를 업데이트
        })
        //API 연동

        //필터를 클릭했을때 변경하는 로직 필요!!!!!!!
        val filter = "MY"

        val viewModel = Reservation(requireActivity().application)
        viewModel.reservation(filter)


        val adapter = ReservationMenuAdapter(reservationDataList)

        //화면설정
        setMonthView()

//        reservationRV = binding.reservationTitle2Rv
//        reservationRV.layoutManager = LinearLayoutManager(requireContext())

//        reservationRV.adapter = adapter

        binding.calendarPreBtn.setOnClickListener {
            CalendarClick.selectDate.add(Calendar.MONTH, -1)
            setMonthView()
        }
        binding.calendarNextBtn.setOnClickListener {
            CalendarClick.selectDate.add(Calendar.MONTH, +1)
            setMonthView()
        }

        return binding.root
    }

    //화면에 날짜 보여주기
    private fun setMonthView() {
        binding.calendarMonthyear.text = monthYearFromData(CalendarClick.selectDate)

        //날짜 생성해서 리스트에 담기
        val dayList = dayMonthArray()

        //어댑터 초기화
        val adapter = CalendarAdapter(dayList)

        //레이아웃 설정 (열 7개)
        var manager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)


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



}