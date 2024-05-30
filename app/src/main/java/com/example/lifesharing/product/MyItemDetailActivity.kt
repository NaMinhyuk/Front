package com.example.lifesharing.product

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.lifesharing.BuildConfig
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMyItemDetailBinding
import com.example.lifesharing.databinding.DialogCustomBinding
import com.example.lifesharing.product.data.ImageViewPagerAdapter
import com.example.lifesharing.product.data.MyItemReviewAdapter
import com.example.lifesharing.product.view_model.DeleteViewModel
import com.example.lifesharing.product.view_model.DeleteViewModelFactory
import com.example.lifesharing.product.view_model.MyItemDetailViewModel
import com.example.lifesharing.product.view_model.MyItemDetailViewModelFactory
import com.example.lifesharing.product.view_model.ProductReviewViewModel
import com.example.lifesharing.profile.Profile_Activity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale

/**
 * MY아이템 화면
 */
class MyItemDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMyItemDetailBinding
    private lateinit var viewModel: MyItemDetailViewModel     // 아이템 상세 정보를 위한 뷰모델 객체
    private lateinit var reviewViewModel: ProductReviewViewModel  // 제품 리뷰 조회 뷰모델
    private lateinit var deleteViewModel: DeleteViewModel     // 제품 삭제를 위한 뷰모델
    private lateinit var reviewAdapter: MyItemReviewAdapter   // 제품에 대한 리뷰 리사이클러뷰 어댑터

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap?=null

    private lateinit var detailAddr: String   // 지도에 위치를 표시하기 위한 정보를 저장하는 객체 생성

    val TAG:String = "로그"
    private val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMyItemDetailBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val productId = intent.extras?.getLong("productId")

        // mapView 초기화
        mapView = binding.detailMap
        mapView.onCreate(savedInstanceState)

        // 가격 천 단위로 끊기
        val formatter = NumberFormat.getNumberInstance(Locale.US)

        // ViewModel 초기화
        // productId가 null이 아닌 경우에만 ViewModel 초기화
        productId?.let {
            viewModel = ViewModelProvider(this, MyItemDetailViewModelFactory(application, it)).get(
                MyItemDetailViewModel::class.java)

            viewModel.productDetail.observe(this, Observer { detail ->     // 뷰 모델로부터 얻어진 제품에 대한 상세 정보를 관찰 -> UI에 표시
                detail?.let {
                    binding.detailCategory.text = detail?.myProductResultDTO?.categoryList?.get(0).toString()                // 카테고리 이름
                    binding.detailLocationTv.text = detail?.myProductResultDTO?.location                                   // 위치(동)
                    binding.detailNameTv.text = detail?.myProductResultDTO?.name                                           // 제품이름
                    binding.ratingBar.rating = detail?.myProductResultDTO?.score?.toInt()!!.toFloat()                      // 별점
                    binding.reviewCount.text = "(" + detail?.myProductResultDTO?.reviewCount + ")"                         // 리뷰개수
                    binding.depositPrice.text = formatter.format(detail?.myProductResultDTO?.deposit) + "원"                // 보증금
                    binding.dayPriceTitle.text = formatter.format(detail?.myProductResultDTO?.dayPrice) + "원"               // 일일 대여비
                    binding.detailContent.text = detail?.myProductResultDTO?.content                                        // 제품 설명
                    binding.detailProfileNameIv.text = detail?.myProductResultDTO?.nickname                             // 유저 닉네임
                    // 유저 프로필 이미지
                    if (!detail?.myProductResultDTO?.userImage.isNullOrEmpty()){
                        Glide.with(this).load(IMAGE_BASE_URL + detail?.myProductResultDTO?.userImage).into(binding.detailProfileIv)
                    }
                    else{
                        Log.e(TAG, "등록자 이미지 url 불러오기 실패")
                        Glide.with(this).load(R.drawable.profile_userimg).into(binding.detailProfileIv)   // 기본이미지로 설정
                    }
                    binding.detailMapLocationTv.text = detail?.myProductResultDTO?.detailLocation                 // 상세 주소

                    // 이미지, 리뷰 등 추가 데이터 바인딩
                    val imageUrl = detail?.myProductResultDTO?.imageUrl

                    // 이미지 뷰 ViewPager 설정
                    val viewPager: ViewPager2 = binding.detailImage
                    val images = imageUrl ?: listOf()
                    val adapter = ImageViewPagerAdapter(this, images)
                    viewPager.adapter = adapter

                    // 초기에 모든 탭에 동그란 인디케이터 추가
                    TabLayoutMediator(binding.indicatorTl, viewPager) { tab, position ->
                        // 초기 아이콘 설정
                        tab.icon = if (position == viewPager.currentItem) {
                            ContextCompat.getDrawable(this, R.drawable.indicator_shape_blue)
                        } else {
                            ContextCompat.getDrawable(this, R.drawable.indicator_shape_gray)
                        }
                    }.attach()


                    binding.reviewCountTv.text = detail?.myProductResultDTO?.reviewCount.toString()    // 리뷰 개수


                    // 판매자 상세 주소 값 저장
                    detailAddr = detail?.myProductResultDTO?.detailLocation.toString()

                    // 지도 띄우기
                    mapView.getMapAsync { map ->
                        googleMap = map
                        getAddressFromLocationName(this, detailAddr)  // 상세 주로를 이용해 위도, 경도 얻는 함수 호출
                    }
                }
            })

            viewModel.getProductDetail()  // 제품 정보 요청 !!
        } ?: run {
            // productId가 null인 경우의 처리( Activity 종료 또는 오류 메시지 표시)
            Log.e(TAG, "제품 아이디 가져오기 실패")
        }

        binding.indicatorTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.icon = ContextCompat.getDrawable(this@MyItemDetailActivity, R.drawable.indicator_shape_blue)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.icon = ContextCompat.getDrawable(this@MyItemDetailActivity, R.drawable.indicator_shape_gray)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // 필요한 경우 여기에 추가 로직 구현
            }
        })

        // 리뷰 리사이클러 뷰
        reviewAdapter = MyItemReviewAdapter(ArrayList())  // 초기에는 빈 리스트로 초기화
        binding.reviewList.adapter = reviewAdapter
        binding.reviewList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        reviewViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ProductReviewViewModel::class.java)

        reviewViewModel.loadProductReviews(productId!!)

        // ViewModel에서 리뷰 목록을 관찰하고, 변경이 있을 때마다 어댑터에 설정
        reviewViewModel.reviews.observe(this, Observer { reviews ->
            reviewAdapter.setItems(ArrayList(reviews))  // 리뷰 목록을 어댑터에 설정
            reviewAdapter.notifyDataSetChanged() // 리뷰 데이터가 변경될 때 리사이클러 뷰를 업데이트
        })

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }

        // ViewModel 초기화
        deleteViewModel = ViewModelProvider(this, DeleteViewModelFactory(application, productId!!.toLong())).get(
            DeleteViewModel::class.java)

        // 제품 삭제
        binding.btnDelete.setOnClickListener {
            // 커스텀 다이얼로그 레이아웃을 인플레이트
            val dialogBinding = DialogCustomBinding.inflate(layoutInflater)
            val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .setView(dialogBinding.root)
                .create()

            dialogBinding.yes.setOnClickListener {   // 삭제를 누르면 제품 삭제
                if (productId > 0) {
                    deleteViewModel.deleteProduct()
                    alertDialog.dismiss()     // 다이얼로그 닫기
                } else {
                    Log.d(TAG, "삭제하고자 하는 제품 ID가 유효하지 않습니다. ")
                }
            }

            dialogBinding.no.setOnClickListener {   // 취소 누르면 다이얼로그 닫기
                alertDialog.dismiss()
            }

            alertDialog.show()
        }

        // 제품 삭제 결과 관찰
        deleteViewModel.deleteStatus.observe(this, Observer { isSuccess ->
            if (isSuccess) {    // 제품이 성공적으로 삭제된 경우 현재 액티비티 종료
                Log.d(TAG, "제품이 성공적으로 삭제됨 !!!!!")
                finish()
            } else {
                Log.d(TAG, "제품 삭제 실패 ,,,,,,")
                Toast.makeText(this, "제품 삭제 실패. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })

        // 대여자 프로필 클릭
        binding.detailProfileNameIv.setOnClickListener {
            val intent = Intent(this, Profile_Activity::class.java)
            startActivity(intent)
        }

        // 제품 수정하기 클릭
        binding.btnGoMyItemEdit.setOnClickListener {
            val intent = Intent(this, ProductEditActivity::class.java)
            intent.putExtra("productId", productId)
            startActivity(intent)
        }

    }

    fun getAddressFromLocationName(context: Context, name: String, maxResults: Int = 5) {
        // Geocoder 클래스를 사용
        val geocoder = Geocoder(context)
        try {
            Log.d("상세 주소 ", "input address : ${name}")

            // 전달받은 주소로 위치 정보를 조회(최대 5개까지)
            val addressList = geocoder.getFromLocationName(name, maxResults) ?: listOf()
            if (addressList.isNotEmpty()) {
                val address = addressList[0]   // 제일 처음 조회된 주소를 이용
                // 직접 위도와 경도를 변수에 할당
                val latitude : Double = address.latitude
                val longitude : Double = address.longitude

                // onMapReady 내에서 마커 위치 업데이트
                updateMap(latitude, longitude)
            } else {
                Log.d("Geocoding", "주소를 찾을 수 없습니다.")
                // 주소가 없다면 사용자에게 알림
                Toast.makeText(context, "No Address Found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Log.e("Geocoding", "Geocoder IOException: ${e.message}")
        }
    }

    fun updateMap(latitude: Double, longitude: Double) {
        val location = LatLng(latitude, longitude)   // 위도, 경도를 이용해 객체 생성
        googleMap?.addMarker(MarkerOptions().position(location))  // 해당 위치에 마커 추가,
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))

        googleMap?.uiSettings?.isZoomControlsEnabled = true   // 상세 화면 지도 확대/축소 버튼 활성화

    }

    // 지도가 사용 완료되었을 때 호출
    override fun onMapReady(p0: GoogleMap) {
        this.googleMap = p0

        val location = LatLng(37.5562,126.9724)  // 좌표는 서울 !
        googleMap?.addMarker(MarkerOptions().position(location).title("위치"))   // 초기 마커 위치 설정
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