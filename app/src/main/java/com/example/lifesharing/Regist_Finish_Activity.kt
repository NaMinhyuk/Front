package com.example.lifesharing

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifesharing.databinding.ActivityRegistAddBinding
import com.example.lifesharing.databinding.ActivityRegistFinishBinding

class Regist_Finish_Activity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegistFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registFinishBackbtn.setOnClickListener{
            val intent = Intent(this, ActivityRegistAddBinding::class.java)
            startActivity(intent)
        }

        binding.registFinishHomebtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}