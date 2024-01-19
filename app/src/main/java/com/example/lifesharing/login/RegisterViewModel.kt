package com.example.lifesharing.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.model.request_body.RegisterRequestBody
import com.example.lifesharing.service.RegisterWork

class RegisterViewModel(application: Application) : AndroidViewModel(application) {


    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")
    var nickname: MutableLiveData<String> = MutableLiveData("")
    var checkPassword: MutableLiveData<String> = MutableLiveData("")
    var phoneNumber: MutableLiveData<String> = MutableLiveData("")
    var verifiedNumber: MutableLiveData<String> = MutableLiveData("")

    var showLoginActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    val registerUserData = RegisterRequestBody(
        email.value.toString(),
        password.value.toString(),
        nickname.value.toString(),
        phoneNumber.value.toString()
    )


    fun registerLogic() {
        val retrofitWork = RegisterWork(registerUserData)
        retrofitWork.registerWork()
        // 리턴값 보고 네비게이터 하는 분기 처리 해줘야함
        showLoginActivity.value = true
    }

}