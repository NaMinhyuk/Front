package com.example.lifesharing.login

import android.app.AlertDialog
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.model.request_body.LoginRequestBody
import com.example.lifesharing.service.LoginWork

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")

    var showMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    val loginUserData = LoginRequestBody(
        email.value.toString(),
        password.value.toString()
    )

    fun loginLogic() {
        val loginWork = LoginWork(loginUserData)
        loginWork.registerWork()
        // 로그인 리턴값보고 네비게이터 하는 분기 처리 해줘야함
        showMainActivity.value = true
    }
}