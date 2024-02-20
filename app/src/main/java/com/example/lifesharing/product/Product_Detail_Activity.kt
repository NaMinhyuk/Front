package com.example.lifesharing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.lifesharing.databinding.ActivityProductDetailBinding
import com.example.lifesharing.product.Product_Detail_Reserve_Activity
import com.example.lifesharing.product.imageadapter.ImageAdapter
import com.example.lifesharing.product.model.slidedata
import com.example.lifesharing.profile.Profile_Activity
import com.example.lifesharing.service.work.DetailProduct
import net.daum.mf.map.api.MapView
import java.util.UUID


class Product_Detail_Activity : AppCompatActivity() {
    lateinit var kakaoMap : MapView
    val TAG :String = "로그"
    private lateinit var viewpager :ViewPager2
    private lateinit var pagechangeListener : ViewPager2.OnPageChangeCallback

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = DetailProduct()
        //API 연동
        viewModel.detailProductAPI()

        Log.d(TAG, "API 연동 확인: detailProduct() 호출됨")

        val binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewpager = binding.detailVieapager2

        val images = arrayListOf(
            slidedata(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.camera1
            ),
            slidedata(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.camera2
            ),
            slidedata(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.camera3
            ),
            slidedata(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.camera4
            ),
            slidedata(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + R.drawable.camera5
            )
        )
        val imageAdapter = ImageAdapter()
        viewpager.adapter = imageAdapter
        imageAdapter.submitList(images)



        val slideDotLL = binding.detailSlideDotLL
        val dotsImage = Array(images.size) {ImageView(this)}

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.non_active_dot
            )
            slideDotLL.addView(it,params)
        }

        dotsImage[0].setImageResource(R.drawable.acrive_dot)

        pagechangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView->
                    if(position ==index) {
                        imageView.setImageResource(
                            R.drawable.acrive_dot
                        )
                    }else {
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }
                }
                super.onPageSelected(position)
            }
        }
        viewpager.registerOnPageChangeCallback(pagechangeListener)



        binding.detailBackBtn.setOnClickListener{
            val intent = Intent(this, Product_Menu_Activity::class.java)
            startActivity(intent)
        }

        binding.detailBottomReserveBtn.setOnClickListener{
            val intent = Intent(this, Product_Detail_Reserve_Activity::class.java)
            startActivity(intent)
        }
        binding.detailChatBtnIv.setOnClickListener {
//            val intent = Intent(this, 채팅::class.jave)
            startActivity(intent)
        }

        var heartcheck :Int = 0
        binding.productDetailHeart.setOnClickListener {
            if (heartcheck == 0) {
                heartcheck = 1
                binding.productDetailHeart.setImageResource(R.drawable.detail_heart_ful)
            }
            else{
                heartcheck = 0
                binding.productDetailHeart.setImageResource(R.drawable.detail_heart)
            }
        }

        binding.detailMapLocationTv.setText("울산 무거동")
        binding.reviewCountTv.setText("99+")
        binding.detailProfileBackgroundIv.setOnClickListener {
            val intent = Intent(this, Profile_Activity::class.java)
            startActivity(intent)
        }


        /////// 체팅intent 로 이동///////
//        binding.detailChatBtnIv.setOnClickListener {
//            val intent = Intent(this, )
//        }


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




        Log.e(TAG, "onCreate: 여기가 맵 시작~!~!~!~!~", )
        val mapView = MapView(this)
        val map = binding.detailMap

//        map.addView(mapView)
        Log.e(TAG, "onCreate: 여기가 맵입니다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", )

    }


}
