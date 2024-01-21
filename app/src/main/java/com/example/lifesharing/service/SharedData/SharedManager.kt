package com.example.lifesharing.service.SharedData

import android.content.Context
import android.content.SharedPreferences
import com.example.lifesharing.model.User
import com.example.lifesharing.model.response_body.LoginResponseBody
import com.example.lifesharing.model.response_body.LoginResult

import com.example.lifesharing.service.SharedData.PreferenceHelper.get
import com.example.lifesharing.service.SharedData.PreferenceHelper.set

class SharedManager (context: Context){
    private val prefs : SharedPreferences = PreferenceHelper.defaultPrefs(context)

    fun saveLoginData(loginResult: LoginResult) {
        prefs["userId"] = loginResult.userId
        prefs["token"] = loginResult.token
        prefs["createdAt"] = loginResult.createdAt
    }

    fun getLoginData() : LoginResult {
        return LoginResult().apply {
            userId = prefs["userId", ]
            token = prefs["token", ""]
            createdAt = prefs["createdAt", ""]
        }
    }

    fun saveCurrentUser(user: User) {
        prefs["idToken"] = user.idToken
        prefs["email"] = user.email
        prefs["nickname"] = user.nickname
        prefs["phone"] = user.phone
    }

    fun getCurrentUser() : User {
        return User().apply {
            idToken = prefs["idToken", ""]
            email = prefs["email", ""]
            nickname = prefs["nickname", ""]
            phone = prefs["phone", ""]
        }
    }
}