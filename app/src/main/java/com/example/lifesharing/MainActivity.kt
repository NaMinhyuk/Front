package com.example.lifesharing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifesharing.databinding.ActivityMainBinding
import com.example.lifesharing.home.HomeFragment
import com.example.lifesharing.messenger.userList.MessengerFragment
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

    private var homeIconSelected = false
    private var reserveIconSelected = false
    private var registIconSelected = false
    private var messengerIconSelected = false
    private var mypageIconSelected = false

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
                    homeIconSelected = true
                    reserveIconSelected = false
                    registIconSelected = false
                    messengerIconSelected = false
                    mypageIconSelected = false
                    updateUnselectedIcons()
                    true
                }

                R.id.reservationFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, ReservationFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_reserve_select_ic)
                    homeIconSelected = false
                    reserveIconSelected = true
                    registIconSelected = false
                    messengerIconSelected = false
                    mypageIconSelected = false
                    updateUnselectedIcons()
                    true
                }

                R.id.registerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, RegistrationFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_regist_select_ic)
                    homeIconSelected = false
                    reserveIconSelected = false
                    registIconSelected = true
                    messengerIconSelected = false
                    mypageIconSelected = false
                    updateUnselectedIcons()
                    true
                }

                R.id.messengerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, MessengerFragment())
                        .commitAllowingStateLoss()

                    item.setIcon(R.drawable.btm_messenger_select_ic)
                    homeIconSelected = false
                    reserveIconSelected = false
                    registIconSelected = false
                    messengerIconSelected = true
                    mypageIconSelected = false
                    updateUnselectedIcons()
                    true
                }

                R.id.mypageFragment -> {
                    val intent = Intent(this@MainActivity, MyPageActivity::class.java)
                    startActivity(intent)


                    homeIconSelected = false
                    reserveIconSelected = false
                    registIconSelected = false
                    messengerIconSelected = false
                    mypageIconSelected = true
                    updateUnselectedIcons()
                    true
                }

                else -> false
            }
        }
    }

    private fun updateUnselectedIcons() {
        binding.mainBottomNavi.menu.findItem(R.id.homeFragment)
            .setIcon(if (homeIconSelected) R.drawable.btm_home_select_ic else R.drawable.btm_home_ic)
        binding.mainBottomNavi.menu.findItem(R.id.reservationFragment)
            .setIcon(if (reserveIconSelected) R.drawable.btm_reserve_select_ic else R.drawable.btm_reserve_ic)
        binding.mainBottomNavi.menu.findItem(R.id.registerFragment)
            .setIcon(if (registIconSelected) R.drawable.btm_regist_select_ic else R.drawable.btm_regist_ic)
        binding.mainBottomNavi.menu.findItem(R.id.messengerFragment)
            .setIcon(if (messengerIconSelected) R.drawable.btm_messenger_select_ic else R.drawable.btm_messenger_ic)
        binding.mainBottomNavi.menu.findItem(R.id.mypageFragment)
            .setIcon(if (mypageIconSelected) R.drawable.btm_my_page_ic else R.drawable.btm_my_page_ic)
    }
}