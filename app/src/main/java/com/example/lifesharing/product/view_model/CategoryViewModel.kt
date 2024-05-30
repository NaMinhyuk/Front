package com.example.lifesharing.product.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.product.api.CategoryItem
import com.example.lifesharing.product.api.CategoryResponse
import com.example.lifesharing.product.work.CategoryWork
import com.example.lifesharing.product.work.ProductDeleteWork
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/**
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class CategoryViewModel(application: Application): AndroidViewModel(application) {
    val categoryProducts : MutableLiveData<List<CategoryItem>> = MutableLiveData()   // 카테고리별 조회된 제품 리스트 관찰 객체

    val TAG : String = "로그"

    private val categoryWork = CategoryWork()

    fun loadCategoryProducts(categoryId: Long) {
        categoryWork.getCategoryProduct(categoryId) { productList, errorMessage ->
            if (productList != null) {
                // 성공적으로 데이터를 가져오면 LiveData를 업데이트
                categoryProducts.postValue(productList)
            } else if (errorMessage != null) {
                // 오류 발생 시 로그 출력, 필요에 따라 오류 처리 LiveData도 사용 가능
                Log.e(TAG, "Failed to load products: $errorMessage")
            }
        }
    }
//    fun getCategoryProduct(categoryId : Long){
//        service.getCategoryProduct(categoryId).enqueue(object : retrofit2.Callback<CategoryResponse>{
//            // 요청 성공 시 호출되는 콜백 메서드
//            override fun onResponse(
//                call: Call<CategoryResponse>,
//                response: Response<CategoryResponse>
//            ) {
//                // 응답이 성공일 경우
//                if (response.isSuccessful){
//                    val result = response.body()    // 응답 바디를 result 변수에 저장
//
//                    categoryProducts.postValue(result?.result?.productList)    // LiveData에 응답으로 얻은 제품 리스트 post
//                    Log.d(TAG, " ${result?.result?.productList} ")        // 제대로 들어왔는지 로그로 출력 - 확인용
//                }
//                // 응답이 실패일 경우
//                else {
//                    Log.e(TAG, "상품 정보 가져오기 실패: ${response.message()}")
//                }
//            }
//
//            // 요청 실패 시 호출되는 콜백 메서드
//            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
//                Log.e(TAG, "통신 실패: ${t.message}" )
//            }
//
//        })
//    }
}