package com.example.lifesharing.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

class PreferenceUtil(context: Context) {

    val gson: Gson = GsonBuilder().create()

    private val prefs: SharedPreferences =
        context.getSharedPreferences("lifesharing", Context.MODE_PRIVATE)

    fun saveJson(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }


    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}