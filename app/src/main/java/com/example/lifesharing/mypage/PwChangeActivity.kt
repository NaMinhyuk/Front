package com.example.lifesharing.mypage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityPwChangeBinding
import com.example.lifesharing.mypage.mypage_data.PwData
import com.example.lifesharing.mypage.viewModel.ChangePwViewModel

class PwChangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPwChangeBinding
    private lateinit var viewModel : ChangePwViewModel   // 비밀번호 변경 ViewModel 객체 선언

    val TAG : String = "비밀번호 변경 로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPwChangeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // 뒤로가기 버튼
        binding.btnBackChangePw.setOnClickListener {
            finish()
        }

        val changeData = PwData(binding.oldPwInputBox.text.toString(), binding.newPwInputBox.text.toString())

        viewModel = ViewModelProvider(this).get(ChangePwViewModel::class.java)
        //viewModel.changePw(changeData)

        setupTextWatchers()     // EditText의 텍스트 변경감지 설정 메서드 호출
        setupChangePassword()   // 비밀번호 변경 버튼 로직 설정 메서드 호출
        observeChangePasswordResult()  // 비밀번호 변경 결과를 관찰
    }

    // 비밀번호 변경 버튼 로직
    private fun setupChangePassword() {
        binding.btnEditPwDone.setOnClickListener {
            val newPassword = binding.newPwInputBox.text.toString()
            val passwordRegex = "^[a-zA-Z0-9!@#$%^&*]{8,16}$".toRegex()   // 비밀번호 입력 정규표현식 정의


            // 새 비밀번호 유효성 검사
            if (!newPassword.matches(passwordRegex)) {
                binding.newPwInputBox.setBackgroundResource(R.drawable.edittext_red_box)
                binding.newPwErrorTv.visibility = View.VISIBLE
            } else {
                // 유효성 검사를 통과했을 때만 배경과 경고 메시지를 정상 상태로 복원
                binding.newPwInputBox.setBackgroundResource(R.drawable.edittext_green_box)
                binding.newPwErrorTv.visibility = View.INVISIBLE
            }

            // 새 비밀번호 확인 로직
            if(newPassword != binding.newPwCheckInputBox.text.toString()){
                // 새로 입력한 비밀번호와 확인용 비밀번호가 다르다면
                binding.newPwCheckInputBox.setBackgroundResource(R.drawable.edittext_red_box)
                binding.newPwCheckErrorTv.visibility = View.VISIBLE
            } else {
                binding.newPwCheckInputBox.setBackgroundResource(R.drawable.edittext_green_box)
                binding.newPwCheckErrorTv.visibility = View.GONE
            }

            val oldPassword = binding.oldPwInputBox.text.toString()
            val changeData = PwData(oldPassword, newPassword)
            Log.d(TAG, "Attempting to change password")
            viewModel.changePassword(changeData)
        }
    }

    // EditText 텍스트 변경 감지
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateButtonState()    // EditText에 문자가 입력이 됐을 경우
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        // 각 EditText에 TextWatcher를 추가
        binding.oldPwInputBox.addTextChangedListener(textWatcher)
        binding.newPwInputBox.addTextChangedListener(textWatcher)
        binding.newPwCheckInputBox.addTextChangedListener(textWatcher)
    }

    // 변경하기 버튼 활성화 메서드
    private fun updateButtonState() {
        // 모든 입력 필드에 텍스트가 있는지 확인
        val isAllFieldsFilled = binding.oldPwInputBox.text.isNotEmpty() &&
                                binding.newPwInputBox.text.isNotEmpty() &&
                                binding.newPwCheckInputBox.text.isNotEmpty()

        // 수정하기 버튼의 활성화 상태 업데이트
        binding.btnEditPwDone.isEnabled = isAllFieldsFilled
        binding.btnEditPwDone.setBackgroundResource(R.drawable.btn_done_shape)
        binding.btnEditPwDone.setTextColor(Color.WHITE)
    }

    // 비밀번호 변경 결과를 관찰하고 처리하는 메서드
    private fun observeChangePasswordResult() {
        viewModel.changePwData.observe(this, Observer { response ->  // 비밀번호 변경 결과를 관찰
            if (response.isSuccess == true) {   // 응답이 true 일 경우
                Log.d(TAG, "비밀번호 변경 성공")
                finish()   // 액티비티 정상 종료
            }
            else {                              // 응답이 false 일 경우 - 응답 code에 따라 로직 구분
                when (response.code) {
                    "USER_400_2" -> {   // 기존 비밀번호 입력 오류
                        binding.oldPwInputBox.setBackgroundResource(R.drawable.edittext_red_box)
                        binding.oldPwWrongTv.visibility = View.VISIBLE
                    }
                    else -> {
                        Log.d(TAG, "비밀번호 변경 실패: ${response.message}")
                    }
                }
            }
        })
    }
}