package com.example.lifesharing.login.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.login.model.request_body.LoginRequestBody
import com.example.lifesharing.service.work.MessengerRoomListWork
import com.example.lifesharing.service.work.LoginWork

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")

    var showMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    val TAG = "로그"

    fun loginLogic() {

        val loginUserData = LoginRequestBody(
            email.value.toString(),
            password.value.toString()
        )

        val loginWork = LoginWork(loginUserData)
        loginWork.loginWorkWithCoroutine()

        val token = GlobalApplication.prefs.getString("access_token", "")

        if (token != "") {

            Log.d(TAG, "토큰 값 $token: ")

            showMainActivity.value = true

            val userId = GlobalApplication.prefs.getString("user_id", "")

            Log.d(TAG, "loginLogic: $userId")

            val getMessengerRoomListWork = MessengerRoomListWork(userId.toInt())

            getMessengerRoomListWork.getMessengerRoomList()



        } else {
            Log.d(TAG, "loginLogic: 뭔가 잘못되고 있다... 액세스토큰값 없음")
        }

        // 로그인 리턴값보고 네비게이터 하는 분기 처리 해줘야함
    }
}