package com.example.lifesharing.search

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivitySearchBinding

class SearchActivity() : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

//    private fun initSearchView() {
//        // init SearchView
//        binding.search.isSubmitButtonEnabled = true
//        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // @TODO
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // @TODO
//                return true
//            }
//        })
//    }
}