package com.example.lifesharing.search.work

import android.util.Log
import com.example.lifesharing.search.model.response_body.SearchResponse
import com.example.lifesharing.search.model.response_body.SearchResult
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

/** 검색 요청 Model */
class SearchWork {
    private val service = RetrofitAPIwithToken.retrofit()
    val TAG : String = "제품 검색 로그"

    fun getSearchProduct(keyword : String, filter : String, callback : (List<SearchResult>?, String?) -> Unit){
        service.getSearch(keyword, filter).enqueue(object : retrofit2.Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                // 응답이 성공적일 경우
                if (response.isSuccessful){
                    callback(response.body()?.result, null)
                    Log.d(TAG, "검색 목록 조회 성공 : ${response.body()}")
                }
                // 응답이 실패일 경우
                else{
                    callback(null, "검색 목록 조회 실패")
                    Log.e(TAG, "검색 목록 조회 실패 : ${response}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                callback(null, "네트워크 통신 오류")
                Log.e(TAG, "네트워크 통신 오류 : ${t.message}")
            }

        })
    }
}