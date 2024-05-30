package com.example.lifesharing.product.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.product.data.MyItemDetailData
import com.example.lifesharing.product.work.MyItemDetailWork

/** MY 아이템 상세 조회 ViewModel */
class MyItemDetailViewModel(application: Application, private val productId: Long) : AndroidViewModel(application){


    private val detailWork = MyItemDetailWork()

    val productDetail = MutableLiveData<MyItemDetailData>()    // 제품 상세 정보 저장 LiveData
    val isLoading = MutableLiveData<Boolean>()     // 제품 상세 정보 로딩 중인지 저장 LiveData
    val errorMessage = MutableLiveData<String>()   // 에러 발생 시 해당 메시지를 저장 LiveData

    fun getProductDetail(){

        isLoading.postValue(true)
        detailWork.getProductDetails(productId) { result, error ->   //제품 상세 정보를 요청
            isLoading.postValue(false)   //호출 완료 시 false로 설정
            if (error != null) {
                errorMessage.postValue(error)
            } else {
                productDetail.postValue(result)   // 성공적으로 요청이 되면 LiveData에 데이터 저장
            }
        }

    }

}