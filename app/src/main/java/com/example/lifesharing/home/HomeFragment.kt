package com.example.lifesharing.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.lifesharing.Product_Detail_Activity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentHomeBinding
import com.example.lifesharing.home.home_data.HomeProductClickListener
import com.example.lifesharing.home.home_data.NewRegistItemAdapter
import com.example.lifesharing.home.home_data.NewRegistItemData
import com.example.lifesharing.home.viewModel.ProductViewModel
import com.example.lifesharing.search.SearchActivity

class HomeFragment: Fragment(), HomeProductClickListener{

    lateinit var binding: FragmentHomeBinding
    private lateinit var newRegistItemAdapter: NewRegistItemAdapter   // 리사이클러뷰 어댑터 인스턴스 생성
    private lateinit var viewModel: ProductViewModel
    private lateinit var indicator0_iv_main: ImageView
    private lateinit var indicator1_iv_main: ImageView
    private lateinit var viewPager: ViewPager2   // ViewPager2 클래스의 인스턴스 생성

    val TAG:String = "로그"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // button connection
        binding.homeSearchIc.setOnClickListener{
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        // button connection (알람 아이콘)
        /*
        binding.homeAlarmIc.setOnClickListener{
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
        */

        // banner
        val bannerAdapter = BannerVPAdapter(this)
        // 배너 프래그먼트 추가
        bannerAdapter.addFragment(BannerFragment.newInstance(R.drawable.home_banner_post))
        bannerAdapter.addFragment(BannerFragment.newInstance(R.drawable.home_banner_bg_img))

        // 배너 뷰페이저 어댑터 설정
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL   // 배너 뷰페이저 방향 설정 - 수평

        // category
        val categoryAdapter = CategoryVPAdapter(this)
        // 카테고리 프래그먼트를 리스트로 추가하여 뷰페이저에 설정
        val fragmentList = listOf(First_CategoryFragment(), Second_CategoryFragment()).toMutableList()

        // 카테고리 뷰페이저 어댑터 설정
        binding.homeCategoryVp.adapter = categoryAdapter
        binding.homeCategoryVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL  // 배너 뷰페이저 방향 설정 - 수평
        categoryAdapter.fragmentlist = fragmentList as ArrayList<Fragment>      // 프래그먼트 리스트 설정

        // 제품을 담을 리사이클러뷰 초기화
        initRecycler()

        // 카테고리 뷰페이저 초기화
        viewPager = binding.homeCategoryVp

        // 드롭다운 스피터 메뉴 설정 함수 호출
        setupFilterSpinner()

        // 홈화면 제품 조회 ViewModel초기화
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        // 조회된 제품 리스트를 관찰
        viewModel.filteredProducts.observe(viewLifecycleOwner, Observer { products ->
            val newRegistItems = products.map { product ->
                // ProductResultDTO를 NewRegistItemData로 변환
                NewRegistItemData(
                    productId = product.productId,
                    img = product.imageUrl ?: R.drawable.camara.toString(), // 이미지 URL이 null이면 기본 카메라 사진
                    location = product.location,
                    score = product.score,
                    reviewCount = product.reviewCount,
                    name = product.name,
                    deposit = product.deposit,
                    dayPrice = product.dayPrice
                )
            }
            newRegistItemAdapter.setItems(ArrayList(newRegistItems)) // 변환된 데이터로 RecyclerView를 업데이트
        })

        // indicator
        indicator0_iv_main = binding.indicator0IvMain
        indicator1_iv_main = binding.indicator1IvMain

        // 페이지 변경 콜백 설정
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
            }
        })

        return binding.root
    }

    // 제품 조회 리사이클러뷰 초기화 메소드
    private fun initRecycler() {
        newRegistItemAdapter = NewRegistItemAdapter(ArrayList(), this)
        binding.newItemRv.adapter = newRegistItemAdapter
    }

    // 제품 필터링 스피너 설정 메소드
    private fun setupFilterSpinner() {
        val filterSpinner = binding.filterSpinner

        val options = resources.getStringArray(R.array.filter_options)    // 필터 옵션 배열 가져오기

        // 스피너 어댑터 설정
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)   // 스피너에 들어갈 아이템 설정(최신순, 인기순, 리뷰순)
        filterSpinner.adapter = adapter

        // 스피너 아이템 선택 리스너 설정
        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            // 스피너 아이템이 선택된 경우
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = options[position]    // 스피너에서 선택된 아이템 문자열 저장
                handleFilterOption(selectedOption)        // 선택된 아이템을 적용할 필터링 메서드 호출
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    // 필터링 옵션 처리 메서드
    private fun handleFilterOption(selectedOption: String) {
        when (selectedOption) {
            "최신순" -> {
                Log.d(TAG, "handleFilterOption: ")
                viewModel.getFilteredProduct("recent")
            }
            "인기순" -> {
                viewModel.getFilteredProduct("popular")
            }
            "리뷰순" -> {
                viewModel.getFilteredProduct("review")
            }
        }
    }

    private fun updateIndicators(position: Int) {
        when (position) {
            0 -> {
                indicator0_iv_main.setImageResource(R.drawable.indicator_shape_blue)
                indicator1_iv_main.setImageResource(R.drawable.indicator_shape_gray)
            }

            1 -> {
                indicator0_iv_main.setImageResource(R.drawable.indicator_shape_gray)
                indicator1_iv_main.setImageResource(R.drawable.indicator_shape_blue)
            }
        }
    }

    // 제품 클릭 시 화면 전환
    override fun onItemClick(position: Int) {
        val item = newRegistItemAdapter.getItem(position)
        val intent = Intent(requireContext(), Product_Detail_Activity::class.java).apply {
            putExtra("productId", item.productId) // productId를 전달
        }
        startActivity(intent)
    }
}