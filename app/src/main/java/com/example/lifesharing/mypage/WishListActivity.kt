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
class WishListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WishListAdapter
    private val viewWishList = ViewWishList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)

//        // RecyclerView 초기화
//        recyclerView = findViewById(R.id.wish_item_rv)
//
//        adapter = WishListAdapter(ArrayList())
//
//        recyclerView.layoutManager = GridLayoutManager(this, 2)
//
//        // 어댑터에 데이터 변경을 알리고 갱신
//        adapter.notifyDataSetChanged()
//
//        val backIv = findViewById<ImageView>(R.id.wish_back_iv)
//
//        backIv.setOnClickListener {
//            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
//            val intent = Intent(this, MyPageActivity::class.java)
//            startActivity(intent)
//        }
//        recyclerView.adapter = adapter

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
}

