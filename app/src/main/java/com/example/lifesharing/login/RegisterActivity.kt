package com.example.lifesharing.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    val TAG: String = "로그"
    lateinit var binding: ActivityRegisterBinding
    val registerViewModel : RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = registerViewModel
        binding.activity = this
        binding.lifecycleOwner = this
        setObserve()
    }

    fun setObserve() {
        registerViewModel.showLoginActivity.observe(this) {
            if (it) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}