package com.example.lifesharing.service.work

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.login.model.response_body.NickNameCheckResponse
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class NicknameCheckWork(application: Application): AndroidViewModel(application) {

    val service = RetrofitAPIwithToken.retrofit()
    val nicknameStatus: MutableLiveData<NicknameStatus> = MutableLiveData()

    val TAG : String = "로그"

    fun checkNickname(nickname: String){
        service.nicknameCheck(nickname).enqueue(object : retrofit2.Callback<NickNameCheckResponse>{
            override fun onResponse(
                call: Call<NickNameCheckResponse>,
                response: Response<NickNameCheckResponse>
            ) {
                if (response.isSuccessful)
                {
                    val result = response.body()?.result?.existNickname ?: false

                    nicknameStatus.postValue(
                        if (result) NicknameStatus.Unavailable else NicknameStatus.Available
                    )

                    Log.d(TAG, "넘겨받은 닉네임: $nickname")
                    Log.d(TAG, "onResponse: $result")
                }
                else {
                    nicknameStatus.postValue(NicknameStatus.Error)
                }
            }

            override fun onFailure(call: Call<NickNameCheckResponse>, t: Throwable) {
                Log.e("통신 실패", "${t.message}")
                nicknameStatus.postValue(NicknameStatus.Error)
            }
        })
    }
}

enum class NicknameStatus {
    Available, Unavailable, Error
}