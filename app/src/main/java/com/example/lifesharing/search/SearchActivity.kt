package com.example.lifesharing.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivitySearchBinding
import com.example.lifesharing.home.HomeFragment


class SearchActivity : AppCompatActivity() {
    val TAG:String = "로그"


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        Log.d(TAG, "onCreateOptionsMenu: 시작?")

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            //검색가능한 구성을 searchview와 연결
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)
        Log.d(TAG, "onCreate 시작?")

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent 시작부분")

        if (intent != null) {
            handleIntent(intent)
        }
    }


    private fun handleIntent(intent: Intent){
        Log.d(TAG, "handleIntent 시작부분 ")
        if(Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.d(TAG, "handleIntent if문 안")
        }
    }
}