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
import com.example.lifesharing.Product_Detail_Activity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ItemReProductMenuBinding

class MenuAdapter(private val context: Context) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    var datas = mutableListOf<ProductMenuData>()

    lateinit var binding : ItemReProductMenuBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_re_product_menu,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

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


        private val location : TextView = itemView.findViewById(R.id.location_text_tv)
        private val review : TextView = itemView.findViewById(R.id.review_tv)
        private val name : TextView = itemView.findViewById(R.id.name_tv)
        private val money_keep : TextView = itemView.findViewById(R.id.money_keep_tv)
        private val money_day : TextView = itemView.findViewById(R.id.money_day_tv)


        fun bind(item: ProductMenuData) {

            location.text = item.location
            review.text = item.review
            name.text = item.name
            money_keep.text = item.money_keep.toString()
            money_day.text = item.money_day.toString()
        }

    }
}