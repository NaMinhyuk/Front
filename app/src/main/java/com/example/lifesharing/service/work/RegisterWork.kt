package com.example.lifesharing.service.work

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.login.model.request_body.RegisterRequestBody
import com.example.lifesharing.login.model.response_body.RegisterResponseBody
import com.example.lifesharing.service.api.RetrofitAPI
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

/** 회원가입 로직을 수행하는 api */
class RegisterWork() {

    val TAG: String = "로그"

    private val service = RetrofitAPI.emgMedService
    val errorMessage: MutableLiveData<String> = MutableLiveData()                  // 에러 메시지를 저장할 LiveData를 선언
    val joinResult : MutableLiveData<RegisterResponseBody> = MutableLiveData()     // 회원가입 결과를 저장할 LiveData를 선언

    fun registerWork(userInfo: RegisterRequestBody, imageFile: MultipartBody.Part) {

        /**
         * call 작업은 두가지로 수행
         * execute - request 보내고 response를 받는 행위를 동기적!
         * enqueue - request 비동기적, response 콜백, 즉 동기적!
         */

        service.registerUser(userInfo, imageFile)
            .enqueue(object : retrofit2.Callback<RegisterResponseBody> {
                override fun onResponse(
                    call: Call<RegisterResponseBody>,
                    response: Response<RegisterResponseBody>
                ) {
                    val result = response.body()

                    if (response.isSuccessful && response.body()?.isSuccess == true) {
                        joinResult.postValue(response.body())     // 회원가입 성공 결과를 LiveData에 저장
                        Log.d(TAG, "RetrofitWork - onResponse() called 회원가입 성공 $result")
                        // 여기서 성공했을 시 accessToken을 저장하는 로직을 구현해야함 실제로는 로그인이 되었을 때 넣어지게끔 로직 구현
                    } else {
                        val responseBody = response.errorBody()?.string()
                        // 이 부분은 JSON 파서를 사용하여 오류 응답을 파싱
                        val errorResponse = Gson().fromJson(responseBody, RegisterResponseBody::class.java)
                        val errorCode = errorResponse?.code ?: "UNKNOWN_ERROR"
                        val errorMsg = errorResponse?.message ?: "알 수 없는 오류가 발생했습니다."

                        errorMessage.postValue("$errorCode: $errorMsg")
                        joinResult.postValue(RegisterResponseBody(false, errorCode, errorMsg, null))
                    }
                }

                override fun onFailure(call: Call<RegisterResponseBody>, t: Throwable) {
                    errorMessage.postValue("네트워크 오류: ${t.message}")
                    Log.d(TAG, "RetrofitWork - onFailure() 화원가입 실패 ${t.message}")
                }
            } )
    }
}