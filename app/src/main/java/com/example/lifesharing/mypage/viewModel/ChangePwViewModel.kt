package com.example.lifesharing.mypage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.mypage.model.response_body.ChangePwResponse
import com.example.lifesharing.mypage.mypage_data.PwData
import com.example.lifesharing.mypage.work.PwChangeWork

/** 비밀번호 변경 ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class ChangePwViewModel(application: Application):AndroidViewModel(application) {

    private val pwChangeWork = PwChangeWork()   // Model 과 통신을 위한 객체
    val changePwData : MutableLiveData<ChangePwResponse> = MutableLiveData()   // 비밀번호 변경 결과를 담을 LiveData

    fun changePassword(pwData: PwData) {
        pwChangeWork.changePw(pwData) { response ->
            changePwData.postValue(response)
        }
    }
}