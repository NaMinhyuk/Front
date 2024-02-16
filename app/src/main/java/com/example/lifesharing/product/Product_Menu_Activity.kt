package com.example.lifesharing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.product.data.MenuAdapter
import com.example.lifesharing.databinding.ActivityProductMenuBinding
import com.example.lifesharing.product.data.ProductMenuData
import com.example.lifesharing.service.work.DetailProduct
import com.example.lifesharing.service.work.MenuProduct

class Product_Menu_Activity : AppCompatActivity() {

    lateinit var menuAdapter: MenuAdapter
    val datas = mutableListOf<ProductMenuData>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProductMenuBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.backButton.setOnClickListener{
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        menuAdapter = MenuAdapter(this)
        binding.productMenuRv.adapter = menuAdapter

        val viewModel = MenuProduct()

        //API 연동
        viewModel.menuProductAPI()
        menuProductAPICALL()




        datas.apply {
            add(ProductMenuData(image = R.drawable.camara,location = "이건아니야", review = "(100)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))
            add(ProductMenuData( image = R.drawable.camara,location = "살려줘", review = "(0)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))
            add(ProductMenuData(image = R.drawable.camara,location = "울산 북구", review = "(0)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))
            add(ProductMenuData(image = R.drawable.camara,location = "울산 삼산", review = "(0)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))
            add(ProductMenuData(image = R.drawable.camara,location = "울산 삼산", review = "(0)", name = "Canon [렌즈 포함] EOS R8CanonCanon [렌즈.." , money_keep = 500000, money_day = 10000))


            menuAdapter.datas = datas
            menuAdapter.notifyDataSetChanged()

        }
    }
    fun menuProductAPICALL() {
        val retrofitWork = MenuProduct()
        retrofitWork.menuProductAPI()
    }
}