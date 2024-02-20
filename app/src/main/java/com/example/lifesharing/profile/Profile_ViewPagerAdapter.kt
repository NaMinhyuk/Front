package com.example.lifesharing.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lifesharing.profile.profileFragment.Profile_Fragment_Page1
import com.example.lifesharing.profile.profileFragment.Profile_Fragment_Page2
import com.example.lifesharing.profile.profileFragment.Profile_Fragment_Page3

class Profile_ViewPagerAdapter(fragmentActivity : FragmentActivity): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int =3
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0->Profile_Fragment_Page1()
            1->Profile_Fragment_Page2()
            else -> Profile_Fragment_Page3()
        }
    }


}