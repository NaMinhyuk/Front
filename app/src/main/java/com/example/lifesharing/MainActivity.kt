package com.example.lifesharing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifesharing.databinding.ActivityMainBinding
import com.example.lifesharing.home.HomeFragment
import com.example.lifesharing.messenger.userList.MessengerFragment
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
                else -> {
                    return@setOnItemSelectedListener  true
                }
            }
        }
    }
}