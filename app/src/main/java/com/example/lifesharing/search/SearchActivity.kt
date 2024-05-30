package com.example.lifesharing.search

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivitySearchBinding
import com.example.lifesharing.home.HomeFragment
import com.example.lifesharing.product.Product_Detail_Activity
import com.example.lifesharing.search.model.response_body.SearchResult
import com.example.lifesharing.search.viewModel.SearchViewModel
import com.kakao.vectormap.mapwidget.component.Horizontal
import com.kakao.vectormap.mapwidget.component.Orientation

/** 검색 View */
class SearchActivity : AppCompatActivity(), SearchClickListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel : SearchViewModel   // 데이터와 UI 로직을 분리하기 위한 ViewModel
    private lateinit var recyclerView: RecyclerView          // 검색 결과를 표시할 RecyclerView
    private lateinit var adapter : SearchListAdapter         // RecyclerView에서 사용할 어댑터
    private lateinit var spinner: Spinner

    private var hasSearched = false // 검색 실행 여부를 추적하는 변수

    override fun onCreate(savedInstanceState: Bundle?){
        binding = ActivitySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        // 뒤로가기
        binding.searchBackIv.setOnClickListener {
            finish()
        }

        // RecyclerView 및 어댑터를 설정, 레이아웃 매니저를 지정
        recyclerView = binding.searchRecycler

        val searchList = ArrayList<SearchResult>()
        adapter = SearchListAdapter(searchList, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 필터 스피너 설정
        setUpSpinner()


        // ViewModel 초기화
        searchViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(SearchViewModel::class.java)

        // LiveData 객체의 변경을 관찰-> 데이터가 변경될 때마다 Observer 블록 내의 코드를 실행
        searchViewModel.searchResults.observe(this, Observer { results ->
            // 검색이 실행된 후 결과를 처리
            if (hasSearched) {
                if (!results.isNullOrEmpty()) {
                    // RecyclerView의 어댑터에 새 데이터를 설정하고 UI를 업데이트
                    adapter.setItems(ArrayList(results))
                    binding.searchResultCount.text = adapter.itemCount.toString()
                    binding.searchNotificationBox.visibility = View.GONE

                } else {
                    // 검색 결과가 없는 경우, 적절한 메시지 표시
                    adapter.setItems(ArrayList()) // 리스트를 비워서 어댑터를 초기화
                    binding.searchNotificationBox.visibility = View.VISIBLE     // 검색 결과 없음 알림 표시
                    binding.keywordTv.text = "'" + binding.searchTv.text.toString() + "'"
                    binding.searchResultCount.text = "0"   // 검색 결과 수 0으로 설정
                }
            }
        })

        // 검색 버튼 클릭 시 서버에 검색 요청
        binding.btnSearch.setOnClickListener {
            hasSearched = true // 검색이 시작됨
            binding.searchNotificationBox.visibility = View.GONE // 검색 시작 전에 메시지 숨김
            searchViewModel.getSearchProduct(keyword = binding.searchTv.text.toString(), "recent")
        }

    }

    private fun setUpSpinner() {
        spinner = binding.searchFilterSpinner

        // ArrayAdapter를 생성
        ArrayAdapter.createFromResource(
            this,
            R.array.filter_options,    // 사용자가 선택할 수 있는 필터 옵션이 저장된 문자열 배열 리소스
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // 스피너 클릭 시 표시되는 드롭다운 목록 뷰 설정
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter   // 위에서 설정한 어댑터를 스피너에 연결
        }
        // 스피너의 가장 기본 값을 지정
        spinner.setSelection(0) // 최신순이 기본값

        // 사용자에게 보이는 값과 서버 요청 값의 매핑
        val filterMap = mapOf(
            "최신순" to "recent",
            "인기순" to "popular",
            "리뷰순" to "review"
        )

        // 선택된 필터에 따라 검색 조건을 변경하여 검색 결과를 업데이트
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val userSelection = parent.getItemAtPosition(position).toString()
                val filter = filterMap[userSelection] ?: "recent" // 매핑된 값이 없다면 기본값 "recent"

                val keyword = binding.searchTv.text.toString()    // 입력된 검색 키워드
                // 스피너 선택에 따라 검색 결과를 재요청
                searchViewModel.getSearchProduct(keyword, filter)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onItemClick(searchItem: SearchResult) {
        Log.d("PRODUCT ID", "${searchItem.productId}")
        val intent = Intent(this, Product_Detail_Activity::class.java)
        intent.putExtra("productId", searchItem.productId)
        startActivity(intent)
    }

}