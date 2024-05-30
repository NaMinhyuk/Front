package com.example.lifesharing.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityRegisterBinding
import com.example.lifesharing.login.model.request_body.LocationDTO
import com.example.lifesharing.login.model.request_body.RegisterRequestBody
import com.example.lifesharing.login.viewModel.RegisterViewModel
import com.example.lifesharing.mypage.viewModel.NicknameCheckViewModel
import com.example.lifesharing.mypage.work.NicknameCheckWork
import com.example.lifesharing.mypage.work.NicknameStatus
import com.example.lifesharing.service.work.RegisterWork
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.util.Locale

/** 회원가입 */
class RegisterActivity : AppCompatActivity() {

    val TAG: String = "로그"
    val LOCATION_TAG: String = "위치 정보 로그"

    val registerViewModel: RegisterViewModel by viewModels()    // 회원가입 ViewModel

    lateinit var binding: ActivityRegisterBinding
    private lateinit var registerWork: RegisterWork             // 회원가입 Model
    private lateinit var nicknameCheckViewModel: NicknameCheckViewModel   // 닉네임 중복 확인 ViewModel

    // 사용자 입력 데이터를 담을 LiveData 객체들을 선언
    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")
    var name: MutableLiveData<String> = MutableLiveData("")
    var checkPassword: MutableLiveData<String> = MutableLiveData("")
    var phone: MutableLiveData<String> = MutableLiveData("")
    var verifiedNumber: MutableLiveData<String> = MutableLiveData("")

    // 이미지 선택 결과를 처리할 ActivityResultLauncher 선언
    private lateinit var imageResultLauncher: ActivityResultLauncher<Intent>
    // 선택된 이미지를 저장할 리스트를 초기화
    private var imageList: ArrayList<MultipartBody.Part> = ArrayList()

    lateinit var body: MultipartBody.Part

    // 위치 정보 제공을 위한 FusedLocationProviderClient와 LocationCallback 객체를 선언
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    var roadAdd: String? = null
    var emdNm : String? = null
    var zipNo : String? = null        // 우편번호
    var admCd : String? = null        // 행정구역코드
    var rnMgtSn : String? = null      // 도로명코드


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = registerViewModel   // 회원가입 ViewModel 설정
        binding.activity = this                 // 바인딩 변수 설정
        binding.lifecycleOwner = this           // 생명주기 소유자 설정

        // 회원가입 시 사용되는 이미지 MultipartBody.Part 생성
        body = MultipartBody.Part.createFormData(
            "multipartFile",
            "",
            "".toRequestBody("image/*".toMediaTypeOrNull())
        )

        // 이미지 선택 결과 처리
        imageResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                handleSelectedImage(result.data)    // 선택된 이미지를 처리
            }
        }

        // 갤러리에서 이미지 선택
        binding.pickImage.setOnClickListener {
            selectImageFromGallery()    // 갤러리 호출 함수
        }

        // 닉네임 중복 체크 ViewModel을 ViewModelProvider를 사용하여 가져와 초기화
        nicknameCheckViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            NicknameCheckViewModel::class.java)


        // 전화번호 인증 요청
        binding.btnPhoneCheck.setOnClickListener {
            binding.btnPhoneCheck.setBackgroundResource(R.drawable.blue_line_gray_background)
            binding.btnPhoneCheck.setTextColor(Color.parseColor("#1277ED"))
        }

        // 인증번호 확인
        binding.btnCertification.setOnClickListener {
            binding.btnCertification.setBackgroundResource(R.drawable.blue_line_gray_background)
            binding.btnCertification.setTextColor(Color.parseColor("#1277ED"))
        }

        // 회원가입 결과 LiveData 관찰 -> 결과에 따라 로그인 화면으로 전환
        setObserve()

        // 현재 사용자의 위치 받아오기
        binding.registerLocationBtn.setOnClickListener {
            getMyLocation()
        }

        // 회원가입 로직을 처리할 객체를 초기화
        registerWork = RegisterWork()

        // 가입하기
        binding.registerButton.setOnClickListener {
            registerWithImage()
        }

        // 뒤로가기
        binding.back.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        // 위치 서비스 클라이언트 초기화 -> 위치API와 상호작용
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // 위치 접근 권한 체크
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_LOCATION)
            return
        }

        // 위치 업데이트 요청을 설정. (높은 정확도와 특정 시간 간격을 이용해 위치 정보 업데이트)
