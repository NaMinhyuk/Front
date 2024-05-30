package com.example.lifesharing.mypage.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.mypage.work.NicknameCheckWork
import com.example.lifesharing.mypage.work.NicknameStatus

/**
 * 닉네임 중복 체크 ViewModel
 * AndroidViewModel을 상속받아 애플리케이션 컨텍스트를 사용
 */
class NicknameCheckViewModel(application: Application) : AndroidViewModel(application) {
    private val nickNameCheckWork = NicknameCheckWork() // Model과 통신 -> 닉네임 중복 확인 요청
    val nicknameStatus: MutableLiveData<NicknameStatus> = MutableLiveData()   // 닉네임 중복 상태 관찰 LiveData

    fun checkNickname(nickname: String) {
        nickNameCheckWork.checkNickname(nickname) { status ->
            nicknameStatus.postValue(status)     // 입력받은 닉네임의 중복 여부를 LiveData에 게시
        }
    }
}