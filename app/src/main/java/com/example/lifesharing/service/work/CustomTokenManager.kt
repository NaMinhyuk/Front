package com.example.lifesharing.service.work

import com.kakao.sdk.auth.TokenManageable
import com.kakao.sdk.auth.TokenManager.Companion.tokenKey
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.AESCipher
import com.kakao.sdk.common.util.Cipher
import com.kakao.sdk.common.util.KakaoJson
import com.kakao.sdk.common.util.PersistentKVStore
import com.kakao.sdk.common.util.SdkLog
import com.kakao.sdk.common.util.SharedPrefsWrapper

/** 카카오 로그인 SDK를 사용하는 Android Application에서 사용자 인증 토큰을 안전하게 관리하기 위한 클래스 */
class CustomTokenManager(
    private val appCache: PersistentKVStore = SharedPrefsWrapper(
        KakaoSdk.applicationContextInfo.sharedPreferences   // SharedPrefsWrapper를 사용하여 카카오 SDK에서 제공하는 sharedPreferences을 활용하여 appCache에 토큰 정보 저장
    ),
    private val encryptor: Cipher = AESCipher(),  // AESCipher 인스턴스를 사용하여 토큰 정보를 암호화
) : TokenManageable {                             // TokenManageable 인터페이스를 구현

    private var currentToken: OAuthToken?     // 현재 사용자의 OAuth 토큰을 저장하는 변수

    init {
        currentToken = appCache.getString(tokenKey)?.let {   // 저장된 토큰 문자열을 가져옴
            try {
                KakaoJson.fromJson<OAuthToken>(
                    encryptor.decrypt(it),   // 가져온 토큰 값을 복호화하여 OAuthToken 객체로 변환
                    OAuthToken::class.java
                )
            } catch (e: Throwable) {
                SdkLog.e(e)
                null
            }
        }
    }

    override fun getToken(): OAuthToken? = currentToken    // 현재 저장된 토큰을 반환

    @Synchronized
    override fun setToken(token: OAuthToken) {   // 새로운 토큰을 받아 저장하고, 암호화하여 appCache에 저장
        val newToken = token.copy()
        try {
            appCache.putString(tokenKey, encryptor.encrypt(KakaoJson.toJson(newToken))).commit()
        } catch (e: Throwable) {
            SdkLog.e(e)
        }
        currentToken = newToken
    }

    override fun clear() {    // 현재 토큰을 메모리에서 제거
        currentToken = null
        appCache.remove(tokenKey).commit()    // appCache에서도 삭제하여 사용자의 로그인 정보를 완전히 초기화
    }

    companion object {
        const val tokenKey = "com.kakao.sdk.oauth_token"
    }
}
