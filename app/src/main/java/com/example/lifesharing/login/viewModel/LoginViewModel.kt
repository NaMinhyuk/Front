package com.example.lifesharing.login.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.login.model.request_body.LoginRequestBody
import com.example.lifesharing.login.model.response_body.LoginResponseBody
import com.example.lifesharing.service.work.MessengerRoomListWork
import com.example.lifesharing.service.work.LoginWork

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // 이메일과 비밀번호를 관리하는 MutableLiveData 선언
    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")

    var showMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    // 로그인 결과를 저장하는 MutableLiveData 선언
    val loginResult : MutableLiveData<LoginResponseBody> = MutableLiveData()

    val TAG = "로그"

    fun loginLogic() {

        // LoginRequestBody 객체를 생성
        val loginUserData = LoginRequestBody(
            email.value.toString(),
            password.value.toString()
        )

        val loginWork = LoginWork(loginUserData)    // 로그인 로직 수행
        loginWork.loginWorkWithCoroutine { result ->
            loginResult.postValue(result)
        }

        // GlobalApplication에서 액세스 토큰을 가져옴
        val token = GlobalApplication.prefs.getString("access_token", "")

        if (token != "") {

            Log.d(TAG, "토큰 값 $token: ")

            showMainActivity.value = true     // 메인 액티비티로의 전환을 활성화

            // 사용자 ID를 GlobalApplication에서 가져옵니다.
            val userId = GlobalApplication.prefs.getString("user_id", "")

            Log.d(TAG, "loginLogic: $userId")

            val getMessengerRoomListWork = MessengerRoomListWork(userId.toLong())

            getMessengerRoomListWork.getMessengerRoomList()


        } else {
            // 토큰이 없는 경우 에러 로그를 출력
            Log.d(TAG, "loginLogic: 뭔가 잘못되고 있다... 액세스토큰값 없음")
        }

        // 로그인 리턴값보고 네비게이터 하는 분기 처리 해줘야함
    }
}