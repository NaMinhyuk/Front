package com.example.lifesharing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifesharing.databinding.ActivityMainBinding
import com.example.lifesharing.home.HomeFragment
import com.example.lifesharing.messenger.MessengerFragment
import com.example.lifesharing.mypage.MyPageActivity
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
                    // 아이콘 변경
                    item.setIcon(R.drawable.btm_home_select_ic)
                    true
                }

                R.id.reservationFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, ReservationFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_reserve_select_ic)
                    true
                }

                R.id.registerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, RegistrationFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_regist_select_ic)
                    true
                }

                R.id.messengerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, MessengerFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_messenger_select_ic)
                    true
                }

                R.id.mypageFragment -> {
                    val intent = Intent(this@MainActivity, MyPageActivity::class.java)
                    startActivity(intent)

                    true
                }

                else -> {
                    // 선택되지 않았을 때 각 Fragment에 따른 기본 아이콘으로 변경하는 로직
                    when (item.itemId) {
                        R.id.homeFragment -> item.setIcon(R.drawable.btm_home_ic)
                        R.id.reservationFragment -> item.setIcon(R.drawable.btm_reserve_ic)
                        R.id.registerFragment -> item.setIcon(R.drawable.btm_regist_ic)
                        R.id.messengerFragment -> item.setIcon(R.drawable.btm_messenger_ic)
                        R.id.mypageFragment -> item.setIcon(R.drawable.btm_my_page_ic)
                    }
                    false
                }
            }
        }
    }
}