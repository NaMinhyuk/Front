package com.example.lifesharing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifesharing.databinding.ActivityMainBinding
import com.example.lifesharing.home.HomeFragment
import com.example.lifesharing.messenger.MessengerFragment
import com.example.lifesharing.mypage.MyPageMainFragment
import com.example.lifesharing.regist.RegistrationFragment
import com.example.lifesharing.reservation.ReservationFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initBottomNavigation()

    }

    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBottomNavi.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, HomeFragment())
                        .commitAllowingStateLoss()
                    // 아이콘 변경
                    item.setIcon(R.drawable.btm_home_select_ic)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.reservationFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, ReservationFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_reserve_select_ic)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.registerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, RegistrationFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_regist_select_ic)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.messengerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, MessengerFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_messenger_select_ic)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, MyPageMainFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_my_page_ic)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }
}