package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.mypage.mypage_data.WishListAdapter
import com.example.lifesharing.mypage.mypage_data.WishListData


// Wish List (찜 목록)
class WishListActivity  : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WishListAdapter
    val data = mutableListOf<WishListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_list)

        val backIv = findViewById<ImageView>(R.id.wish_back_iv)

        backIv.setOnClickListener {
            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.wish_item_rv)

        val WishListItem = ArrayList<WishListData>() // 데이터 리스트 준비

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
    }
}