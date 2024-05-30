package com.example.lifesharing.login.viewModel

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.login.model.request_body.RegisterRequestBody
import com.example.lifesharing.service.work.RegisterWork
import com.google.android.gms.location.LocationServices

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

}