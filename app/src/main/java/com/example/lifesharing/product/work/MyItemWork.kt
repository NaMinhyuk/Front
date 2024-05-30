package com.example.lifesharing.product.work

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.product.data.MyRegisterProductData
import com.example.lifesharing.product.data.myProductResultDTO
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** MY 아이템 Model */
class MyItemWork {

    private val service = RetrofitAPIwithToken.retrofit()

    private val TAG: String = "로그"
    //val myProducts: MutableLiveData<List<myProductResultDTO>> = MutableLiveData()


    fun getMyRegisterProduct(callback: (List<myProductResultDTO>, Boolean) -> Unit){
        service.getMyRegisterProduct().enqueue(object : retrofit2.Callback<MyRegisterProductData>{
            override fun onResponse(
                call: Call<MyRegisterProductData>,
                response: Response<MyRegisterProductData>
            ) {
                val result = response.body()  // 데이터를 뿌려 주는 로직

                Log.d(TAG, " ${result?.myProductResultDTO} ")
                callback(result!!.myProductResultDTO, true)

                //myProducts.postValue(result?.myProductResultDTO)
            }

            override fun onFailure(call: Call<MyRegisterProductData>, t: Throwable) {
                Log.e(TAG, "상품 정보 가져오기 실패: ${t.message}", )
            }

        })

    }
}