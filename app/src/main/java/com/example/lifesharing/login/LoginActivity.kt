package com.example.lifesharing.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.lifesharing.MainActivity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityLoginBinding
import com.example.lifesharing.login.viewModel.LoginViewModel
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    val loginViewModel : LoginViewModel by viewModels()

    private var emailFlag = false

    private var passwordFlag = false

    fun passwordRegex(password: String) : Boolean {
        return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,16}$".toRegex())
    }

    private val passwordListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun afterTextChanged(s: Editable?) {
            if (s != null ) {
                when {
                    s.isEmpty() -> {
                        binding.passwordInputLayout.error = "영문, 숫자, 특수문자 조합 8~16자를 입력해 주세요."
                        passwordFlag = false
                    }
                    !passwordRegex(s.toString()) -> {
                        binding.passwordInputLayout.error = "영문, 숫자, 특수문자 조합 8~16자를 입력해 주세요."
                        passwordFlag = false
                    }
                    else -> {
                        binding.passwordInputLayout.error = null
                        passwordFlag = true
                    }
                }
                flagCheck()
            }
        }

    }

    // 이메일 체크

    private val emailListener = object : TextWatcher {
        var pattern: Pattern? = Patterns.EMAIL_ADDRESS

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s!=null) {
                when {
                    s.isEmpty() -> {
                        binding.emailInputLayout.error = "올바른 이메일을 입력해주세요."
                        emailFlag = false
                    }
                    pattern!!.matcher(s).matches() -> {
                        binding.emailInputLayout.error = "올바른 이메일을 입력해주세요."
                        emailFlag = false
                    }
                    else -> {
                        binding.emailInputLayout.error = null
                        emailFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    fun flagCheck() {
        binding.loginButton.isEnabled = emailFlag && passwordFlag
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.activity = this
        binding.lifecycleOwner = this
        setObserve()

        binding.emailInputLayout.editText?.addTextChangedListener(emailListener)
        binding.emailInputEditText.hint = resources.getString(R.string.email_hint)
        binding.emailInputEditText.setOnFocusChangeListener{_, hasFocus ->
            if (hasFocus) {
                binding.emailInputEditText.hint =""
            } else {
                binding.emailInputEditText.hint = resources.getString(R.string.email_hint)
            }
        }

        binding.passwordInputLayout.editText?.addTextChangedListener(passwordListener)
        binding.passwordInput.hint = resources.getString(R.string.password_hint)
        binding.passwordInput.setOnFocusChangeListener{_, hasFocus ->
            if (hasFocus) {
                binding.passwordInput.hint =""
            } else {
                binding.passwordInput.hint = resources.getString(R.string.password_hint)
            }
        }
    }

    fun setObserve() {
        loginViewModel.showMainActivity.observe(this) {
            if (it) {
                finish() // 로그인 되고 난 후 이제 로그인 액티비티 필요없어성 >_<
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}