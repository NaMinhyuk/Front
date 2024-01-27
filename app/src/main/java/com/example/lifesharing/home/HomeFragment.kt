package com.example.lifesharing.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentHomeBinding
import com.example.lifesharing.home.home_data.BannerVPAdapter
import com.example.lifesharing.home.home_data.CategoryVPAdapter
import com.example.lifesharing.home.home_data.NewRegistItemAdapter
import com.example.lifesharing.home.home_data.NewRegistItemData
import com.example.lifesharing.home.home_data.ProductViewModel

class HomeFragment: Fragment(){

    lateinit var binding: FragmentHomeBinding
    private lateinit var newRegistItemAdapter: NewRegistItemAdapter
    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

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
        categoryAdapter.fragmentlist = fragmentList as ArrayList<Fragment>
        binding.homeCategoryVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // initialize RecyclerView
        initRecycler()

        // add dropdown menu
        setupFilterSpinner()

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        return binding.root
    }

    private fun initRecycler() {
        newRegistItemAdapter = NewRegistItemAdapter(ArrayList())
        binding.newItemRv.adapter = newRegistItemAdapter

        val items = ArrayList<NewRegistItemData>().apply {
            add(NewRegistItemData(img = R.drawable.camara, location = "울산 무거동", review = "(100)", name = "카메라", deposit = 500000, a_day_fee = 10000))
            add(NewRegistItemData(img = R.drawable.camara, location = "울산 삼산", review = "(0)", name = "카메라", deposit = 500000, a_day_fee = 10000))
        }

        newRegistItemAdapter.setItems(items)
    }

    private fun setupFilterSpinner() {
        val filterSpinner = binding.filterSpinner

        val options = resources.getStringArray(R.array.filter_options)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterSpinner.adapter = adapter

        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedOption = options[position]
                handleFilterOption(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun handleFilterOption(selectedOption: String) {
        when (selectedOption) {
            "최신순" -> {
                viewModel.filterProductsBy("recent")
            }
            "인기순" -> {
                viewModel.filterProductsBy("popular")
            }
            "리뷰순" -> {
                viewModel.filterProductsBy("review")
            }
        }
    }
}