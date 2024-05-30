package com.example.lifesharing.mypage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.mypage.model.response_body.UsageHistoryResult
import com.example.lifesharing.mypage.work.UsageHistoryWork

class UsageHistoryViewModel(application: Application): AndroidViewModel(application) {
    private val usageHistoryWork = UsageHistoryWork()

    val usageHistoryList = MutableLiveData<List<UsageHistoryResult>>()
    val isLoading = MutableLiveData<Boolean>()     // 예약 목록 로딩 중인지 저장 LiveData
    val errorMessage = MutableLiveData<String>()   // 에러 발생 시 해당 메시지를 저장 LiveData

    fun loadUsageHistory(){
        isLoading.postValue(true)
        usageHistoryWork.getUsageHistory { result, error ->
            isLoading.postValue(false)
            if (error != null){
                errorMessage.postValue(error)      //호출 완료 시 false로 설정
            }
            else {
                usageHistoryList.postValue(result)   // 성공적으로 요청이 되면 LiveData에 데이터 저장
            }
        }
    }
}