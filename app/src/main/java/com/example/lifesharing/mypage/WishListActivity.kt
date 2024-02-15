package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.mypage.mypage_api.ViewWishList
import com.example.lifesharing.mypage.mypage_data.WishListAdapter
import com.example.lifesharing.mypage.mypage_data.WishListData


// Wish List (찜 목록)
class WishListActivity  : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WishListAdapter
    private val viewWishList = ViewWishList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.wish_item_rv)

        adapter = WishListAdapter(ArrayList())
        recyclerView.adapter = adapter

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // 어댑터에 데이터 변경을 알리고 갱신
        adapter.notifyDataSetChanged()

        val backIv = findViewById<ImageView>(R.id.wish_back_iv)

        backIv.setOnClickListener {
            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.wish_item_rv)
        recyclerView.adapter = adapter

        // api를 통해 데이터 받아오기
        val viewWishList = ViewWishList()
        val wishListAdapter = WishListAdapter(ArrayList())

        viewWishList.getWishList(onSuccess = { heartResults ->
            val wishListItems = heartResults.map { heartResult ->
                // HeartResult를 WishListData로 변환
                WishListData(
                    img = heartResult.image_url ?: R.drawable.camera.toString(),
                    location = heartResult.location,
                    reviewCount = heartResult.reviewCount,
                    name = heartResult.name,
                    deposit = heartResult.deposit,
                    dayPrice = heartResult.dayPrice
                )
            }
            wishListAdapter.setItems(ArrayList(wishListItems)) // 변환된 데이터로 RecyclerView를 업데이트
        },
            onFailure = { errorMessage ->
                Toast.makeText(this@WishListActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
    }

        /*val WishListItem = ArrayList<WishListData>() // 데이터 리스트 준비

        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 무거동", reviewCount = "(100)", name = "카메라" , deposit = 500000, dayPrice = 10000))
        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 삼산", reviewCount = "(0)", name = "카메라" , deposit = 500000, dayPrice = 10000))
        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 삼산", reviewCount = "(0)", name = "카메라" , deposit = 500000, dayPrice = 10000))
        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 삼산", reviewCount = "(0)", name = "카메라" , deposit = 500000, dayPrice = 10000))
        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 삼산", reviewCount = "(0)", name = "카메라" , deposit = 500000, dayPrice = 10000))
        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 삼산", reviewCount = "(0)", name = "카메라" , deposit = 500000, dayPrice = 10000))
        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 삼산", reviewCount = "(0)", name = "카메라" , deposit = 500000, dayPrice = 10000))
        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 삼산", reviewCount = "(0)", name = "카메라" , deposit = 500000, dayPrice = 10000))
        WishListItem.add(WishListData(img = R.drawable.camara, location = "울산 삼산", reviewCount = "(0)", name = "카메라" , deposit = 500000, dayPrice = 10000))



        // 어댑터 생성 및 설정
        adapter = WishListAdapter(WishListItem)
        recyclerView.adapter = adapter

        // 리사이클러뷰 레이아웃 매니저 설정
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // 어댑터에 데이터 변경을 알리고 갱신
        adapter.notifyDataSetChanged()
    }*/
}