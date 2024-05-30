package com.example.lifesharing.regist

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityRegistAddBinding
import com.example.lifesharing.regist.model.request_body.ProductRegisterRequestBody
import com.example.lifesharing.service.work.RegisterProduct
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import kotlinx.coroutines.*
import okhttp3.RequestBody.Companion.toRequestBody

/** 제품 등록 View*/
class Regist_Add_Activity : AppCompatActivity() {

    val TAG:String = "로그"

    var body: MultipartBody.Part?=null
    var categoryIds : String?=null

    lateinit var pickMultipleMediaRequest: ActivityResultLauncher<Intent>
    private lateinit var pickDateResultLauncher: ActivityResultLauncher<Intent>

    // 각 화면에 보여지는 데이터를 관찰하는 변수 선언
    var productName: MutableLiveData<String> = MutableLiveData()
    var day_price: MutableLiveData<String> = MutableLiveData()
    var time_price: MutableLiveData<String> = MutableLiveData()
    var deposit : MutableLiveData<String> = MutableLiveData()
    var lendingPeriod : MutableLiveData<String> = MutableLiveData()
    var product_text : MutableLiveData<String> = MutableLiveData()
    

    var imageList : ArrayList<MultipartBody.Part> = ArrayList()

    lateinit var binding: ActivityRegistAddBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist_add)
        binding.activity = this
        binding.lifecycleOwner = this

        // 단일 미디어 항목 코드
        pickMultipleMediaRequest = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                handleSelectedImages(result.data)
            }
        }


        // 대여 기간 선택 결과를 처리하는 런처 초기화
        // registerForActivityResult를 통해  intent와 result Code를 사용해서 원하는 데이터를 가져옴
        pickDateResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // 선택된 기간 받기
                    val selectedPeriod = result.data?.getStringExtra("selectedPeriod")
                    // EditText 뷰에 표시
                    binding.registSetTimeBtn.text = selectedPeriod
                }
        }

        // 뒤로가기
        binding.registBackBtn.setOnClickListener {
            finish()    // Activity 종료
        }

        // 대여 기간설정 버튼
        binding.registSetTimeBtn.setOnClickListener {
            val intent = Intent(this, Regist_SetTime_Activity::class.java)
            pickDateResultLauncher.launch(intent)      // 데이터를 받아올 activity를 실행
        }

        // 이미지 등록 버튼
        binding.registImageIv.setOnClickListener {
            selectedGallery()    // 갤러리에서 사진 선택하는 함수 호출
        }

        // 제품 등록 완료 버튼
        binding.registBottomBtn.setOnClickListener {
            if (validateInput()) {
                binding.registBottomBtn.isEnabled = true
                binding.registBottomBtn.setBackgroundColor(Color.parseColor("#1277ED"))
                registerProduct()    // 제품 등록 함수 호출
            } else {
                binding.registBottomBtn.isEnabled = false
                binding.registBottomBtn.setBackgroundColor(Color.parseColor("#B0B0B0"))
            }
        }

        //카테고리 전역변수
        val category_checkarray = arrayOf(0,0,0,0,0,0,0,0)


        //카테고리ID 지정
        binding.registCategory1.setOnClickListener {
            category_checkarray[0] = 1     // 배열의 첫번째 요소 = 1번 카테고리 -> 선택될 경우 1로 set
            for (i in 0..7) {        // 0부터 7번 인덱스까지 반복
                if (i == 0) continue       // 현재 인덱스가 1이라면 else문 생략

                else {      // 다른 카테고리가 선택되었을 경우
                    category_checkarray[i] = 0      // 현재 인덱스가 1이 아니라면 해당 인덱스 0으로 set
                    resetCategory(i)    // 카테고리 선택이 해제될 때 호출
                }
            }

            // 현재 인덱스가 1일 때 진행
            categoryIds = "1"
            Log.d(TAG, "categoryId 1 : click ${categoryIds}")
            // 카테고리 뷰 텍스트, 배경 색 변경
            binding.registCategory1.setTextColor(Color.parseColor("#1277ED"))
            binding.registCategory1.setBackgroundResource(R.drawable.regist_category_click1)
        }
        binding.registCategory2.setOnClickListener {
            category_checkarray[1] = 1
            for (i in 0..7) {
                if (i == 1) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "2"
            Log.d(TAG, "categoryId 2 : click ${categoryIds}")
            binding.registCategory2.setTextColor(Color.parseColor("#1277ED"))
            binding.registCategory2.setBackgroundResource(R.drawable.regist_category_click2)
        }
        binding.registCategory3.setOnClickListener {
            category_checkarray[2] = 1
            for (i in 0..7) {
                if (i == 2) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "3"
            Log.d(TAG, "categoryId 3 : click ${categoryIds}")
            binding.registCategory3.setTextColor(Color.parseColor("#1277ED"))
            binding.registCategory3.setBackgroundResource(R.drawable.regist_category_click3)
        }
        binding.registCategory4.setOnClickListener {
            category_checkarray[3] = 1
            for (i in 0..7) {
                if (i == 3) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "4"
            Log.d(TAG, "categoryId 4 : click ${categoryIds}")
            binding.registCategory4.setTextColor(Color.parseColor("#1277ED"))
            binding.registCategory4.setBackgroundResource(R.drawable.regist_category_click4)
        }
        binding.registCategory5.setOnClickListener {
            category_checkarray[4] = 1
            for (i in 0..7) {
                if (i == 4) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "5"
            Log.d(TAG, "categoryId 5 :  click ${categoryIds}")
            binding.registCategory5.setTextColor(Color.parseColor("#1277ED"))
            binding.registCategory5.setBackgroundResource(R.drawable.regist_category_click5)
        }
        binding.registCategory6.setOnClickListener {
            category_checkarray[5] = 1
            for (i in 0..7) {
                if (i == 5) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "6"
            Log.d(TAG, "categoryId 6 : click ${categoryIds}")
            binding.registCategory6.setTextColor(Color.parseColor("#1277ED"))
            binding.registCategory6.setBackgroundResource(R.drawable.regist_category_click6)
        }
        binding.registCategory7.setOnClickListener {
            category_checkarray[6] = 1
            for (i in 0..7) {
                if (i == 6) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "7"
            Log.d(TAG, "categoryId 7 : click ${categoryIds}")
            binding.registCategory7.setTextColor(Color.parseColor("#1277ED"))
            binding.registCategory7.setBackgroundResource(R.drawable.regist_category_click7)
        }
        binding.registCategory8.setOnClickListener {
            category_checkarray[7] = 1
            for (i in 0..7) {
                if (i == 7) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "8"
            Log.d(TAG, "categoryId 8 : click ${categoryIds}")
            binding.registCategory8.setTextColor(Color.parseColor("#1277ED"))
            binding.registCategory8.setBackgroundResource(R.drawable.regist_category_click8)
        }

    }


    /**
     * 입력 필드와 카테고리, 이미지 리스트가 모두 채워졌는지 확인하는 메서드
     */
    private fun validateInput() : Boolean {
        val isAllFieldsFilled = !productName.value.isNullOrEmpty() && !day_price.value.isNullOrEmpty() &&
                !time_price.value.isNullOrEmpty() && !deposit.value.isNullOrEmpty() &&
                !lendingPeriod.value.isNullOrEmpty() && !product_text.value.isNullOrEmpty() &&
                !categoryIds.isNullOrEmpty() && imageList.isNotEmpty()


        var customToast = layoutInflater.inflate(R.layout.custom_toast, null)
        val view = customToast.findViewById<TextView>(R.id.toast_text)

        val v2 = Toast(this)
        v2.view = customToast


        // 만약 모든 필드가 채워지지 않았다면, 첫 번째로 비어 있는 필드에 대해 토스트 메시지 표시
        if (!isAllFieldsFilled) {
            when {
                productName.value.isNullOrEmpty() -> {
                    view.text = "제품 이름을 입력하세요."
                    v2.show()
                }
                day_price.value.isNullOrEmpty() ->{
                    view.text = "일일 대여 가격을 입력하세요."
                    v2.show()
                }
                time_price.value.isNullOrEmpty() -> {
                    view.text = "시간 당 대여 가격을 입력하세요."
                    v2.show()
                }
                deposit.value.isNullOrEmpty() ->{
                    view.text = "보증금을 입력하세요."
                    v2.show()
                }
                lendingPeriod.value.isNullOrEmpty() -> {
                    view.text = "대여 기간을 설정하세요."
                    v2.show()
                }
                product_text.value.isNullOrEmpty() -> {
                    view.text = "제품 설명을 입력하세요."
                    v2.show()
                }
                categoryIds.isNullOrEmpty() -> {
                    view.text = "카테고리를 설정하세요."
                    v2.show()
                }
                !imageList.isNotEmpty() -> {
                    view.text = "제품 이미지를 선택해주세요."
                    v2.show()
                }
            }
            return false
        }
        return true
    }

    /**
     * 다른 카테고리가 선택될 시 이전 선택 카테고리 리셋 메서드
     */
    private fun resetCategory(check : Int) {
        when (check) {
            0 -> {
                binding.registCategory1.setTextColor(Color.parseColor("#5C5D61"))
                binding.registCategory1.setBackgroundResource(R.drawable.regist_category_1)

            }
            1 -> {
                binding.registCategory2.setTextColor(Color.parseColor("#5C5D61"))
                binding.registCategory2.setBackgroundResource(R.drawable.regist_category_2)

            }
            2 -> {
                binding.registCategory3.setTextColor(Color.parseColor("#5C5D61"))
                binding.registCategory3.setBackgroundResource(R.drawable.regist_category_3)

            }
            3 -> {
                binding.registCategory4.setTextColor(Color.parseColor("#5C5D61"))
                binding.registCategory4.setBackgroundResource(R.drawable.regist_category_4)

            }
            4 -> {
                binding.registCategory5.setTextColor(Color.parseColor("#5C5D61"))
                binding.registCategory5.setBackgroundResource(R.drawable.regist_category_5)

            }
            5 -> {
                binding.registCategory6.setTextColor(Color.parseColor("#5C5D61"))
                binding.registCategory6.setBackgroundResource(R.drawable.regist_category_6)

            }
            6 -> {
                binding.registCategory7.setTextColor(Color.parseColor("#5C5D61"))
                binding.registCategory7.setBackgroundResource(R.drawable.regist_category_7)

            }
            7 -> {
                binding.registCategory8.setTextColor(Color.parseColor("#5C5D61"))
                binding.registCategory8.setBackgroundResource(R.drawable.regist_category_8)

            }
        }
    }

    /**
     * 갤러리에서 사진을 선택하는 메서드
     */
    private fun selectedGallery() {
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        // 권한 확인
        if(writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED){
            // 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
        else{  // 권한이 있는 경우 갤러리 실행
            // 다중이미지 선택
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            pickMultipleMediaRequest.launch(intent) // 갤러리 열기
        }
    }

    /**
     * 선택된 이미지 uri 처리하는 메서드
     */
    private fun handleSelectedImages(data: Intent?) {
        // 다중 이미지 선택 시
        data?.clipData?.let { clipData ->
            for (i in 0 until clipData.itemCount) {
                val uri = clipData.getItemAt(i).uri    // clipData 객체에서 이미지 Uri들을 순차적으로 저장
                displayImage(i, uri)                   // 저장된 uri를 화면에 보여주는 함수 호출
            }
        } ?: data?.data?.let { uri ->     // 단일 이미지 선택시
            displayImage(0, uri)
        }
    }

    /**
     * 이미지 뷰에 선택한 이미지 표시하는 메서드
     */
    private fun displayImage(index: Int, imageUri: Uri?) {
        imageUri?.let { uri ->      // 전달받은 이미지 uri를 이미지 뷰에 할당
            val imageView = when (index) {
                0 -> binding.registImage1
                1 -> binding.registImage2
                2 -> binding.registImage3
                3 -> binding.registImage4
                4 -> binding.registImage5
                else -> null
            }
            imageView?.let {
                Glide.with(this).load(uri).into(it)    // Glide 라이브러리로 이미지뷰에 로드

                // 이미지의 실제 경로 가져오기 & 이미지 파일 변환 및 리스트에 추가
                val file = File(getRealPathFromURI(uri))

                // 경로로부터 RequestBody와 MultipartBody.Part를 생성
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("files", file.name, requestFile)
                imageList.add(part)    // imageList에 추가

                Log.d(TAG, "Image $index path: ${file.path}")
            }
        } ?: run {
            Log.e(TAG, "이미지 Uri이 유효하지 않습니다. :  $index")
        }
    }

    /**
     *  이미지 경로 가져오는 메서드
     */
    private fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER    // 현재 기기의 제조사를 가져옴
        if (buildName.equals("Xiaomi")){      // 샤오미의 경우에는 이미지 경로 처리 방법이 다르기에 경로를 직접 반환
            return uri.path!!
        }

        var columnIndex = 0
        // MediaStore.Images.Media.DATA를 사용하여 커서를 통해 파일의 실제 경로를 조회하고 반환
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        // 이미지 파일의 실제 경로를 얻기 위해 커서를 사용 - 이미지 경로를 이용해 쿼리
        val cursor = contentResolver.query(uri, proj, null, null, null)

        if (cursor!!.moveToFirst()){   // 커서 이동 -> 해당 위치에 데이터가 있는지 확인 후 있으면 true 반환
            // 이미지 파일의 경로를 저장하는 column index 저장
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }

        // 커서에서 가져온 데이터에서 이미지 파일의 실제 경로를 문자열 형태로 가져옴
        val result = cursor.getString(columnIndex)
        cursor.close()   // 데이터 조회가 끝나면 커서를 close -> 리소스 누수 방지

        return result  // 실제 이미지 경로 반환
    }

    fun registerProduct() {
        // 입력된 데이터를 제품 등록 요청 Body형태로 변환
        val productInfo = ProductRegisterRequestBody(
            categoryIds.toString(),
            productName.value.toString(),
            product_text.value.toString(),
            day_price.value?.toInt(),
            time_price.value?.toInt(),
            deposit.value?.toInt(),
            lendingPeriod.value.toString()
        )

        val gson = Gson()
        val json = gson.toJson(productInfo)    // productInfo를 JSON 문자열로 반환(서버로 데이터를 전송하기 위한 형태)

        // requestBody 생성 (Multipart/form-data 형식)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        val productPart = MultipartBody.Part.createFormData("request", null, requestBody)

        Log.d(TAG, "제품정보: $productInfo")
        Log.d(TAG, "이미지리스트 잘들어왔나요?: $imageList")

        // RegisterProduct 클래스의 인스턴스 생성
        val retrofitWork = RegisterProduct(imageList!!, productPart)

        retrofitWork.registerProduct()    // 제품 등록 요청 api 호출

        if (validateInput()) {
            // 제품 등록 완료 후 화면 전환
            val intent = Intent(this, Regist_Finish_Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

