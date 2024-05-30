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
import com.example.lifesharing.service.work.GoogleLoginWork
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class SocialLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivitySocialLoginBinding
    val socialLoginViewModel : SocialLoginViewModel by viewModels()  // SocialLoginViewModel 클래스의 인스턴스로 초기화

    val TAG: String = "로그"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_social_login)
        binding.viewModel = socialLoginViewModel
        binding.activity = this
        binding.lifecycleOwner = this
        NaverIdLoginSDK.initialize(this, "3SvlouKq1mjS2CR8T2nX", "3R_PtZHz5X", "lifesharing")

        // SocialLoginViewModel의 LiveData를 관찰하여 적절한 액션을 취함
        setObserve()

        // 카카오 로그인 시 필요한 해시 값을 구하기 위함
        var keyHash = Utility.getKeyHash(this)
        Log.e(TAG, "해시 키 값 : ${keyHash}")


        initFirebase()
    }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e("FCM targetToken", task.result.toString())
            }
        }
    }

    fun setObserve() {
        // 로그인 화면으로 이동을 관찰하는 코드
        socialLoginViewModel.navigatedLoginActivity.observe(this) {
            if(it) {
                // true면 화면 전환
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        // 회원가입 화면으로 이동을 관찰하는 코드
        socialLoginViewModel.navigatedRegisterActivity.observe(this) {
            if(it) {
                // true면 화면 전환
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
        // 비밀번호 초기화 화면으로 이동을 관찰하는 코드
        socialLoginViewModel.navigatedResetPasswordActivity.observe(this) {
            if(it) {
                // true면 화면 전환
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
        // 메인 화면으로 이동을 관찰하는 코드
        socialLoginViewModel.navigatedMainActivity.observe(this) {
            if(it) {
                // true면 화면 전환
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }


    // 구글 로그인이 성공한 결과값을 받는 함수
    var googleLoginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->

        val data = result.data   // 로그인 결과 데이터를 변수에 저장
        if (data != null) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)    // 로그인 결과를 가져옴
                val account = task.getResult(ApiException::class.java)   // ApiException클래스를 사용하여 로그인 결과에서 계정 정보를 추출

                // 계정 정보에 idToken값이 null이 아닐 경우
                account?.idToken?.let {
                    Log.d(TAG, "Google ID Token: $it")
                    // GoogleLoginWork 클래스 사용 - 구글 로그인 수행
                    GoogleLoginWork(it).googleLoginWorkCoroutine()

                    //val account = task.getResult(ApiException::class.java)
                    // 계정 정보가 올바르게 추출되었는지 Log로 확인
                    Log.d("구글", "이메일 : " + account.email)
                    Log.d("구글", "로그인한 유저의 성 : " + account.familyName)
                    Log.d("구글", "로그인한 유저의 이름 : " + account.givenName)
                    Log.d("구글", "로그인한 유저의 전체 이름 : " + account.displayName)
                    Log.d("구글", "프로필 사진 주소 : " + account.photoUrl)
                }
            } catch (e: ApiException) {
                Log.e(TAG, "Google sign in failed", e)
            }
        }
    }

    fun naverLogin() {
        //NaverIdLoginSDK.initialize(this, com.example.lifesharing.BuildConfig.NAVER_CLIENT_ID, com.example.lifesharing.BuildConfig.NAVER_SECRET_KEY, "lifesharing")

        Log.d("네이버", "naverLogin: ")
        val oAuthLoginCallback = object : OAuthLoginCallback {
            // 로그인 성공 시 호츌
            override fun onSuccess() {
                // 계정 정보가 올바르게 추출되었는지 Log로 확인
                Log.d("네이버", "AccessToken : " + NaverIdLoginSDK.getAccessToken())
                Log.d("네이버", "ReFreshToken : " + NaverIdLoginSDK.getRefreshToken())
                Log.d("네이버", "Expires : " + NaverIdLoginSDK.getExpiresAt().toString())
                Log.d("네이버", "TokenType : " + NaverIdLoginSDK.getTokenType())
                Log.d("네이", "State : " + NaverIdLoginSDK.getState().toString())
            }

            // 로그인 실 시 호출
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