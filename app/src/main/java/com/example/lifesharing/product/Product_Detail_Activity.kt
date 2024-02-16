package com.example.lifesharing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.lifesharing.databinding.ActivityProductDetailBinding
import com.example.lifesharing.product.Product_Detail_Reserve_Activity
import com.example.lifesharing.product.data.DeetailReviewItemData
import com.example.lifesharing.product.data.DetailProductItemData
import com.example.lifesharing.regist.model.request_body.ProductRegisterRequestBody
import com.example.lifesharing.service.work.DetailProduct
import com.kakao.vectormap.MapView


class Product_Detail_Activity : AppCompatActivity() {
    lateinit var kakaoMap : MapView
    val TAG :String = "로그"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = DetailProduct()

        //API 연동
        viewModel.detailProductAPI()

        Log.d(TAG, "API 연동 확인: detailProduct() 호출됨")

        val binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailBackBtn.setOnClickListener{
            val intent = Intent(this, Product_Menu_Activity::class.java)
            startActivity(intent)
        }

        binding.detailBottomReserveBtn.setOnClickListener{
            val intent = Intent(this, Product_Detail_Reserve_Activity::class.java)
            startActivity(intent)
        }


        /* fragment일때 mutable live data 접근
        viewModel.DetailProductList.observe(this,Observer { products ->
            val newDetailProducts = products.map { product ->
                DetailProductItemData(
                    productId = product.productId,
                    userId = product.userId,
                    categoryList = product.categoryList,
                    imageUrl = product.imageUrl,
                    name = product.name,
                    score = product.score,
                    reviewCount = product.reviewCount,
                    deposit = product.deposit,
                    dayPrice = product.dayPrice,
                    hourPrice = product.hourPrice,
                    isLiked = product.isLiked,
                    content = product.content,
                    userNickname = product.userNickname,
                    userImage = product.userImage,
                )
            }
        })
        viewModel.DetailReviewList.observe(this,Observer{products->
            val newDetailReviews = products.map{ product ->
                DeetailReviewItemData(
                    reviewId = product.reviewId,
                    userId = product.reviewId,
                    nickName = product.nickName,
                    profileUrl =product.profileUrl ,
                    createdAt = product.createdAt,
                    lentDay =product.lentDay,
                    imageList = product.imageList,
                    score = product.score,
                    content = product.content
                )
            }
        })
        */



        val mapView = MapView(this)
//        binding.detailMap.addView(mapView)


//        // Kakao MapView 초기화
//        val mapView = binding.detailMap
//        kakaoMap = binding.detailMap// 지도 객체 생성
//        // 필요한 설정 및 사용
//
//        mapView.start(object : MapLifeCycleCallback() {
//            override fun onMapDestroy() {
//                // 지도 API 가 정상적으로 종료될 때 호출됨
//            }
//
//            override fun onMapError(error: Exception) {
//                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
//            }
//        }, object : KakaoMapReadyCallback() {
//            override fun onMapReady(kakaoMap: KakaoMap) {
//                Log.d(TAG, "kakao map debug"); // 디버그용 로그
//                // 인증 후 API 가 정상적으로 실행될 때 호출됨
//            }
//        })

//        kakaoMap.setOnMapViewInfoChangeListener(object : OnMapViewInfoChangeListener {
//            override fun onMapViewInfoChanged(mapViewInfo: MapViewInfo) {
//                // MapViewInfo 변경 성공 시 호출
//            }
//
//            override fun onMapViewInfoChangeFailed() {
//                // MapViewInfo 변경 실패 시 호출
//            }
//        })
//
//        kakaoMap.changeMapViewInfo(MapViewInfo.from("openmap", MapType.NORMAL))



//        vpMainBanner.adapter = RecyclerViewAdapter()
//        TabLayoutMediator(
//            tabMainBanner,
//            vpMainBanner
//        )
//        { tab, position ->
//            vpMainBanner.setCurrentItem(tab.position)
//        }.attach()
    }
}
