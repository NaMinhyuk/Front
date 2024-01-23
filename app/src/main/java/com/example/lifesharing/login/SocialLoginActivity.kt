package com.example.lifesharing.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.lifesharing.MainActivity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivitySocialLoginBinding
import com.example.lifesharing.login.viewModel.SocialLoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class SocialLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivitySocialLoginBinding
    val socialLoginViewModel : SocialLoginViewModel by viewModels()

    val TAG1: String = "구글"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_social_login)
        binding.viewModel = socialLoginViewModel
        binding.activity = this
        binding.lifecycleOwner = this
        NaverIdLoginSDK.initialize(this, "3SvlouKq1mjS2CR8T2nX", "3R_PtZHz5X", "lifesharing")
        setObserve()
    }

    fun setObserve() {
        socialLoginViewModel.navigatedLoginActivity.observe(this) {
            if(it) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        socialLoginViewModel.navigatedRegisterActivity.observe(this) {
            if(it) {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
        socialLoginViewModel.navigatedResetPasswordActivity.observe(this) {
            if(it) {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
        socialLoginViewModel.navigatedMainActivity.observe(this) {
            if(it) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    // 구글 로그인이 성공한 결과값을 받는 함수

    var googleLoginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->

        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        account.idToken // 로그인한 사용자 정보를 암호화한 값
        socialLoginViewModel.firebaseAuthWithGoogle(account.idToken)

        Log.d(TAG1, "${account.idToken} ")
    }

    fun naverLogin() {
        //NaverIdLoginSDK.initialize(this, com.example.lifesharing.BuildConfig.NAVER_CLIENT_ID, com.example.lifesharing.BuildConfig.NAVER_SECRET_KEY, "lifesharing")

        Log.d("네이버", "naverLogin: ")
        val oAuthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                Log.d("네이버", "AccessToken : " + NaverIdLoginSDK.getAccessToken())
                Log.d("네이버", "ReFreshToken : " + NaverIdLoginSDK.getRefreshToken())
                Log.d("네이버", "Expires : " + NaverIdLoginSDK.getExpiresAt().toString())
                Log.d("네이버", "TokenType : " + NaverIdLoginSDK.getTokenType())
                Log.d("네이", "State : " + NaverIdLoginSDK.getState().toString())
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.e("네이버", "네이버 로버그인 실패 $errorCode, $errorDescription" )
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oAuthLoginCallback)
    }


}