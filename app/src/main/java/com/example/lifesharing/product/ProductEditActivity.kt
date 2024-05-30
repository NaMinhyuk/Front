package com.example.lifesharing.product

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityProductEditBinding
import com.example.lifesharing.product.data.UpdateRequestData
import com.example.lifesharing.product.view_model.ProductImageViewModel
import com.example.lifesharing.product.view_model.ProductInfoViewModel
import com.example.lifesharing.product.view_model.ProductUpdateViewModel
import com.example.lifesharing.regist.Regist_SetTime_Activity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/** 제품 정보 수정 View */
class ProductEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductEditBinding
    private lateinit var infoViewModel : ProductInfoViewModel     // 제품 수정 정보 요청을 위함
    private lateinit var updateVieModel : ProductUpdateViewModel  // 제품 수정 요청을 위함
    private lateinit var updateImageViewModel : ProductImageViewModel
    val TAG : String = "제품 수정 로그"

    var productId : Long ?= null

    var categoryIds : String?=null
    //var deposit : MutableLiveData<String> = MutableLiveData("")

    var imageList : ArrayList<MultipartBody.Part> = ArrayList()

    private lateinit var pickDateResultLauncher: ActivityResultLauncher<Intent>
    lateinit var pickMultipleMediaRequest: ActivityResultLauncher<Intent>

    // 제품 정보, 이미지 수정 성공 여부를 받아오기 위한 객체 선언
    private var isInfoUpdateSuccess: Boolean = false
    private var isImageUpdateSuccess: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 제품 아이디 인텐트로 받아오기
        productId = intent.getLongExtra("productId", -1)

        // 제품 수정을 위한 정보 요청 뷰모델 초기화
        infoViewModel = ViewModelProvider(this).get(ProductInfoViewModel::class.java)
        // 제품 정보 수정 뷰모델 초기화
        updateVieModel = ViewModelProvider(this).get(ProductUpdateViewModel::class.java)
        // 제품 이미지 수정 뷰모델 초기화
        updateImageViewModel = ViewModelProvider(this).get(ProductImageViewModel::class.java)


        infoViewModel.getProductEditInfo(productId!!)     // 제품 아이디에 해당하는 정보 요청

        infoViewModel.productData.observe(this, Observer { result ->     // 요청 결과로 얻은 제품 정보 로드
            // 로드된 데이터를 view에 표시
            binding.productEditName.text = Editable.Factory.getInstance().newEditable(result.name)
            binding.productEditDayPrice.text = Editable.Factory.getInstance().newEditable(result.dayPrice.toString())
            binding.productEditHourPrice.text = Editable.Factory.getInstance().newEditable(result.houPrice.toString())
            binding.productEditDeposit.text = Editable.Factory.getInstance().newEditable(result.deposit.toString())
            binding.productEditSetTime.text = Editable.Factory.getInstance().newEditable(result.lendingPeriod)
            binding.productEditContent.text = Editable.Factory.getInstance().newEditable(result.content)
        })


        // 수정하기 버튼 클릭
        binding.btnEditDone.setOnClickListener {
            updateProductInfo()     // 제품 정보 수정 요청
            updateProductImage()    // 제품 이미지 수정 요청
        }

        // 제품 정보 수정 결과 관찰
        updateVieModel.updateResult.observe(this, Observer { isSuccess ->
            isInfoUpdateSuccess = isSuccess
            if (isSuccess) {
                checkBothUpdatesCompleted()
            } else {
                Log.e(TAG, "제품 정보 수정 실패")
            }
        })


        // 뒤로가기
        binding.editBackBtn.setOnClickListener {
            finish()
        }


        // 대여기간 날짜 지정
        binding.productEditSetTime.setOnClickListener {
            val intent = Intent(this, Regist_SetTime_Activity::class.java)
            pickDateResultLauncher.launch(intent)
        }

        // 날짜 선택 결과를 처리하는 런처 초기화
        pickDateResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // 선택된 기간 받기
                    val selectedPeriod = result.data?.getStringExtra("selectedPeriod")
                    // EditText 뷰에 표시
                    binding.productEditSetTime.text = selectedPeriod
                }
            }



        //카테고리 전역변수
        val category_checkarray = arrayOf(0, 0, 0, 0, 0, 0, 0, 0)

        //카테고리ID 지정
        binding.productCategory1.setOnClickListener {
            category_checkarray[0] = 1      // 배열의 첫번째 요소 = 1번 카테고리 -> 선택될 경우 1로 set
            for (i in 0..7) {         // 0부터 7번 인덱스까지 반복
                if (i == 0) continue        // 현재 인덱스가 1이라면 else문 생략

                else {      // 다른 카테고리가 선택되었을 경우
                    category_checkarray[i] = 0     // 현재 인덱스가 1이 아니라면 해당 인덱스 0으로 set
                    resetCategory(i)        // 카테고리 선택이 해제될 때 호출
                }
            }

            // 현재 인덱스가 1일 때 진행
            categoryIds = "1"
            Log.d(TAG + " 카테고리", "categoryId 1 : click ${categoryIds}")
            binding.productCategory1.setTextColor(Color.parseColor("#1277ED"))     // 카테고리 뷰 텍스트, 배경 색 변경
            binding.productCategory1.setBackgroundResource(R.drawable.regist_category_click1)
        }
        binding.productCategory2.setOnClickListener {
            category_checkarray[1] = 1
            for (i in 0..7) {
                if (i == 1) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "2"
            Log.d(TAG + " 카테고리", "categoryId 2 : click ${categoryIds}")
            binding.productCategory2.setTextColor(Color.parseColor("#1277ED"))
            binding.productCategory2.setBackgroundResource(R.drawable.regist_category_click2)
        }
        binding.productCategory3.setOnClickListener {
            category_checkarray[2] = 1
            for (i in 0..7) {
                if (i == 2) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "3"
            Log.d(TAG + " 카테고리", "categoryId 3 : click ${categoryIds}")
            binding.productCategory3.setTextColor(Color.parseColor("#1277ED"))
            binding.productCategory3.setBackgroundResource(R.drawable.regist_category_click3)
        }
        binding.productCategory4.setOnClickListener {
            category_checkarray[3] = 1
            for (i in 0..7) {
                if (i == 3) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "4"
            Log.d(TAG + " 카테고리", "categoryId 4 : click ${categoryIds}")
            binding.productCategory4.setTextColor(Color.parseColor("#1277ED"))
            binding.productCategory4.setBackgroundResource(R.drawable.regist_category_click4)
        }
        binding.productCategory5.setOnClickListener {
            category_checkarray[4] = 1
            for (i in 0..7) {
                if (i == 4) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "5"
            Log.d(TAG + " 카테고리", "categoryId 5 :  click ${categoryIds}")
            binding.productCategory5.setTextColor(Color.parseColor("#1277ED"))
            binding.productCategory5.setBackgroundResource(R.drawable.regist_category_click5)
        }
        binding.productCategory6.setOnClickListener {
            category_checkarray[5] = 1
            for (i in 0..7) {
                if (i == 5) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "6"
            Log.d(TAG + " 카테고리", "categoryId 6 : click ${categoryIds}")
            binding.productCategory5.setTextColor(Color.parseColor("#1277ED"))
            binding.productCategory5.setBackgroundResource(R.drawable.regist_category_click6)
        }
        binding.productCategory7.setOnClickListener {
            category_checkarray[6] = 1
            for (i in 0..7) {
                if (i == 6) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "7"
            Log.d(TAG + " 카테고리", "categoryId 7 : click ${categoryIds}")
            binding.productCategory5.setTextColor(Color.parseColor("#1277ED"))
            binding.productCategory5.setBackgroundResource(R.drawable.regist_category_click7)
        }
        binding.productCategory8.setOnClickListener {
            category_checkarray[7] = 1
            for (i in 0..7) {
                if (i == 7) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            categoryIds = "8"
            Log.d(TAG + " 카테고리", "categoryId 8 : click ${categoryIds}")
            binding.productCategory8.setTextColor(Color.parseColor("#1277ED"))
            binding.productCategory8.setBackgroundResource(R.drawable.regist_category_click8)
        }


        // 사진 선택 버튼(갤러리 Open)
        binding.productEditImageIv.setOnClickListener {
            selectedGallery()
        }

        pickMultipleMediaRequest = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        { result ->
            if (result.resultCode == RESULT_OK) {
                handleSelectedImages(result.data)
            }
        }

        // 제품 이미지 수정 결과 관찰
        updateImageViewModel.updateImageResult.observe(this, Observer { isSuccess ->
            isImageUpdateSuccess = isSuccess
            if (isSuccess) {
                checkBothUpdatesCompleted()
            } else {
                Log.e(TAG + " 이미지", "제품 이미지 수정 실패")
            }
        })
    }

    private fun checkBothUpdatesCompleted() {
        if (isInfoUpdateSuccess){           // 제품 정보만 수정하는 경우
            val intent = Intent(this, EditDoneActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if (isImageUpdateSuccess){     // 제품 이미지만 수정하는 경우
            val intent = Intent(this, EditDoneActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if (isInfoUpdateSuccess && isImageUpdateSuccess) {
            // 두 수정 작업이 모두 성공한 경우
            val intent = Intent(this, EditDoneActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun handleSelectedImages(data: Intent?) {
        // 사용자가 다중 이미지를 선택했을 경우, 이미지의 URI가 ClipData 객체에 포함
        data?.clipData?.let { clipData ->
            for (i in 0 until clipData.itemCount) {  // 각 이미지의 URI를 순차적으로 저장
                val uri = clipData.getItemAt(i).uri        // i번째 이미지의 URI를 객체에 저장
                displayImage(i, uri)    // 해당 이미지를 뷰에 표시하기 위해 함수 호출
            }
        } //사용자가 단일 이미지만 선택했을 경우
            ?: data?.data?.let { uri ->
            displayImage(0, uri)  // 해당 이미지를 뷰에 표시하기 위해 함수 호출
        }
    }

    // 이미지 뷰에 선택한 이미지 표시
    private fun displayImage(index: Int, imageUri: Uri?) {
        imageUri?.let { uri ->     // 전달받은 이미지 uri를 이미지 뷰에 할당
            val imageView = when (index) {
                0 -> binding.editImage1
                1 -> binding.editImage2
                2 -> binding.editImage3
                3 -> binding.editImage4
                4 -> binding.editImage5
                else -> null
            }
            imageView?.let {
                Glide.with(this).load(uri).into(it)    // Glide 라이브러리로 이미지뷰에 로드
                // 이미지의 실제 경로 가져오기 & 이미지 파일 변환 및 리스트에 추가
                val file = File(getRealPathFromURI(uri))
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())   // 경로로부터 RequestBody와 MultipartBody.Part를 생성
                val part = MultipartBody.Part.createFormData("files", file.name, requestFile)
                imageList.add(part)    // imageList에 추가

                Log.d(TAG + " 이미지", "Image $index path: ${file.path}")
            }
        } ?: run {
            Log.e(TAG + " 이미지", "이미지 Uri이 유효하지 않습니다. : $index")
        }
    }

    // 이미지 경로 가져오는 함수
    private fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER   // 현재 기기의 제조사를 가져옴
        if (buildName.equals("Xiaomi")){     // 샤오미의 경우에는 이미지 경로 처리 방법이 다르기에 경로를 직접 반환
            return uri.path!!
        }

        var columnIndex = 0
        // MediaStore.Images.Media.DATA를 사용하여 커서를 통해 파일의 실제 경로를 조회하고 반환
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, proj, null, null, null)    // 이미지 경로를 이용해 쿼리

        if (cursor!!.moveToFirst()){    // 커서 이동 -> 해당 위치에 데이터가 있는지 확인 후 있으면 true 반환
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)     // 이미지 파일의 경로를 저장하는 column index 저장
        }

        // 커서에서 가져온 데이터에서 이미지 파일의 실제 경로를 문자열 형태로 가져옴
        val result = cursor.getString(columnIndex)
        cursor.close()    // 데이터 조회가 끝나면 커서를 close -> 리소스 누수 방지

        return result     // 실제 이미지 경로 반환
    }

    private fun selectedGallery() {
        // 다중이미지 선택
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        pickMultipleMediaRequest.launch(intent) // 갤러리 열기
    }

    // 제품 정보 수정
    private fun updateProductInfo() {
        // 사용자 입력 수집
        val productName = binding.productEditName.text.toString()
        val dayPrice = binding.productEditDayPrice.text.toString().toInt()
        val hourPrice = binding.productEditHourPrice.text.toString().toInt()
        val deposit = binding.productEditDeposit.text.toString().toInt()
        val lendingPeriod = binding.productEditSetTime.text.toString()
        val content = binding.productEditContent.text.toString()

        // API 요청 데이터 생성
        val updateData = UpdateRequestData(
            categoryIds.toString(),
            productName,
            content,
            dayPrice,
            hourPrice,
            deposit,
            lendingPeriod
        )

        // ViewModel을 통해 제품 정보 수정 요청
        updateVieModel.updateProductInfo(productId!!, updateData)
    }

    private fun updateProductImage() {
        if (imageList.isNotEmpty()){
            updateImageViewModel.updateProductImage(productId!!, imageList)
        }
    }

    private fun resetCategory(check : Int) {
        when (check) {
            0 -> {
                binding.productCategory1.setTextColor(Color.parseColor("#5C5D61"))
                binding.productCategory1.setBackgroundResource(R.drawable.regist_category_1)

            }
            1 -> {
                binding.productCategory2.setTextColor(Color.parseColor("#5C5D61"))
                binding.productCategory2.setBackgroundResource(R.drawable.regist_category_2)

            }
            2 -> {
                binding.productCategory3.setTextColor(Color.parseColor("#5C5D61"))
                binding.productCategory3.setBackgroundResource(R.drawable.regist_category_3)

            }
            3 -> {
                binding.productCategory4.setTextColor(Color.parseColor("#5C5D61"))
                binding.productCategory4.setBackgroundResource(R.drawable.regist_category_4)

            }
            4 -> {
                binding.productCategory5.setTextColor(Color.parseColor("#5C5D61"))
                binding.productCategory5.setBackgroundResource(R.drawable.regist_category_5)

            }
            5 -> {
                binding.productCategory6.setTextColor(Color.parseColor("#5C5D61"))
                binding.productCategory6.setBackgroundResource(R.drawable.regist_category_6)

            }
            6 -> {
                binding.productCategory7.setTextColor(Color.parseColor("#5C5D61"))
                binding.productCategory7.setBackgroundResource(R.drawable.regist_category_7)

            }
            7 -> {
                binding.productCategory8.setTextColor(Color.parseColor("#5C5D61"))
                binding.productCategory8.setBackgroundResource(R.drawable.regist_category_8)

            }
        }
    }
}