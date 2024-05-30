package com.example.lifesharing.mypage.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.login.model.response_body.NickNameChangeResponse
import com.example.lifesharing.mypage.work.NicknameChangeWork
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 닉네임 변경 요청  ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class NicknameChangeViewModel(application: Application): AndroidViewModel(application) {

    private val nickNameChangeWork = NicknameChangeWork()   // Model과 통신 - 닉네임 변경 요청용 객체
    val nicknameChangeSuccess = MutableLiveData<Boolean>()   // 닉네임 변경 결과 관찰

    fun changeNickname(nickname: String) {
        nickNameChangeWork.changeNickname(nickname) { isSuccess ->
            nicknameChangeSuccess.postValue(isSuccess)    // 닉네임 변경 성공 여부를 LiveData에 게시(설정)
        }
    }
}