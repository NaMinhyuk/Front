package com.example.lifesharing.login.viewModel

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.login.model.request_body.RegisterRequestBody
import com.example.lifesharing.service.work.RegisterWork

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    val TAG: String = "데이터확인"

    val PHOTO: String = "photo"

    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")
    var name: MutableLiveData<String> = MutableLiveData("")
    var checkPassword: MutableLiveData<String> = MutableLiveData("")
    var phone: MutableLiveData<String> = MutableLiveData("")
    var verifiedNumber: MutableLiveData<String> = MutableLiveData("")

    var showLoginActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    private lateinit var pickSingleMediaLaunder: ActivityResultLauncher<Intent>






    fun registerLogic() {
         val registerUserData = RegisterRequestBody(
            email.value.toString(),
            password.value.toString(),
            name.value.toString(),
            phone.value.toString()
        )

        Log.d(TAG, "$registerUserData ")

        val retrofitWork = RegisterWork(registerUserData, null)
        retrofitWork.registerWork()
        // 리턴값 보고 네비게이터 하는 분기 처리 해줘야함
        showLoginActivity.value = true
    }

}