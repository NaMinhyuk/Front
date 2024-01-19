package com.example.lifesharing.login

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class SocialLoginViewModel(application: Application) : AndroidViewModel(application) {

    val TAG: String = "로그"

    val TAG1: String = "카카오 로그인 관련"

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
                    Log.i(TAG1, "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun naverLogin() {
        Log.d(TAG, "naverLogin: ")
    }

    fun googleLogin(view: View) {
        Log.d(TAG, "googleLogin: ")
        var i = googleSignInClient.signInIntent
        (view.context as? SocialLoginActivity)?.googleLoginResult?.launch(i)
    }

    fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
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