package com.example.lifesharing.login.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.model.request_body.LoginRequestBody
import com.example.lifesharing.service.SharedData.SharedManager
import com.example.lifesharing.service.work.LoginWork

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")

    var showMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    private val sharedManager : SharedManager by lazy { SharedManager(application) }



    suspend fun loginLogic() {

        val loginUserData = LoginRequestBody(
            email.value.toString(),
            password.value.toString()
        )

        val loginWork = LoginWork(loginUserData)
        val result = loginWork.loginWorkWithCoroutine()

        when (result) {
            is Result.Success -> {

            }

            is Result.Error -> {

            }
        }
        // 로그인 리턴값보고 네비게이터 하는 분기 처리 해줘야함
        showMainActivity.value = true
    }
}