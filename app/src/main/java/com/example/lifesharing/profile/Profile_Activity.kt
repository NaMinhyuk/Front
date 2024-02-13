package com.example.lifesharing.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityProfileBinding
import com.google.android.material.tabs.TabLayout

class Profile_Activity:AppCompatActivity() {
    val binding = ActivityProfileBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.profileBodyTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            //텝을 한번 클릭하였을때 어떤 이벤트가 나올지 설정하는 함수
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position){
                    0-> {

                    }
                    1->{

                    }
                    2-> {

                    }
                }
            }

            //이미 한번 클릭하였을때 한번더 클릭하면 클릭되지 않는 상태로 바꿔주는 함수
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            //이미 한번 클릭하였을때 한번더 클릭하면 어떤 이벤트가 실행되는지 설정하는 함수
            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })
    }
}