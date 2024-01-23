package com.example.lifesharing.login.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.R
import com.example.lifesharing.login.SocialLoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class SocialLoginViewModel(application: Application) : AndroidViewModel(application) {

    val TAG: String = "로그"

    val TAG1: String = "카카오"

    val TAG2: String = "네이버"

    val TAG3: String = "구글"



    var auth = FirebaseAuth.getInstance()

    var navigatedRegisterActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var navigatedResetPasswordActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var navigatedLoginActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var navigatedMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    var kakaoToken : String = ""

    val context = getApplication<Application>().applicationContext

    var googleSignInClient : GoogleSignInClient

    init {
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun kakaoLogin() {
        println("카카오 로그인 눌렀어요~!")
        Log.d(TAG1, "kakaoLogin: ")
        // 로그인 조합 예제

        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG1, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                kakaoToken = token.accessToken // 서버에 저장하는 로직 구현만 하면 됌
                Log.i(TAG1, "카카오계정으로 로그인 성공 ${token.accessToken}")
                navigatedMainActivity.value = true
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG1, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    kakaoToken = token.accessToken
                    Log.i(TAG1, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    navigatedMainActivity.value = true
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun naverLogin() {

        NaverIdLoginSDK.initialize(context, com.example.lifesharing.BuildConfig.NAVER_CLIENT_ID, com.example.lifesharing.BuildConfig.NAVER_SECRET_KEY, "lifesharing")

        Log.d(TAG, "naverLogin: ")
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
                Log.e(TAG2, "네이버 로버그인 실패 $errorCode, $errorDescription" )
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(context, oAuthLoginCallback)
    }

    fun googleLogin(view: View) {
        Log.d(TAG3, "googleLogin: ")
        var i = googleSignInClient.signInIntent
        (view.context as? SocialLoginActivity)?.googleLoginResult?.launch(i)
    }

    fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG3, "firebaseAuthWithGoogle: ${auth.currentUser.toString()}")
                navigatedMainActivity.value = true
            } else {

            }
        }
    }

    fun registerPage() {
        Log.d(TAG, "registerPage: ")
        navigatedRegisterActivity.value = true
    }

    fun resetPassword() {
        Log.d(TAG, "resetPassword: ")
        navigatedResetPasswordActivity.value = true
    }

    fun loginToEmail() {
        Log.d(TAG, "loginToEmail: ")
        navigatedLoginActivity.value = true
    }
}