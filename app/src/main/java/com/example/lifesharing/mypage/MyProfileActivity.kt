package com.example.lifesharing.mypage

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lifesharing.BuildConfig
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMyProfileBinding
import com.example.lifesharing.mypage.viewModel.NicknameChangeViewModel
import com.example.lifesharing.mypage.viewModel.MyPageInfoViewModel
import com.example.lifesharing.mypage.viewModel.NicknameCheckViewModel
import com.example.lifesharing.mypage.work.NicknameStatus

/** 마이페이지 내 정보 View */
class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var nicknameCheckViewModel: NicknameCheckViewModel     // 닉네임 중복 확인을 위한 객체
    private lateinit var viewModel: MyPageInfoViewModel           // MyPageInfoViewModel - 내 정보 화면에 필요한 객체
    private lateinit var nicknameChangeViewModel : NicknameChangeViewModel    // NicknameChangeViewModel - 닉네임 변경에 필요한 객체


    val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL    // 프로필 이미지를 로딩하기 위한 aws baseUrl

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기
        binding.myProfileBackIv.setOnClickListener {
            finish()
        }

        // 닉네임 중복 확인 ViewModel을 ViewModelProvider를 사용하여 가져옴
        nicknameCheckViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(NicknameCheckViewModel::class.java)
        // 닉네임 변경 ViewModel을 ViewModelProvider를 사용하여 가져옴
        nicknameChangeViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(NicknameChangeViewModel::class.java)


        // 닉네임 중복 체크
        binding.btnNicknameCheck.setOnClickListener {
            nicknameCheck()
        }

        // 비밀번호 변경
        binding.pwChangeBtn.setOnClickListener {
            val intent = Intent(this, PwChangeActivity::class.java)
            startActivity(intent)
        }

        // 사용자 정보 조회 ViewModel을 ViewModelProvider를 사용하여 가져옴
        viewModel = ViewModelProvider(this).get(MyPageInfoViewModel::class.java)
        viewModel.userInfo.observe(this, Observer {userInfo ->     // 내정보에 필요한 데이터를 바인딩하기 위해 LiveData 관찰
            Glide.with(this).load(IMAGE_BASE_URL+userInfo.profileUrl).into(binding.imgProfile) // 이미지 로딩
            // EditText에 텍스트를 설정
            binding.emailInputBox.text = Editable.Factory.getInstance().newEditable(userInfo.email)
            binding.nicknameInputBox.text = Editable.Factory.getInstance().newEditable(userInfo.nickname)
            binding.phoneInputBox.text = Editable.Factory.getInstance().newEditable(userInfo.phone)   // 유저의 전화번호를 넘겨주도록 해야함
        })

        // 서버로 부터 사용자 정보를 요청
        viewModel.getUserProfileInfo()


        // 수정하기 버튼
        binding.btnEditDone.setOnClickListener {
            val newNickname = binding.nicknameInputBox.text.toString()   // 입력된 닉네임을 변수에 할당
            nicknameChangeViewModel.changeNickname(newNickname) // 닉네임 변경 요청 api 호출
            // 닉네임 변경 결과를 LiveData를 통해 관찰
            nicknameChangeViewModel.nicknameChangeSuccess.observe(this, Observer { result ->
                // 결과가 true(성공적으로 변경)일 경우
                if (result) {
                    setResult(Activity.RESULT_OK)  // 성공 결과 설정(마이페이지 액티비티에 결과를 넘겨주기 위함)
                    finish()  // 액티비티 종료
                }
                // 결과가 false(변경 실패)일 경우
                else {
                    Toast.makeText(this, "닉네임 변경에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun nicknameCheck() {
        val inputNickname : String = binding.nicknameInputBox.text.toString()    // 입력된 닉네임을 변수에 할당
        nicknameCheckViewModel.checkNickname(inputNickname)    // 입력된 닉네임을 중복확인 api에 전달

        // 닉네임 사용 가능 여부
        nicknameCheckViewModel.nicknameStatus.observe(this, Observer { status ->    // 닉네임 중복 확인 결과 LviveData를 관찰
            if(status.equals(NicknameStatus.Available)) {   // 사용 가능할 경우
                binding.nicknameInputBox.setBackgroundResource(R.drawable.edittext_green_box)
                binding.usageTrueOrFalse.text = "사용 가능한 닉네임입니다."
                binding.usageTrueOrFalse.setTextColor(Color.parseColor("#08C152"))
            }
            else if (status.equals(NicknameStatus.Unavailable)){    // 중복일 경우
                binding.nicknameInputBox.setBackgroundResource(R.drawable.edittext_red_box)
                binding.usageTrueOrFalse.text = "중복된 닉네임입니다."
                binding.usageTrueOrFalse.setTextColor(Color.parseColor("#E13017"))
            }
            else if (status.equals(NicknameStatus.Error)){   // 네트워크 오류
                binding.nicknameInputBox.setBackgroundResource(R.drawable.edittext_red_box)
                binding.usageTrueOrFalse.text = "오류가 발생했습니다. 다시 시도해주세요."
                binding.usageTrueOrFalse.setTextColor(Color.parseColor("#E13017"))
            }
        })
    }
}