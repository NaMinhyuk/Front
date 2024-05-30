package com.example.lifesharing

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.lifesharing.databinding.ActivityProductDetailBinding
import com.example.lifesharing.messenger.userChat.MessengerDetailWithDummy
import com.example.lifesharing.mypage.mypage_data.UserInfoResultDTO
import com.example.lifesharing.product.Product_Detail_Reserve_Activity
import com.example.lifesharing.product.api.HeartRepository
import com.example.lifesharing.product.data.ImageViewPagerAdapter
import com.example.lifesharing.product.data.MyItemDetailData
import com.example.lifesharing.product.data.MyItemReviewAdapter
import com.example.lifesharing.product.view_model.HeartViewModel
import com.example.lifesharing.product.view_model.HeartViewModelFactory
import com.example.lifesharing.product.view_model.MyItemDetailViewModel
import com.example.lifesharing.product.view_model.MyItemDetailViewModelFactory
import com.example.lifesharing.product.view_model.ProductReviewViewModel
import com.example.lifesharing.profile.Profile_Activity
import com.example.lifesharing.service.RetrofitInterface.RetrofitService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale


class Product_Detail_Activity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : ActivityProductDetailBinding
    private lateinit var viewModel: MyItemDetailViewModel
    private lateinit var reviewViewModel: ProductReviewViewModel
    private lateinit var heartViewModel : HeartViewModel
    private lateinit var reviewAdapter: MyItemReviewAdapter
    private lateinit var userInfoData : UserInfoResultDTO
    private var productDetail: MyItemDetailData? = null


    private lateinit var mapView: com.google.android.gms.maps.MapView
    private var googleMap: GoogleMap?=null

    private lateinit var detailAddr: String

    val TAG:String = "로그"
    private val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL
    var sellerId : Long ?= null
    var sellerName : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.extras?.getLong("productId")

        binding.detailBackBtn.setOnClickListener{
            finish()
        }

        binding.detailBottomReserveBtn.setOnClickListener{
            val intent = Intent(this, Product_Detail_Reserve_Activity::class.java)
            startActivity(intent)
        }


        // mapView 초기화
        mapView = binding.detailMap
        mapView.onCreate(savedInstanceState)

        // 가격 천 단위로 끊기
        val formatter = NumberFormat.getNumberInstance(Locale.US)

        // HeartViewModel 초기화
        val heartRepository = HeartRepository()
        val heartViewModelFactory = HeartViewModelFactory(heartRepository)
        heartViewModel = ViewModelProvider(this, heartViewModelFactory).get(HeartViewModel::class.java)

        // ViewModel 초기화
        // productId가 null이 아닌 경우에만 ViewModel 초기화
        productId?.let {
            viewModel = ViewModelProvider(this, MyItemDetailViewModelFactory(application, it)).get(
                MyItemDetailViewModel::class.java)

            viewModel.productDetail.observe(this, Observer { detail ->
                binding.detailCategoryTitle.text = detail?.myProductResultDTO?.categoryList?.get(0).toString()   // 상단 카테고리 이름
                binding.detailTitle.text = detail?.myProductResultDTO?.categoryList?.get(0).toString()           // 카테고리 이름
                binding.detailLocationTv.text = detail?.myProductResultDTO?.location                             // 위치(동)
                binding.detailNameTv.text = detail?.myProductResultDTO?.name                                     // 제품이름
                binding.detailRatingBar.rating = detail?.myProductResultDTO?.score?.toInt()!!.toFloat()          // 별점
                binding.detailRateTv.text = "(" + detail?.myProductResultDTO?.reviewCount + ")"                  // 리뷰개수
                binding.detailMoneyKeepTv.text = formatter.format(detail?.myProductResultDTO?.deposit)           // 보증금
                binding.detailMoneyDayTv.text = formatter.format(detail?.myProductResultDTO?.dayPrice)           // 일일 대여비
                binding.detailExplanTv.text = detail?.myProductResultDTO?.content                                // 제품 설명
                binding.detailProfileNameIv.text = detail?.myProductResultDTO?.nickname                          // 유저 닉네임
                // 유저 프로필 이미지
                if (!detail?.myProductResultDTO?.userImage.isNullOrEmpty()){
                    Glide.with(this).load(IMAGE_BASE_URL + detail?.myProductResultDTO?.userImage).into(binding.detailProfileIv)
                }
                else{
                    Log.e(TAG, "등록자 이미지 url 불러오기 실패")
                }
                binding.detailMapLocationTv.text = detail?.myProductResultDTO?.detailLocation                 // 상세 주소
                // 이미지, 리뷰 등 추가 데이터 바인딩
                val imageUrl = detail?.myProductResultDTO?.imageUrl

                val viewPager: ViewPager2 = binding.detailProductImg     // 제품 이미지
                val images = imageUrl ?: listOf()
                val adapter = ImageViewPagerAdapter(this, images)
                viewPager.adapter = adapter

                binding.reviewCountTv.text = detail?.myProductResultDTO?.reviewCount.toString()       // 리뷰 개수

                // 좋아요 여부
//                if (detail?.myProductResultDTO?.isLiked == true){
//                    binding.btnDetailHeart.setImageResource(R.drawable.full_heart)
//                } else{
//                    binding.btnDetailHeart.setImageResource(R.drawable.detail_heart)
//                }

                // 찜 버튼 설정
                setHeartButtonIcon(detail?.myProductResultDTO?.isLiked ?: false)

                binding.btnDetailHeart.setOnClickListener {

                    // 찜을 추가하거나 삭제하는 API 호출
                    if (detail.myProductResultDTO?.isLiked == true) {
                        // 이미 찜한 경우에는 삭제 API를 호출
                        heartViewModel.deleteHeart(productId)
                    } else {
                        // 찜하지 않은 경우에는 추가 API를 호출
                        heartViewModel.addHeart(productId)
                    }
                    // 찜 버튼의 아이콘 변경
                    val isLiked = detail.myProductResultDTO?.isLiked ?: false
                    setHeartButtonIcon(!isLiked)
                }


                // 판매자 상세 주소 값 저장
                detailAddr = detail?.myProductResultDTO?.detailLocation.toString()
                // 판매자 아이디 저장
                sellerId = detail?.myProductResultDTO?.userId
                sellerName = detail?.myProductResultDTO?.nickname

                // 지도 띄우기
                mapView.getMapAsync { map ->
                    googleMap = map
                    getAddressFromLocationName(this, detailAddr)
                }

                binding.detailBottomMoneyHourTv.text = formatter.format(detail?.myProductResultDTO?.hourPrice)    // 시간당 대여비
                binding.detailBottomMoneyDayTv.text = formatter.format(detail?.myProductResultDTO?.dayPrice)      // 일일 대여비
            })

            viewModel.getProductDetail()
        } ?: run {
            // productId가 null인 경우의 처리, 예: Activity 종료 또는 오류 메시지 표시
            Log.e(TAG, "제품 아이디 가져오기 실패")
        }

        // 리뷰 리사이클러 뷰
        reviewAdapter = MyItemReviewAdapter(ArrayList())  // 초기에는 빈 리스트로 초기화
        binding.detailReviewList.adapter = reviewAdapter
        binding.detailReviewList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        reviewViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ProductReviewViewModel::class.java)

        reviewViewModel.loadProductReviews(productId!!)

        // ViewModel에서 리뷰 목록을 관찰하고, 변경이 있을 때마다 어댑터에 설정
        reviewViewModel.reviews.observe(this, Observer { reviews ->
            reviewAdapter.setItems(ArrayList(reviews)) // 리뷰 목록을 어댑터에 설정
            reviewAdapter.notifyDataSetChanged() // 변경 내용을 리사이클러뷰에 알리기
        })


        // 판매자 프로필(대여자 프로필) 클릭
        binding.detailProfileNameIv.setOnClickListener {
            val intent = Intent(this, Profile_Activity::class.java)
            intent.putExtra("sellerId", sellerId )
            startActivity(intent)
        }

        userInfoData = GlobalApplication.getUserInfoData()

        // 채팅하기
        binding.detailChatBtnIv.setOnClickListener {
            val intent = Intent(this, MessengerDetailWithDummy::class.java)
            Log.d(TAG, "Sender Id : ${userInfoData.userId}")
            intent.putExtra("sender", userInfoData.userId.toLong())
            intent.putExtra("sellerName", sellerName)

            intent.putExtra("productId", productId)
            startActivity(intent)
        }

        // HeartViewModel 라이브데이터
        heartViewModel.addHeartResponse.observe(this, Observer { response ->
            if (response != null) {
                productDetail?.myProductResultDTO?.isLiked = true
                setHeartButtonIcon(true)
            }
        })

        heartViewModel.deleteHeartResponse.observe(this, Observer { response ->
            if (response != null) {
                productDetail?.myProductResultDTO?.isLiked = false
                setHeartButtonIcon(false)
            }
        })

    }

    private fun setHeartButtonIcon(isLiked: Boolean) {
        val iconResId = if (isLiked) R.drawable.full_heart else R.drawable.detail_heart // 찜 여부에 따라 버튼 아이콘 결정
        binding.btnDetailHeart.setImageResource(iconResId)
    }

    fun getAddressFromLocationName(context: Context, name: String, maxResults: Int = 5) {
        val geocoder = Geocoder(context)
        try {
            Log.d("Address", "input address : ${name}")

            val addressList = geocoder.getFromLocationName(name, maxResults) ?: listOf()
            if (addressList.isNotEmpty()) {
                val address = addressList[0]
                // 직접 위도와 경도를 변수에 할당
                val latitude : Double = address.latitude
                val longitude : Double = address.longitude
                Log.e("Geocoding", "${latitude} ${longitude}")

                // onMapReady 내에서 마커와 카메라 위치를 업데이트 하도록 합니다.
                updateMap(latitude, longitude)
            } else {
                Log.d("Geocoding", "No Address Found")
                // 주소가 없다면 사용자에게 알림
                Toast.makeText(context, "No Address Found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Log.e("Geocoding", "Geocoder IOException: ${e.message}")
        }
    }

    fun updateMap(latitude: Double, longitude: Double) {
        val location = LatLng(latitude, longitude)
        googleMap?.addMarker(MarkerOptions().position(location).title("보호소 위치"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))

        googleMap?.uiSettings?.isZoomControlsEnabled = true   // 상세 화면 지도 확대/축소 버튼 활성화

    }

    override fun onMapReady(p0: GoogleMap) {
        this.googleMap = p0

        val location = LatLng(37.5562,126.9724)
        googleMap?.addMarker(MarkerOptions().position(location).title("위치"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}