//        val locationRequest = LocationRequest.create().apply {
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            interval = 10000
//            fastestInterval = 5000
//        }
        val locationRequest = LocationRequest.Builder(10000L)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setMinUpdateIntervalMillis(5000L)
            .build()

        // 위치가 업데이트 될 때마다 수행할 작업 정의
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.lastOrNull()?.let {
                    // 마지막 위치의 위도 , 경도 추출
                    val latitude = it.latitude
                    val longitude = it.longitude
                    Log.d(LOCATION_TAG, "Latitude: $latitude, Longitude: $longitude")    // 확인용 로그

                    // Geocoder를 사용하여 위도, 경도 값으로 주소 조회
                    val geocoder = Geocoder(this@RegisterActivity, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                        if (addresses!!.isNotEmpty()) {
                            val address = addresses[0]
                            // 얻은 주소 리스트의 제일 첫번째 주소의 도로명 주소를 변수에 저장
                            val addressText = address.getAddressLine(0)
                            Log.d(LOCATION_TAG, "Address: $addressText")

                            // 도로명 또는 동 이름 추출하여 emdNm에 저장
                            val addressParts = address.getAddressLine(0).split(", ")
                            val mainPart = addressParts.firstOrNull() ?: ""
                            val subParts = mainPart.split(" ")

                            // "동"이 포함된 부분 또는 "로"가 포함된 부분 추출
                            emdNm = subParts.find { it.contains("동") || it.contains("로") }
                            // "구"가 포함된 부분 추출
                            rnMgtSn = subParts.find { it.contains("구") }

                            roadAdd = addressText              // 도로명 주소 설정
                            admCd = address.adminArea ?: ""         // 시, 도
                            zipNo = address.postalCode ?: ""        // 우편번호

                            Log.d(LOCATION_TAG, "도로명주소: $roadAdd 읍/면/동: $emdNm, 시/도: $admCd, 구: $rnMgtSn, 우편번호: $zipNo")   // 확인용 로그


                            // 주소가 제대로 조회가 된다면
                            if (!addressText.isNullOrEmpty()){
                                // 위치 인증 완료 버튼 업데이트
                                binding.registerLocationBtn.setBackgroundResource(R.drawable.blue_line_gray_background)
                                binding.registerLocationBtn.setTextColor(Color.parseColor("#1277ED"))
                                binding.registerLocationBtn.text = "위치 인증 완료"
                            }

                        } else {
                            Log.d(LOCATION_TAG, "주소를 찾을 수 없습니다.")
                        }
                    } catch (e: IOException) {
                        Log.e(LOCATION_TAG, "Geocoder failed", e)
                    }

                    // 위치 업데이트 중지
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                } ?: Log.d(LOCATION_TAG, "No location data available.")
            }
        }
        // 위치 업데이트 요청을 시작
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()!!)
    }

    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_LOCATION = 1
    }

    // 갤러리에서 이미지 선택하는 메서드
    private fun selectImageFromGallery() {
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        // 권한 확인
        if(writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED){
            // 권한 요청
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                1)
        }
        // 권한이 있는 경우 갤러리 실행
        else{
            // 다중이미지 선택
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            imageResultLauncher.launch(intent) // 갤러리 열기
        }
    }

    private fun handleSelectedImage(data: Intent?) {
        // 갤러리에서 선택된 이미지를 처리
        data?.data?.let { uri ->
            Glide.with(this).load(uri).into(binding.myUserProfile)
            val file = File(getRealPathFromURI(uri))
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("multipartFile", file.name, requestFile)
            imageList.clear()  // 이미지가 재선택되면 이전 이미지를 클리어
            imageList.add(part)
        }
    }

    // 이미지의 경로를 가져오는 함수
    private fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER   // 현재 기기의 제조사를 가져옴
        if (buildName.equals("Xiaomi")){     // 샤오미의 경우네는 이미지 경로 처리 방법이 다르기에 경로를 직접 반환
            return uri.path!!
        }

        var columnIndex = 0
        // MediaStore.Images.Media.DATA를 사용하여 커서를 통해 파일의 실제 경로를 조회하고 반환환
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        // 이미지 파일의 실제 경로를 얻기 위해 커서를 사용
        val cursor = contentResolver.query(uri, proj, null, null, null)

        // 커서 이동 -> 해당 위치에 데이터가 있는지 확인 후 있으면 true 반환
        if (cursor!!.moveToFirst()){
            // 이미지 파일의 경로를 저장하는 column index 저장
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }

        // 커서에서 가져온 데이터 중에서 이미지 파일의 실제 경로를 문자열 형태로 가져옴
        val result = cursor.getString(columnIndex)
        cursor.close()  // 데이터 조회가 끝나면 커서를 close -> 리소스 누수 방지

        return result   // 실제 이미지 경로 반환
    }

    // 제품 등록 요청 메서드
    fun registerWithImage() {

        // 먼저 비밀번호 입력 일치 여부를 검증
        if (!passwordCheck()) {
            binding.passwordInput1.setBackgroundResource(R.drawable.edittext_red_box)
            binding.errorPwCheckTv.visibility = View.VISIBLE
            return  // 비밀번호 불일치로 회원가입 중지
        }

        // 입력된 데이터를 회원가입 RequestBody 형태로 변환
        val registerUserData = RegisterRequestBody(
            email.value.toString(),
            password.value.toString(),
            name.value.toString(),
            phone.value.toString(),
            LocationDTO(roadAdd, emdNm, zipNo, admCd)
        )

        // 회원가입 결과 LiveData를 관찰
        registerWork.joinResult.observe(this, Observer { result ->
            if (result.isSuccess == true){   // 회원가입이 성공적으로 이루어질 경우
                Log.d(TAG, "회원가입 성공")

                // 회원가입 성공 시 해당 액티비티 종료 후 로그인 화면 전환
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            // 회원가입이 실패할 경우
            else {
                // 비밀번호 입력 일치 여부 확인
                passwordCheck()

                Log.d(TAG, "회원가입 실패: ${result.code} - ${result.message}")
                when (result.code) {
                    // 이메일 또는 닉네임 중복
                    "USER_400_1" -> {
                        binding.editTextTextEmailAddress.setBackgroundResource(R.drawable.edittext_red_box)
                        binding.errorEmailTv.visibility = View.VISIBLE
                    }

                    // 비밀번호 형식 오류
                    "USER_400_2" -> {
                        binding.passwordInput.setBackgroundResource(R.drawable.edittext_red_box)
                        binding.errorPwTv.visibility = View.VISIBLE
                    }

                }
            }
        })

        // 회원가입 요청 api 호출
        registerWork.registerWork(registerUserData, imageList[0])
    }


    private fun passwordCheck() : Boolean{
        // 입력한 비밀번호와 확인용 비밀번호가 다를 경우
        if (binding.passwordInput.text.toString() != binding.passwordInput1.text.toString() ){
            return false
        }
        else{
            binding.passwordInput1.setBackgroundResource(R.drawable.edittext_green_box)
            binding.passwordInput1.visibility = View.INVISIBLE
            return true
        }
    }

    fun nicknameCheck() {
        val inputNickname : String = binding.nicknameInput.text.toString()   // 입력된 닉네임을 변수에 할당
        // 입력된 닉네임을 중복확인 api에 전달
        nicknameCheckViewModel.checkNickname(inputNickname)

        // 닉네임 사용 가능 여부를 닉네임 중복 확인 결과 LviveData를 통해 결정
        nicknameCheckViewModel.nicknameStatus.observe(this, Observer { status ->
            if(status.equals(NicknameStatus.Available)){          // 사용 가능할 경우
                binding.nicknameInput.setBackgroundResource(R.drawable.edittext_green_box)
                binding.nicknameErrorTv.text = "사용 가능한 닉네임입니다."
                binding.nicknameErrorTv.setTextColor(Color.parseColor("#08C152"))
            }
            else if(status.equals(NicknameStatus.Unavailable)){    // 중복일 경우
                binding.nicknameInput.setBackgroundResource(R.drawable.edittext_red_box)
                binding.nicknameErrorTv.text = "중복된 닉네임입니다."
                binding.nicknameErrorTv.setTextColor(Color.parseColor("#E13017"))
            }
            else if(status.equals(NicknameStatus.Error)){          // 네트워크 오류
                binding.nicknameInput.setBackgroundResource(R.drawable.edittext_red_box)
                binding.nicknameErrorTv.text = "오류가 발생했습니다. 다시 시도해주세요."
                binding.nicknameErrorTv.setTextColor(Color.parseColor("#E13017"))
            }

        })
        binding.registerNicknameCheck.setBackgroundResource(R.drawable.register_location_btn)
        binding.registerNicknameCheck.setTextColor(Color.parseColor("#1277ED"))
    }

    // 약관 동의 클릭 이벤트 로직
    fun checkAll() {
        binding.registerAllCheck.setImageResource(R.drawable.register_all_check)
        binding.registerCheck1.setImageResource(R.drawable.register_check_color)
        binding.registerCheck2.setImageResource(R.drawable.register_check_color)
        binding.registerButton.setBackgroundResource(R.drawable.register_colored_btn)
        binding.registerButton.setTextColor(Color.parseColor("#ffffff"))
    }

    fun check1() {
        binding.registerCheck1.setImageResource(R.drawable.register_check_color)
    }

    fun check2() {
        binding.registerCheck2.setImageResource(R.drawable.register_check_color)
    }


    fun setObserve() {
        registerViewModel.showLoginActivity.observe(this) {
            if (it) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}