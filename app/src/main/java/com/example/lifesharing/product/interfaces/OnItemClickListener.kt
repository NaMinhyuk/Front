package com.example.lifesharing.product.interfaces

import com.example.lifesharing.product.data.MyItemData

interface OnItemClickListener {
    fun onItemClicked(position: Int, item: MyItemData)
}