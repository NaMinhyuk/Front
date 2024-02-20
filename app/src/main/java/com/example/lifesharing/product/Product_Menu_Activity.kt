package com.example.lifesharing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lifesharing.product.data.MenuAdapter
import com.example.lifesharing.databinding.ActivityProductMenuBinding
import com.example.lifesharing.product.data.ProductMenuData
import com.example.lifesharing.product.model.response_body.ProductMenuResponseBody
import com.example.lifesharing.product.model.response_body.ProductMenuResultDTO
import com.example.lifesharing.service.work.MenuProduct
import okhttp3.ResponseBody
import java.util.ArrayList

class Product_Menu_Activity : AppCompatActivity() {

    lateinit var menuAdapter: MenuAdapter
    val datas = mutableListOf<ProductMenuData>()
    val TAG:String = "로그"

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

        //api 연동
        menuProductAPICALL()



        var menuDataToAPI :ArrayList<ProductMenuResultDTO> =
            GlobalApplication.getMenuDetailProductDataList()

        menuAdapter.submitList(menuDataToAPI)

        Log.d(TAG, "menudatatoapi $menuDataToAPI")

        binding.productMenuRv.apply {
            layoutManager = GridLayoutManager(this@Product_Menu_Activity,2)
            adapter = menuAdapter

        }

    }
    fun menuProductAPICALL() {
        val retrofitWork = MenuProduct()
        retrofitWork.menuProductAPI()
    }
}