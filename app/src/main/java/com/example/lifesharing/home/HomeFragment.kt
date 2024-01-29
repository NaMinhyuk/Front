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
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentHomeBinding
import com.example.lifesharing.home.home_data.NewRegistItemAdapter
import com.example.lifesharing.home.home_data.NewRegistItemData
import com.example.lifesharing.home.home_data.ProductViewModel
import com.example.lifesharing.search.SearchActivity

class HomeFragment: Fragment(){

    lateinit var binding: FragmentHomeBinding
    private lateinit var newRegistItemAdapter: NewRegistItemAdapter
    private lateinit var viewModel: ProductViewModel
    private lateinit var indicator0_iv_main: ImageView
    private lateinit var indicator1_iv_main: ImageView
    private lateinit var viewPager: ViewPager2

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
        bannerAdapter.addFragment(BannerFragment(R.drawable.home_banner_post))
        bannerAdapter.addFragment(BannerFragment(R.drawable.home_banner_bg_img))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // category
        val categoryAdapter = CategoryVPAdapter(this)
        val fragmentList = listOf(First_CategoryFragment(), Second_CategoryFragment()).toMutableList()

        binding.homeCategoryVp.adapter = categoryAdapter
        binding.homeCategoryVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        categoryAdapter.fragmentlist = fragmentList as ArrayList<Fragment>

        // initialize RecyclerView
        initRecycler()

        // Initialize viewPager
        viewPager = binding.homeCategoryVp

        // add dropdown menu
        setupFilterSpinner()

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.filteredProducts.observe(viewLifecycleOwner, Observer { products ->
            val newRegistItems = products.map { product ->
                // ProductResultDTO를 NewRegistItemData로 변환
                NewRegistItemData(
                    img = product.imageUrl ?: R.drawable.camara.toString(), // 이미지 URL이 null이면 기본 카메라 사진
                    location = product.location,
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

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
            }
        })

        return binding.root
    }

    private fun initRecycler() {
        newRegistItemAdapter = NewRegistItemAdapter(ArrayList())
        binding.newItemRv.adapter = newRegistItemAdapter
    }

    private fun setupFilterSpinner() {
        val filterSpinner = binding.filterSpinner

        val options = resources.getStringArray(R.array.filter_options)

        val guideText = "필터"
        val optionsWithGuide = listOf(guideText) + options.toList()

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.home_spinner_item_selected,  // 선택된 아이템을 보여줄 레이아웃
            R.id.spinner_title_tv,  // 레이아웃 안의 TextView ID
            optionsWithGuide
        )
        adapter.setDropDownViewResource(R.layout.home_spinner_item_dropdown) // 드롭다운 레이아웃
        filterSpinner.adapter = adapter

        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position == 0) {
                    // 가이드 문구가 선택되었으므로 여기에 대한 처리를 추가하세요.
                    // 예를 들어, 사용자에게 메시지를 표시하거나 아무 작업도 수행하지 않을 수 있습니다.
                } else {
                    // 실제 옵션을 선택한 경우 해당 옵션에 대한 처리를 수행합니다.
                    val selectedOption = options[position - 1] // 가이드 문구를 제외한 실제 옵션의 위치는 position - 1입니다.
                    handleFilterOption(selectedOption)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

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
}