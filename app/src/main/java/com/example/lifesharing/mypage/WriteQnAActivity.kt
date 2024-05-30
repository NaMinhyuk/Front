package com.example.lifesharing.mypage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lifesharing.databinding.ActivityWriteQnaBinding
import com.example.lifesharing.mypage.model.request_body.QnaRequest
import com.example.lifesharing.mypage.viewModel.QnaWriteViewModel
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

// Write QnA (문의작성)
class WriteQnAActivity : AppCompatActivity() {

    val TAG:String = "로그"

    private lateinit var viewModel: QnaWriteViewModel
    private lateinit var binding: ActivityWriteQnaBinding
    var imageList : ArrayList<MultipartBody.Part> = ArrayList()

    // 갤러리에서 이미지 선택 결과를 처리할 객체 선언
    private lateinit var imageResult: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWriteQnaBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기
        binding.writeQnaBackIv.setOnClickListener {
            finish()
        }

        // 문의 작성하기
        binding.btnDone.setOnClickListener {
            writeQna()
        }

        // 이미지 선택
        binding.btnQnaImage.setOnClickListener {
            selectedGallery()     // 갤러리에서 사진 선택하는 함수 호출
        }

        imageResult = registerForActivityResult( //oncreate에서만 정의가능 -> 모듈화 불가능
            ActivityResultContracts.StartActivityForResult()
        ){ result ->

            // 다중 이미지 선택
            if (result.resultCode == RESULT_OK) {
                handleSelectedImages(result.data)
            }
        }

        // 뷰모델 초기화
         viewModel = ViewModelProvider(this).get(QnaWriteViewModel::class.java)

        // 작성 결과 관찰
        viewModel.resultLiveData.observe(this){ isSuccess ->
            if (isSuccess){     // 작성 결과가 true일 경우
                Log.d(TAG, "문의 작성 성공 !!!!")
                // 작성이 성공적으로 완료되면 화면 전환 후 액티비티 종료
                val intent = Intent(this, QnAWriteDoneActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Log.e(TAG, "문의 작성 실패 !!!!")
                Toast.makeText(this, "문의 작성 실패. 다시 시도해주세요. ${isSuccess}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleSelectedImages(data: Intent?) {
        // 다중 이미지 선택 시
        data?.clipData?.let { clipData ->
            for (i in 0 until clipData.itemCount) {
                val uri = clipData.getItemAt(i).uri  // clipData 객체에서 이미지 Uri들을 순차적으로 저장
                displayImage(i, uri)                 // 저장된 uri를 화면에 보여주는 함수 호출
            }
        } ?: data?.data?.let { uri ->     // 단일 이미지 선택시
            displayImage(0, uri)
        }
    }

    // 이미지 뷰에 선택한 이미지 표시
    private fun displayImage(index: Int, imageUri: Uri?) {
        imageUri?.let { uri ->    // 전달받은 이미지 uri를 이미지 뷰에 할당
            val imageView = when (index) {
                0 -> binding.qnaImage1
                1 -> binding.qnaImage2
                2 -> binding.qnaImage3
                3 -> binding.qnaImage4
                4 -> binding.qnaImage5
                else -> null
            }
            imageView?.let {
                Glide.with(this).load(uri).into(it)     // Glide 라이브러리로 이미지뷰에 로드

                // 이미지의 실제 경로 가져오기 & 이미지 파일 변환 및 리스트에 추가
                val file = File(getRealPathFromURI(uri))
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())    // 경로로부터 RequestBody와 MultipartBody.Part를 생성
                val part = MultipartBody.Part.createFormData("multipartFiles", file.name, requestFile)
                imageList.add(part)    // imageList에 추가

                Log.d(TAG, "Image $index path: ${file.path}")
            }
        } ?: run {
            Log.e(TAG, "이미지 Uri이 유효하지 않습니다. : $index")
        }
    }

    private fun selectedGallery() {
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        // 권한 확인
        if(writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED){
            // 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
        else{
            // 권한이 있는 경우 갤러리 실행

            // 다중이미지 선택
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            imageResult.launch(intent) // 갤러리 열기
        }
    }

    // 이미지 경로 가져오는 함수
    private fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER    // 현재 기기의 제조사를 가져옴
        if (buildName.equals("Xiaomi")){      // 샤오미의 경우에는 이미지 경로 처리 방법이 다르기에 경로를 직접 반환
            return uri.path!!
        }

        var columnIndex = 0
        // MediaStore.Images.Media.DATA를 사용하여 커서를 통해 파일의 실제 경로를 조회하고 반환
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        // 이미지 파일의 실제 경로를 얻기 위해 커서를 사용
        val cursor = contentResolver.query(uri, proj, null, null, null)  // 이미지 경로를 이용해 쿼리

        if (cursor!!.moveToFirst()){   // 커서 이동 -> 해당 위치에 데이터가 있는지 확인 후 있으면 true 반환
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)    // 이미지 파일의 경로를 저장하는 column index 저장
        }

        // 커서에서 가져온 데이터 중에서 이미지 파일의 실제 경로를 문자열 형태로 가져옴
        val result = cursor.getString(columnIndex)
        cursor.close()    // 데이터 조회가 끝나면 커서를 close -> 리소스 누수 방지

        return result     // 실제 이미지 경로 반환
    }

    private fun writeQna() {
        // 입력된 데이터를 문의 작성 요청 Body형태로 변환
        val qnaContent = QnaRequest(
            binding.qnaTitle.text.toString(),
            binding.qnaBody.text.toString()
        )

        val gson = Gson()
        val json = gson.toJson(qnaContent)  // QnaRequest 객체를 JSON으로 변환(서버로 데이터를 전송하기 위한 형태)

        // requestBody 생성 (Multipart/form-data 형식)
        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        val inquiryPart = MultipartBody.Part.createFormData("inquiryDTO", null, requestBody)  // Part 생성

        viewModel.writeQna(inquiryPart, imageList)  // 문의 작성 요청 api 호출

        /**
         * 위 주석 부분만 없애면 문의작성하기 api 연결 및 구현 완료
         *
         * 24.05.07
         * 현재 로컬에서는 문의작성하기가 가능하나 원격에서는 POST시 403에러 발생
         * 해결을 위해 임시로 작성 완료 시 화면 전환이 되도록 해 놓음
         *
         * 24.05.09 주석 처리 제거 : 오류발생
         * "result":"Cannot invoke \"java.util.List.iterator()\" because \"multipartFiles\" is null"
         *
         * 24.05.09 해결 완료
         */

    }
}