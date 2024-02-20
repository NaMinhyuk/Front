package com.example.lifesharing.product.data

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.Product_Detail_Activity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ItemReProductMenuBinding
import com.example.lifesharing.product.model.response_body.ProductMenuResultDTO
import com.example.lifesharing.product.model.response_body.ProductMenuResultDTOList

class MenuAdapter(private val context: Context) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    lateinit var binding : ItemReProductMenuBinding

    private var menuItemList = ArrayList<ProductMenuResultDTO>()

    fun submitList(itemList : ArrayList<ProductMenuResultDTO>) {
        this.menuItemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_re_product_menu,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = menuItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.menuItemList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, Product_Detail_Activity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {

            }
        }

        fun bind(item: ProductMenuResultDTO) {
            var productId = item.productId
            var name = item.name
            var location = item.location
            var deposit = item.deposit
            var dayPrice = item.dayPrice
            var score = item.score
            var reviewCount = item.reviewCount
            var image_url = item.imageUrl

            /*
            with(context)
            load(가져올이미지)
            placeholder(미리보여줄이미지)
            fallback(이미지 url null인경우 보여줄 이미지
            error (error일때 보여줄 이미지
            into(image를 보여줄 view)
             */
//            Glide.with(GlobalApplication.instence).load()


        }

//        fun bind(reviewListDTOList: ReviewListDTOList) {
//            nickname.text = reviewListDTOList.nickName
//            date.text = reviewListDTOList.createdAt
//            rentDay.text = reviewListDTOList.lentDay
//            // score 에 따라 다르게 해야할듯
//            content.text = reviewListDTOList.content
//
//            Glide
//                .with(GlobalApplication.instance)
//                .load(reviewListDTOList.profileUrl)
//                .centerCrop()
//                .placeholder(R.drawable.profile)
//                .into(userProfile)
//
//            Glide
//                .with(GlobalApplication.instance)
//                .load(reviewListDTOList.imageList)
//                .placeholder(R.drawable.camera_dummy)
//                .into(itemImg)
//        }


    }
}