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
import com.example.lifesharing.mypage.mypage_api.NoticeDataResponse
import com.example.lifesharing.mypage.mypage_api.UserInfoResponse
import com.example.lifesharing.mypage.review.model.request_body.ReviewRequestBody
import com.example.lifesharing.mypage.review.model.response_body.GetReviewResponseBody
import com.example.lifesharing.mypage.review.model.response_body.ReviewResponseBody
import com.example.lifesharing.product.model.request_body.ProductPayRequestBody
import com.example.lifesharing.product.model.response_body.Detail_ResponseBody
import com.example.lifesharing.product.model.response_body.ProductMenuResponseBody
import com.example.lifesharing.product.model.response_body.ProductPayResponseBody
import com.example.lifesharing.regist.model.request_body.ProductRegisterRequestBody
import com.example.lifesharing.regist.model.response_body.ProductRegisterResponseBody
import com.example.lifesharing.reservation.ReservationResponseBody
import com.example.lifesharing.service.work.DetailProduct
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
import retrofit2.http.PartMap
import retrofit2.http.Query

interface RetrofitService {

    @Multipart
    @POST("user/join")
    fun registerUserByEnqueue(@Body userInfo: RegisterRequestBody): Call<RegisterResponseBody> // call은 흐름처리 기능 제공

    @Multipart
    @POST("product/register")
    fun registerProduct(
        @Part files: List<MultipartBody.Part>,
        @Part ("request") request: ProductRegisterRequestBody
        ): Call<ProductRegisterResponseBody>


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

    @Headers("Content-Type: applcation/json")
    @POST("payments/{productId}/toss/reserver")
    fun registerReservation()

    @Multipart
    @POST("reviews/write/{reservationId}")
    fun registerReview(
        @Path("reservationId") reservationId : Int,
        @Part ("request") request: ReviewRequestBody,
        @Part files: List<MultipartBody.Part>,
    ): Call<ReviewResponseBody>

    @GET("reviews/list")
    fun getReviewList() : Call<GetReviewResponseBody>


    @GET("chats/room-list/{sender}")
    fun getMessengerRoomList(
        @Path("sender") senderId : Int
    ) : Call<MessengerRoomListResponseBody>


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

    @GET("notice")
    fun getNoticeList(@Query("page") page: Int): Call<NoticeDataResponse>

    @POST("social/kakao/login")
    suspend fun getKakaoUser(
        @Query("idToken") idToken: String
    ) : Response<LoginResponseBody>

    @POST("social/google/login")
    suspend fun getGoogleUser(
        @Query("idToken") idToken: String
    ) : Response<LoginResponseBody>

}