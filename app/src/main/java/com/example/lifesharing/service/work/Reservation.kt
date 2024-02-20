package com.example.lifesharing.service.work

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.home.home_data.ProductResultDTO
import com.example.lifesharing.reservation.ReservationResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class Reservation(application: Application):AndroidViewModel(application) {
    private val retrofitService = RetrofitAPIwithToken.retrofit()
    val TAG :String = "로그"

    val filteredProducts: MutableLiveData<List<ProductResultDTO>> = MutableLiveData()

    fun reservation(filter:String) {
        retrofitService.getReservations(filter).enqueue(object : retrofit2.Callback<ReservationResponseBody> {
            override fun onResponse(
                call: Call<ReservationResponseBody>,
                response: Response<ReservationResponseBody>
            ) {
                val result = response.body()
                if(response.isSuccessful) {
                    Log.d(TAG, "reserve API 연동 성공? $result")
                } else {
                    Log.e(TAG, "통신실패 원인은 ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<ReservationResponseBody>, t: Throwable) {
                Log.e(TAG, "reserve 서버에도 못감 원인은 ${t.message}", )
            }

        })
    }
}