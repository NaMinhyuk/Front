package com.example.lifesharing.product.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R

// 제품 이미지 리스트를 ViewPager에 보이도록 하는 Adapter
class ImageViewPagerAdapter(private val context: Context, private val images: List<String>) :
    RecyclerView.Adapter<ImageViewPagerAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_image_viewpager_item, parent, false)
        return ImageViewHolder(view)
    }

    // RecyclerView에 표시될 데이터를 ViewHolder와 연결(bind)
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // Glide 라이브러리를 사용하여 각 ImageView에 이미지 URL에서 로드한 이미지를 설정
        Glide.with(context).load(images[position]).into(holder.imageView)
    }

    //이미지 URL 목록의 크기가 반환
    override fun getItemCount(): Int = images.size   // 이 값은 RecyclerView가 스크롤 범위와 페이지 수를 결정하는 데 사용
}