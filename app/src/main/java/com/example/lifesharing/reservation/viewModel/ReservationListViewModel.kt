package com.example.lifesharing.reservation.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.reservation.api.ReservationList
import com.example.lifesharing.reservation.api.ReservationResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class ReservationListViewModel(application: Application) : AndroidViewModel(application) {

    private val _reservationList = MutableLiveData<List<ReservationList>>()
    val reservationList: LiveData<List<ReservationList>> get() = _reservationList

    private val _filteredReservations = MutableLiveData<List<ReservationList>>()
    val filteredReservations: LiveData<List<ReservationList>> get() = _filteredReservations

    private val _dateWiseReservationCount = MutableLiveData<Map<String, Int>>()
    val dateWiseReservationCount: LiveData<Map<String, Int>> get() = _dateWiseReservationCount

    // 예약 목록을 불러오는 함수
    fun loadReservations() {
        // 더미 데이터 생성
        val reservations = listOf(
            ReservationList(
                reservationId = 1L,
                productId = 101L,
                productName = "Canon EOS 5D",
                productImage = "https://lifesharing.s3.ap-northeast-2.amazonaws.com/product/8e4f2c95-64ea-404c-a7c2-f0b7376d9e9e_img.png",
                filter = "MY",
                startDate = "2024-05-24",
                endDate = "2024-05-24",
                location = "울산 무거동",
                totalTime = "2 hours",
                amount = 10000L,
                deposit = 1000L,
                status = "대여"
            ),
            ReservationList(
                reservationId = 2L,
                productId = 102L,
                productName = "Nikon D850",
                productImage = "https://lifesharing.s3.ap-northeast-2.amazonaws.com/product/8e4f2c95-64ea-404c-a7c2-f0b7376d9e9e_img.png",
                filter = "대여",
                startDate = "2024-05-5",
                endDate = "2024-05-5",
                location = "울산 삼산동",
                totalTime = "3 hours",
                amount = 20000L,
                deposit = 2000L,
                status = "MY"
            ),
            ReservationList(
                reservationId = 3L,
                productId = 103L,
                productName = "Sony A7R IV",
                productImage = "https://lifesharing.s3.ap-northeast-2.amazonaws.com/product/8e4f2c95-64ea-404c-a7c2-f0b7376d9e9e_img.png",
                filter = "MY",
                startDate = "2024-05-04",
                endDate = "2024-05-05",
                location = "울산 다운동",
                totalTime = "24 hours",
                amount = 30000L,
                deposit = 3000L,
                status = "대여"
            )
        )

        _reservationList.value = reservations
        _dateWiseReservationCount.value = reservations.groupBy { it.startDate }.mapValues { it.value.size }
        _filteredReservations.value = reservations
    }

    fun getReservationList(filter: String) {
        val filteredList = when (filter) {
            "my" -> _reservationList.value?.filter { it.filter == "MY" }
            "rent" -> _reservationList.value?.filter { it.filter == "대여" }
            else -> _reservationList.value
        }
        _filteredReservations.value = filteredList
    }
}

//class ReservationListViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val service = RetrofitAPIwithToken.retrofit()
//
//    private val TAG: String = "ReservationViewModel"
//    val filteredReservations: MutableLiveData<List<ReservationList>> = MutableLiveData()
//
//    fun getReservationList(filter: String) {
//        service.getReservationList(filter).enqueue(object : retrofit2.Callback<ReservationResponseBody> {
//            override fun onResponse(call: Call<ReservationResponseBody>, response: Response<ReservationResponseBody>) {
//                val result = response.body()
//                if (response.isSuccessful) {
//                    Log.d(TAG, "예약 필터 정보 $result")
//
//                    Log.d(TAG, " ${result?.result?.reservationList} ")
//
//                    filteredReservations.postValue(result?.result?.reservationList)
//
//                } else {
//                    Log.e(TAG, "네트워크 에러: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ReservationResponseBody>, t: Throwable) {
//                Log.e(TAG, "예약 목록 조회 실패: ${t.message}")
//            }
//        })
//    }
//}