package com.example.lifesharing.service.RetrofitInterface

import com.example.lifesharing.common.response_body.ProductIdResponseBody
import com.example.lifesharing.home.home_data.ProductResponse
import com.example.lifesharing.login.model.request_body.LoginRequestBody
import com.example.lifesharing.login.model.request_body.RegisterRequestBody
import com.example.lifesharing.login.model.response_body.LoginResponseBody
import com.example.lifesharing.login.model.response_body.RegisterResponseBody
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListResponseBody
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResponseBody
import okhttp3.MultipartBody
import com.example.lifesharing.mypage.mypage_api.UserInfoResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @Headers("Content-Type: application/json")
    @Multipart
    @POST("user/join")
    fun registerUserByEnqueue(@Body userInfo: RegisterRequestBody): Call<RegisterResponseBody> // call은 흐름처리 기능 제공


    // 여기서는 멀티파트라서 콘텐트 타입은 제거해주자~!
    @Multipart
    @POST("user/join")
    fun registerUser(
        @Part ("joinDTO") joinDTO: RegisterRequestBody,
        @Part multipartFile: MultipartBody.Part
    ): Call<RegisterResponseBody> // call은 흐름처리 기능 제공


    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun loginUser(@Body userInfo: LoginRequestBody): Response<LoginResponseBody>

    @GET("chats/room-list/{sender}")
    fun getMessengerRoomList(
        @Path("sender") senderId : Int
    ) : Call<MessengerRoomListResponseBody>

    @GET("product/home")
    fun getFilteredProducts(@Query("filter") filter: String) : Call<ProductResponse>

    @GET("chats/room-list/{user}")
    fun getMessengerRoomListTemp(
        @Path("user") userId: Int
    ) : Call<MessengerRoomListTempResponseBody>

    @GET("product/detail")
    fun getProductData(
        @Query("productId") productId: Int
    ) : Call<ProductIdResponseBody>

    @GET("user/my-page")
    fun getUserProfile(): Call<UserInfoResponse>

}