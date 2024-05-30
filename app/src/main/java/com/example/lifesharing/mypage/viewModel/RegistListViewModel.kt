package com.example.lifesharing.mypage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.mypage.model.response_body.MyRegProductList
import com.example.lifesharing.mypage.work.RegistListWork

/** 등록내역 조회 ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class RegistListViewModel(application : Application) : AndroidViewModel(application) {
    private val registListWork = RegistListWork()   // Model 과 통신

    val registerListItem : MutableLiveData<List<MyRegProductList>> = MutableLiveData()   // 제품 조회 결과를 관찰하는 LiveData
    val productCountLiveData: MutableLiveData<Int> = MutableLiveData()  // 제품 개수를 위한 LiveData

    fun getRegisterList() {
        registListWork.getRegisterList { products, count ->
            registerListItem.postValue(products ?: emptyList())   // 응답 결과 제품 리스트를 LiveData에 설정
            productCountLiveData.postValue(count ?: 0)            // 제품 개수 또한 LiveData로 설정
        }
    }
}