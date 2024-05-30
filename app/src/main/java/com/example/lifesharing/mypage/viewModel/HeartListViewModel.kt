package com.example.lifesharing.mypage.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.mypage.model.response_body.HeartList
import com.example.lifesharing.mypage.work.HeartListWork

/** 찜 목록 조회 ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class HeartListViewModel (application: Application) : AndroidViewModel(application){
    private val heartListWork = HeartListWork()
    val heartListItem: MutableLiveData<List<HeartList>> = MutableLiveData()   // 요청 결과 리스트를 담을 LiveData

    val TAG: String = "로그"

    fun getHeartList() {
        heartListWork.getHeartList { heartList, error ->
            if (heartList != null) {
                heartListItem.postValue(heartList)
            } else {
                Log.e(TAG, "Error fetching heart list", error)
            }
        }
    }
}