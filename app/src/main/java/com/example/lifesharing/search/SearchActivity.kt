package com.example.lifesharing.search

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    private fun initSearchView() {

        lateinit var binding : ActivitySearchBinding

        // init SearchView
        binding.searchBar.isSubmitButtonEnabled = true
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                // @TODO
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // @TODO
                return true
            }
        })
    }
}