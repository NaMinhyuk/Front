package com.example.lifesharing.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lifesharing.product.data.MenuAdapter
import com.example.lifesharing.databinding.ActivityProductMenuBinding
import com.example.lifesharing.product.Product_Detail_Activity
import com.example.lifesharing.product.api.CategoryItem
import com.example.lifesharing.product.interfaces.CategoryItemClickListener
import com.example.lifesharing.product.view_model.CategoryViewModel

/** 카테고리별 제품 화면 */
class Product_Menu_Activity : AppCompatActivity(), CategoryItemClickListener {

    private lateinit var binding: ActivityProductMenuBinding
    private lateinit var viewModel: CategoryViewModel    // CategoryViewModel 클래스의 인스턴스 생성

    lateinit var menuAdapter: MenuAdapter    // 리사이클러뷰 어댑터 인스턴스 생성

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityProductMenuBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기
        binding.backButton.setOnClickListener{
            finish()   // 액티비티 종료
        }

        // 카테고리 타이틀 설정 - 홈에서 선택된 카테고리 문자열 get
        binding.title.text = intent.getStringExtra("category_name").toString()

        // 카테고리 식별자 설정
        val categoryId = intent.getLongExtra("category_id", -1L)
        Log.d("ProductMenuActivity", "Received categoryId: $categoryId")

        // 제품 리사이클러뷰 초기화 메서드 호출
        initRecycler()


        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModel.categoryProducts.observe(this, Observer { products ->    // 카테고리별 제품 조회 결과 관칠
            menuAdapter.setItems(ArrayList(products))    // 제품 리스트를 얻어 어댑터를 업데이트
            ifEmptyItemList(products.size)               // 제품 목록이 비어 있는지 확인
        })

        // 카테고리 ID로 제품 목록 요청
        viewModel.loadCategoryProducts(categoryId)
    }

    // 제품 목록이 비어 있을 때와 아닐 때의 UI 설정 메서드
    private fun ifEmptyItemList(itemCount : Int) {
        // 해당 카테고리에 아이템이 존재한다면 리사이클러뷰 보이기
        if (itemCount > 0) {
            binding.menuRv.visibility = View.VISIBLE
            binding.notificationText.visibility = View.GONE
        }
        // 해당 카테고리에 아이템이 없으면 텍스트 표시
        else {
            binding.menuRv.visibility = View.INVISIBLE
            binding.notificationText.visibility = View.VISIBLE
        }
    }

    private fun initRecycler() {
        menuAdapter = MenuAdapter(ArrayList(), this)
        binding.menuRv.adapter = menuAdapter
        binding.menuRv.layoutManager = GridLayoutManager(this, 2)
    }

    // 새로운 인텐트가 있을 때 처리하는 메서드
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        // category ID 를 이용해 제품 목록을 재요청
        val categoryId = intent!!.getLongExtra("category_id", 1)
        viewModel.loadCategoryProducts(categoryId)
    }

    // 제품 클릭 이벤트 처리 메서드
    override fun onItemClick(categoryItem: CategoryItem) {
        val intent = Intent(this, Product_Detail_Activity::class.java)
        intent.putExtra("productId", categoryItem.productId) // 상세 화면 전환 시 필요한 제품 식별자
        startActivity(intent)
    }
}