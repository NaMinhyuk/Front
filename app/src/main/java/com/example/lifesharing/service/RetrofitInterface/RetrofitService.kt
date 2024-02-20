package com.example.lifesharing.service.RetrofitInterface

import com.example.lifesharing.home.home_data.ProductResponse
import com.example.lifesharing.login.model.request_body.LoginRequestBody
import com.example.lifesharing.login.model.request_body.RegisterRequestBody
import com.example.lifesharing.login.model.response_body.LoginResponseBody
import com.example.lifesharing.login.model.response_body.RegisterResponseBody
import com.example.lifesharing.mypage.mypage_api.InquiryRegisterRequestBody
import com.example.lifesharing.mypage.mypage_api.InquiryRegisterResponseBody
import com.example.lifesharing.mypage.mypage_api.InquiryResponse
import com.example.lifesharing.mypage.mypage_api.NoticeResponse
import com.example.lifesharing.mypage.mypage_api.UserInfoResponse
import com.example.lifesharing.mypage.mypage_api.ViewInquiryAnswerResponse
import com.example.lifesharing.mypage.mypage_api.ViewWishListResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface RetrofitService {

    @Headers("Content-Type: application/json")
    @POST("user/join")
    fun registerUserByEnqueue(@Body userInfo: RegisterRequestBody): Call<RegisterResponseBody> // call은 흐름처리 기능 제공

    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun loginUser(@Body userInfo: LoginRequestBody): Response<LoginResponseBody>

    @GET("product/home")
    fun getFilteredProducts(@Query("filter") filter: String) : Call<ProductResponse>

    @GET("user/my-page")
    fun getUserProfile(): Call<UserInfoResponse>

    @GET("heart/list")
    fun getWishList(): Call<ViewWishListResponse>

    @GET("notice")
    fun getNoticeList(@Query("lastNoticeId") lastNoticeId: Long?): Call<NoticeResponse>

    @Multipart
    @POST("user/inquiry")
    fun registerQna(
        @Part ("inquiryDTO") request: InquiryRegisterRequestBody,
        @Part multipartFiles: List<MultipartBody.Part>
    ): Call<InquiryRegisterResponseBody>

    @GET("user/inquires")
    fun getInquiryList(@Query("size") size: Int) : Call<InquiryResponse>

    @GET("user/inquiry")
    fun getViewInquiryAnswer(@Query("inquiryId") inquiryId : Long) : Call<ViewInquiryAnswerResponse>

}