package com.example.lifesharing.service.RetrofitInterface

import com.example.lifesharing.home.home_data.ProductResponse
import com.example.lifesharing.login.model.request_body.LoginRequestBody
import com.example.lifesharing.login.model.request_body.RegisterRequestBody
import com.example.lifesharing.login.model.response_body.LoginResponseBody
import com.example.lifesharing.login.model.response_body.RegisterResponseBody
import com.example.lifesharing.product.model.request_body.ProductPayRequestBody
import com.example.lifesharing.product.model.response_body.Detail_ResponseBody
import com.example.lifesharing.product.model.response_body.ProductMenuResponseBody
import com.example.lifesharing.product.model.response_body.ProductPayResponseBody
import com.example.lifesharing.regist.model.request_body.ProductRegisterRequestBody
import com.example.lifesharing.regist.model.response_body.ProductRegisterResponseBody
import com.example.lifesharing.reservation.ReservationResponseBody
import com.example.lifesharing.service.work.DetailProduct
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @Headers("Content-Type: application/json")
    @POST("user/join")
    fun registerUserByEnqueue(@Body userInfo: RegisterRequestBody): Call<RegisterResponseBody> // call은 흐름처리 기능 제공

    @Multipart
    @POST("product/register")
    fun registerProduct(
        @Part("request") request: ProductRegisterRequestBody,
        @Part files: ArrayList<MultipartBody.Part>
    ): Call<ProductRegisterResponseBody>


    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun loginUser(@Body userInfo: LoginRequestBody): Response<LoginResponseBody>

    @GET("product/home")
    fun getFilteredProducts(@Query("filter") filter: String) : Call<ProductResponse>

    @GET("product/detail")
    fun getProductDetails(@Query("productId") productId: Int) : Call<Detail_ResponseBody>

    @GET("reservations/list")
    fun getReservations(@Query("filter") filter: String): Call<ReservationResponseBody>

    @GET("product/category")
    fun getProductMenu(@Query("categoryId") categoryId: Int): Call<ProductMenuResponseBody>

    @POST("payments/{productId}/toss/reserve")
    fun postPaymentDetails(@Path("productId") productId: Int,
                           @Body requestBody: ProductPayRequestBody)
    : Call<ProductPayResponseBody>


}