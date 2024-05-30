package com.example.lifesharing.service.RetrofitInterface

import com.example.lifesharing.common.response_body.ProductIdResponseBody
import com.example.lifesharing.home.home_data.ProductResponse
import com.example.lifesharing.login.model.request_body.LoginRequestBody
import com.example.lifesharing.login.model.request_body.RegisterRequestBody
import com.example.lifesharing.login.model.response_body.LoginResponseBody
import com.example.lifesharing.login.model.response_body.NickNameChangeResponse
import com.example.lifesharing.login.model.response_body.NickNameCheckResponse
import com.example.lifesharing.login.model.response_body.RegisterResponseBody
import com.example.lifesharing.messenger.model.response_body.ChatListResponse
import com.example.lifesharing.messenger.model.response_body.CreateChatRoomResponse
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListResponseBody
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResponseBody
import com.example.lifesharing.mypage.model.response_body.ChangePwResponse
import com.example.lifesharing.mypage.model.response_body.HeartListResponse
import com.example.lifesharing.mypage.model.response_body.QnaDetailResponse
import com.example.lifesharing.mypage.model.response_body.QnaResponse
import com.example.lifesharing.mypage.model.response_body.QnaWaitResponse
import com.example.lifesharing.mypage.model.response_body.RegisterHistoryResponse
import com.example.lifesharing.mypage.model.response_body.UsageHistoryResponse
import okhttp3.MultipartBody
import com.example.lifesharing.mypage.mypage_data.UserInfoResponse
import com.example.lifesharing.mypage.model.response_body.UserProfileInfoResponse
import com.example.lifesharing.mypage.mypage_data.PwData
import com.example.lifesharing.mypage.review.model.request_body.ReviewRequestBody
import com.example.lifesharing.mypage.review.model.response_body.GetReviewResponseBody
import com.example.lifesharing.mypage.review.model.response_body.ReviewResponseBody
import com.example.lifesharing.product.api.AddHeartResponse
import com.example.lifesharing.product.api.CategoryResponse
import com.example.lifesharing.product.api.DeleteHeartResponse
import com.example.lifesharing.product.api.ProductDeleteResponse
import com.example.lifesharing.product.api.ProductEditInfoResponse
import com.example.lifesharing.product.api.ProductReviewResponse
import com.example.lifesharing.product.api.ProductUpdateResponse
import com.example.lifesharing.product.data.MyRegisterProductData
import com.example.lifesharing.product.data.UpdateRequestData
import com.example.lifesharing.profile.model.response_body.SellerProductResponse
import com.example.lifesharing.profile.model.response_body.SellerResponseBody
import com.example.lifesharing.profile.model.response_body.SellerReviewResponse
import com.example.lifesharing.regist.model.response_body.ProductRegisterResponseBody
import com.example.lifesharing.reservation.api.ReservationResponseBody
import com.example.lifesharing.search.model.response_body.SearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @Headers("Content-Type: application/json")
    @Multipart
    @POST("user/join")
    fun registerUserByEnqueue(@Body userInfo: RegisterRequestBody): Call<RegisterResponseBody> // call은 흐름처리 기능 제공

    // 제품 등록
    @Multipart
    @POST("product/register")
    fun registerProduct(
        @Part files: List<MultipartBody.Part>,
        @Part request: MultipartBody.Part
    ): Call<ProductRegisterResponseBody>


    // 여기서는 멀티파트라서 콘텐트 타입은 제거해주자~!
    @Multipart
    @POST("user/join")
    fun registerUser(
        @Part ("joinDTO") joinDTO: RegisterRequestBody,
        @Part multipartFile: MultipartBody.Part
    ): Call<RegisterResponseBody> // call은 흐름처리 기능 제공

    // 닉네임 중복 확인
    @POST("user/check-nickname")
    fun nicknameCheck(
        @Body nickname : String
    ) : Call<NickNameCheckResponse>

    // 닉네임 변경
    @PATCH("user/change/nickname")
    fun nicknameChange(
        @Body nickname : String
    ) : Call<NickNameChangeResponse>

    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun loginUser(@Body userInfo: LoginRequestBody): Response<LoginResponseBody>

    @Headers("Content-Type: applcation/json")
    @POST("payments/{productId}/toss/reserver")
    fun registerReservation()

    @Multipart
    @POST("reviews/write/{reservationId}")
    fun registerReview(
        @Path("reservationId") reservationId : Long,
        @Part ("request") request: ReviewRequestBody,
        @Part files: List<MultipartBody.Part>,
    ): Call<ReviewResponseBody>

    @GET("reviews/list")
    fun getReviewList() : Call<GetReviewResponseBody>


    @GET("chats/room-list/{sender}")
    fun getMessengerRoomList(
        @Path("sender") senderId : Int
    ) : Call<MessengerRoomListResponseBody>


    // 홈 제품 조회
    @GET("product/home")
    fun getFilteredProducts(@Query("filter") filter: String) : Call<ProductResponse>

    // 카테고리별 제품 조회
    @GET("product/category")
    fun getCategoryProduct(
        @Query("categoryId") categoryId : Long
    ) : Call<CategoryResponse>

    // 제품 검색
    @GET("product/search")
    fun getSearch(
        @Query("keyword") keyword : String,
        @Query("filter") filter : String
    ) : Call<SearchResponse>

    // 채팅방 리스트
    @GET("chats/room-list/{user}")
    fun getMessengerRoomListTemp(
        @Path("user") userId: Long
    ) : Call<MessengerRoomListTempResponseBody>

    // 채팅방 만들기
    @POST("chats/make/{sender}/{productId}")
    fun createChatRoom(
        @Path("sender") senderId : Long,
        @Path("productId") productId : Long
    ) : Call<CreateChatRoomResponse>

    // 채팅 내용 보기
    @GET("chats/chat-list/{chatroom}")
    fun loadChatContent(
        @Path("chatroom") roodId : Long
    ) : Call<ChatListResponse>

    // 제품 상세 조회
    @GET("product/detail")
    fun getProductData(
        @Query("productId") productId: Long
    ) : Call<ProductIdResponseBody>

    // 제품에 대한 리뷰 조회
    @GET("reviews/product")
    fun getProductReview(
        @Query("productId") productId : Long
    ) : Call<ProductReviewResponse>

    // 제품 수정 정보 요청
    @GET("product/info/{productId}")
    fun getProductEditInfo(
        @Path("productId") productId : Long
    ) : Call<ProductEditInfoResponse>

    // 제품 정보 수정
    @PATCH("product/update/{productId}")
    fun updateProductInfo(
        @Path("productId") productId: Long,
        @Body updateIfo : UpdateRequestData
    ) : Call<ProductUpdateResponse>

    // 제품 이미지 수정
    @Multipart
    @PUT("product/update-image/{productId}")
    fun updateProductImage(
        @Path("productId") productId: Long,
        @Part imageUrl: List<MultipartBody.Part>
    ) : Call<ProductUpdateResponse>

    // 찜 생성
    @POST("heart/create")
    fun addHeart(
        @Query("productId") productId: Long
    ): Call<AddHeartResponse>

    // 찜 삭제
    @DELETE("heart/remove")
    fun delHeart(
        @Query("productId") productId: Long
    ): Call<DeleteHeartResponse>

    // 마이페이지
    @GET("user/my-page")
    fun getUserProfile(): Call<UserInfoResponse>

    // 마이페이지 내정보 수정 페이지
    @GET("user/info")
    fun getUserInfo() : Call<UserProfileInfoResponse>

    // 비밀번호 변경
    @PATCH("user/password")
    fun changePw(
        @Body changePw : PwData
    ) : Call<ChangePwResponse>

    // 마이페이지 등록내역
    @GET("product/my-regist")
    fun getRegisterHistory() : Call<RegisterHistoryResponse>

    // My 아이템
    @GET("product/my")
    fun getMyRegisterProduct(): Call<MyRegisterProductData>

    // 제품 삭제
    @DELETE("product/delete/{productId}")
    fun productDelete(
        @Path("productId") productId : Long
    ) : Call<ProductDeleteResponse>

    // 소셜로그인 - 카카오
    @POST("social/kakao/login")
    suspend fun getKakaoUser(
        @Query("code") code: String
    ) : Response<LoginResponseBody>

    // 소셜로그인 - 구글
    @POST("social/google/login")
    suspend fun getGoogleUser(
        @Query("idToken") idToken: String
    ) : Response<LoginResponseBody>

    // 대여자 프로필
    @GET("seller/profile/{sellerId}")
    fun getSellerProfile(
        @Path("sellerId") sellerId : Long
    ) : Call<SellerResponseBody>

    // 대여자 프로필 - 대여물품
    @GET("seller/{sellerId}/products")
    fun getSellerProducts(
        @Path("sellerId") sellerId : Long
    ) : Call<SellerProductResponse>

    // 대여자 프로필 - 대여중 물품
    @GET("seller/{sellerId}/rentProduct")
    fun getSellerRentProducts(
        @Path("sellerId") sellerId : Long
    ) : Call<SellerProductResponse>

    // 대여자 프로필 - 리뷰 목록
    @GET("seller/{sellerId}/reviews")
    fun getSellerReviews(
        @Path("sellerId") sellerId : Long
    ) : Call<SellerReviewResponse>

    // 찜목록
    @GET("heart/list")
    fun getHeartList(
    ) : Call<HeartListResponse>

    // 문의작성
    @Multipart
    @POST("user/inquiry")
    fun registerQna(
        @Part inquiryDTO: MultipartBody.Part,
        @Part multipartFiles: List<MultipartBody.Part>,
    ) : Call<QnaResponse>

    // 문의한 내용
    @GET("user/inquires")
    fun getInquiryList(
        @Query("lastInquiryId") lastInquiryId : Long?, // 옵셔널 파라미터
        @Query("size") size : Int = 1  // 필수 파라미터
    ) : Call<QnaWaitResponse>

    // 문의 상세 & 답변
    @GET("user/inquiry/{inquiry-id}")
    fun getInquiryDetail(
        @Path("inquiry-id") inquiryId : Long
    ) : Call<QnaDetailResponse>

    // 예약
    @GET("reservations/list")
    fun getReservationList(
        @Query("filter") filter: String
    ): Call<ReservationResponseBody>

    // 이용 내역
    @GET("reservations/user/list")
    fun getUserUseHistory() : Call<UsageHistoryResponse>

}