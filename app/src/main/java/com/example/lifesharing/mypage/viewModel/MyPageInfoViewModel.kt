package com.example.lifesharing.mypage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.mypage.model.response_body.InfoResult
import com.example.lifesharing.mypage.work.MyPageInfoWork

/** 마이페이지 진입 시 필요한 데이터 ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class MyPageInfoViewModel (application: Application): AndroidViewModel(application){
    private val myPageInfoWork = MyPageInfoWork()   // Model과 통신을 위한 객체
    val userInfo : MutableLiveData<InfoResult> = MutableLiveData()    // 요청으로 조회된 사용자 정보를 담을 LiveData(내부 데이터가 업데이트될 때마다 반응하여 사용자에게 정보를 표시)
    val error: MutableLiveData<String> = MutableLiveData()   // 네트워크 요청이 실패했을 때를 위한 LiveData

    fun getUserProfileInfo() {
        myPageInfoWork.getUserProfileInfo { result, errMsg ->  // 사용자 정보 요청 후 콜백으로 요청 결과와 에러 메시지를 받음
            result?.result?.let {  // result가 null이 아닌 경우
                userInfo.postValue(it)    // result 내의 사용자 정보를 LiveData 객체에 게시(설정)
            } ?: run {  // result가 null일 경우
                error.postValue(errMsg)   // error 메시지 설정
            }
        }
    }
}