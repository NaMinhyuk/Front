package com.example.lifesharing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bottom.messenger.MessengerFragment
import com.example.bottom.regist.RegistrationFragment
import com.example.bottom.reservation.ReservationFragment
import com.example.lifesharing.databinding.ActivityMainBinding
import com.example.lifesharing.home.HomeFragment
import com.example.lifesharing.mypage.MyPageMainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNavigation()

    }

    // bottom navigation
    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, HomeFragment())
            .commitAllowingStateLoss()

        lateinit var binding: ActivityMainBinding

        binding.mainBottomNavi.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.reservationFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, ReservationFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.registerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, RegistrationFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.messengerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, MessengerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, MyPageMainFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                else -> { return@setOnItemSelectedListener true }
            }
        }
    }
}