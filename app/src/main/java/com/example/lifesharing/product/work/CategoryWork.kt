package com.example.lifesharing.product.work

import android.util.Log
import com.example.lifesharing.product.api.CategoryItem
import com.example.lifesharing.product.api.CategoryResponse
import com.example.lifesharing.product.data.MyItemDetailData
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 카테고리별 제품 조회 요청 */
class CategoryWork {
    private val TAG: String = "로그"
    private val service = RetrofitAPIwithToken.retrofit()

    fun getCategoryProduct(categoryId : Long, callback: (List<CategoryItem>?, String?) -> Unit){
        service.getCategoryProduct(categoryId).enqueue(object : retrofit2.Callback<CategoryResponse>{
            // 요청 성공 시 호출되는 콜백 메서드
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                // 응답이 성공일 경우
                if (response.isSuccessful){
                    val result = response.body()    // 응답 바디를 result 변수에 저장
                    callback(result?.result?.productList, null)
                    //categoryProducts.postValue(result?.result?.productList)    // LiveData에 응답으로 얻은 제품 리스트 post
                    Log.d(TAG, " ${result?.result?.productList} ")        // 제대로 들어왔는지 로그로 출력 - 확인용
                }
                // 응답이 실패일 경우
                else {
                    Log.e(TAG, "상품 정보 가져오기 실패: ${response.message()}")
                    callback(null, "제품 정보 가져오기 실패: ${response.code()} ${response.message()}")
                }
            }

            // 요청 실패 시 호출되는 콜백 메서드
            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.e(TAG, "통신 실패: ${t.message}" )
            }

        })
    }
}