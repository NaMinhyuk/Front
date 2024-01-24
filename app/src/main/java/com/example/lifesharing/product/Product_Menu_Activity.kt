package com.example.lifesharing.product

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.MainActivity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityProductMenuBinding
import com.example.lifesharing.product_data.MenuAdapter
import com.example.lifesharing.product_data.MenuData

class Product_Menu_Activity : AppCompatActivity() {

    lateinit var menuAdapter: MenuAdapter
    val datas = mutableListOf<MenuData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_menu)

        val binding = ActivityProductMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        initRecycler()
    }

    private fun initRecycler() {
        val binding = ActivityProductMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuAdapter = MenuAdapter(this)
        binding.menuRv.adapter = menuAdapter


        datas.apply {
            add(MenuData(img = R.drawable.camara, location = "울산 무거동", review = "(100)", name = "카메라" , money_keep = 500000, money_day = 10000))
            add(MenuData(img = R.drawable.camara, location = "울산 삼산", review = "(0)", name = "카메라" , money_keep = 500000, money_day = 10000))


            menuAdapter.datas = datas
            menuAdapter.notifyDataSetChanged()

        }
    }
}