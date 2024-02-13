package com.example.lifesharing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.product.data.MenuAdapter
import com.example.lifesharing.databinding.ActivityProductMenuBinding
import com.example.lifesharing.product.data.ProductMenuData

class Product_Menu_Activity : AppCompatActivity() {

    lateinit var menuAdapter: MenuAdapter
    val datas = mutableListOf<ProductMenuData>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProductMenuBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            finish()
        }

        menuAdapter = MenuAdapter(this)
        binding.productMenuRv.adapter = menuAdapter




        datas.apply {
            add(ProductMenuData(image = R.drawable.camara,location = "울산 무거동", review = "(100)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))
            add(ProductMenuData( image = R.drawable.camara,location = "울산 삼산", review = "(0)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))
            add(ProductMenuData(image = R.drawable.camara,location = "울산 삼산", review = "(0)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))
            add(ProductMenuData(image = R.drawable.camara,location = "울산 삼산", review = "(0)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))
            add(ProductMenuData(image = R.drawable.camara,location = "울산 삼산", review = "(0)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))


            menuAdapter.datas = datas
            menuAdapter.notifyDataSetChanged()

        }
    }
}