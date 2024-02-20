package com.example.lifesharing.product

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityDetailReserveBinding

class Product_Detail_Reserve_Activity : AppCompatActivity() {
    val TAG : String = "로그"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_reserve)

        val binding = ActivityDetailReserveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reserveBackBtn.setOnClickListener {
            finish()
        }

        binding.reserveBottomTextbox.setOnClickListener {
            val intent = Intent(this, Product_Detail_Reserve_Finish_Activity::class.java)
            startActivity(intent)
        }

        val morning_afternoon_Array = arrayOf(0,0)
        binding.reserveMorningBtn.setOnClickListener {
            morning_afternoon_Array[0] = 1
            if(morning_afternoon_Array[1] == 1){
                morning_afternoon_Array[1] = 0
                binding.reserveAfternoonBtn.setImageResource(R.drawable.reserve_time_btn)
                binding.reserveAfternoonTv.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
            }
            binding.reserveMorningBtn.setImageResource(R.drawable.regist_add_clickbtn)
            binding.reserveMorningTv.setTextColor(Color.parseColor("#1277ED"))

            val text = binding.detailReserveTopBeforeTime.text.toString()
            Log.d(TAG, "${text}")
            text.replace("오후","오전")
            binding.detailReserveTopBeforeTime.text = text
        }
        binding.reserveAfternoonBtn.setOnClickListener {
            morning_afternoon_Array[1] = 1
            if(morning_afternoon_Array[0] == 1){
                morning_afternoon_Array[0] = 0
                binding.reserveMorningBtn.setImageResource(R.drawable.reserve_time_btn)
                binding.reserveMorningTv.setTextColor(ContextCompat.getColor(this, R.color.gray_800))
            }
            binding.reserveAfternoonBtn.setImageResource(R.drawable.regist_add_clickbtn)
            binding.reserveAfternoonTv.setTextColor(Color.parseColor("#1277ED"))
            val text = binding.detailReserveTopBeforeTime.text.toString()
            Log.d(TAG, "${text}")
            text.replace("오전","오후")
            binding.detailReserveTopBeforeTime.text = text
        }

        val time_Array = arrayOf(0,0,0,0,0,0,0,0,0,0,0,0)

        val time_btn_array = arrayOf(binding.reserveHour1Btn,binding.reserveHour2Btn,binding.reserveHour3Btn,
            binding.reserveHour4Btn,binding.reserveHour5Btn,binding.reserveHour6Btn,binding.reserveHour7Btn,
            binding.reserveHour8Btn,binding.reserveHour9Btn,binding.reserveHour10Btn,binding.reserveHour11Btn,
            binding.reserveHour12Btn)

        val time_text_array = arrayOf(binding.reserveHour1Tv,binding.reserveHour2Tv,binding.reserveHour3Tv,
            binding.reserveHour4Tv,binding.reserveHour5Tv,binding.reserveHour6Tv,binding.reserveHour7Tv,
            binding.reserveHour8Tv,binding.reserveHour9Tv,binding.reserveHour10Tv,binding.reserveHour11Tv,
            binding.reserveHour12Tv)


        for (i in 0..11) {
            time_btn_array[i].setOnClickListener {
                time_Array[i] = 1
                for (j in 0..11){
                    if(i==j) continue
                    else time_Array[j] = 0
                }
                for (j in 0..11) {
                    if (time_Array[j] == 1) {
                        time_btn_array[j].setImageResource(R.drawable.regist_add_clickbtn)
                        time_text_array[j].setTextColor(Color.parseColor("#1277ED"))
                    } else {
                        time_btn_array[j].setImageResource(R.drawable.reserve_time_btn)
                        time_text_array[j].setTextColor(ContextCompat.getColor(this,R.color.gray_800))
                    }
                }
            }
        }


    }
}