package com.example.lifesharing.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lifesharing.Product_Menu_Activity
import com.example.lifesharing.databinding.FragmentFirstCategoryBinding

class First_CategoryFragment() : Fragment() {

    lateinit var binding: FragmentFirstCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstCategoryBinding.inflate(inflater, container, false)

        // 카테고리 별 제품 조회
        binding.homeCategoryDigitalIv.setOnClickListener{
            val intent = Intent(requireContext(), Product_Menu_Activity::class.java)
            intent.putExtra("category_name", "디지털기기")
            intent.putExtra("category_id", 1L)
            Log.d("CategoryFragment", "Sending categoryId: 1")
            startActivity(intent)
        }

        binding.homeCategorySoundIv.setOnClickListener{
            val intent = Intent(requireContext(), Product_Menu_Activity::class.java)
            intent.putExtra("category_name", "악기/음향")
            intent.putExtra("category_id", 2L)
            Log.d("CategoryFragment", "Sending categoryId: 2")
            startActivity(intent)
        }

        binding.homeCategoryGameIv.setOnClickListener{
            val intent = Intent(requireContext(), Product_Menu_Activity::class.java)
            intent.putExtra("category_name", "게임/취미")
            intent.putExtra("category_id", 3L)
            Log.d("CategoryFragment", "Sending categoryId: 3")
            startActivity(intent)
        }

        binding.homeCategoryCampingIv.setOnClickListener{
            val intent = Intent(requireContext(), Product_Menu_Activity::class.java)
            intent.putExtra("category_name", "레저/캠핑")
            intent.putExtra("category_id", 4L)
            Log.d("CategoryFragment", "Sending categoryId: 4")
            startActivity(intent)
        }

        binding.homeCategoryClothesIv.setOnClickListener{
            val intent = Intent(requireContext(), Product_Menu_Activity::class.java)
            intent.putExtra("category_name", "의류")
            intent.putExtra("category_id", 5L)
            Log.d("CategoryFragment", "Sending categoryId: 5")
            startActivity(intent)
        }

        binding.homeCategoryShoesIv.setOnClickListener{
            val intent = Intent(requireContext(), Product_Menu_Activity::class.java)
            intent.putExtra("category_name", "신발")
            intent.putExtra("category_id", 6L)
            Log.d("CategoryFragment", "Sending categoryId: 6L")
            startActivity(intent)
        }

        binding.homeCategoryKitchenIv.setOnClickListener{
            val intent = Intent(requireContext(), Product_Menu_Activity::class.java)
            intent.putExtra("category_name", "생활/주방")
            intent.putExtra("category_id", 7L)
            Log.d("CategoryFragment", "Sending categoryId: 7L")
            startActivity(intent)
        }

        binding.homeCategoryFurnitureIv.setOnClickListener{
            val intent = Intent(requireContext(), Product_Menu_Activity::class.java)
            intent.putExtra("category_name", "가구")
            intent.putExtra("category_id", 8L)
            Log.d("CategoryFragment", "Sending categoryId: 8L")
            startActivity(intent)
        }

        return binding.root
    }
}