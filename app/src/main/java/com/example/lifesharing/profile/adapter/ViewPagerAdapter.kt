package com.example.lifesharing.profile.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lifesharing.profile.UserRegistFragment
import com.example.lifesharing.profile.UserRentFragment
import com.example.lifesharing.profile.UserReviewFragment

class ViewPagerAdapter (fragmentActivity: FragmentActivity, private val sellerId: Long?): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment =  when(position){
            0 -> UserRegistFragment()
            1 -> UserRentFragment()
            else -> UserReviewFragment()
        }

        fragment.arguments = Bundle().apply {
            putLong("sellerId", sellerId ?: -1)  // -1 or another value as a default
        }
        return fragment
    }
}