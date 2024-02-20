package com.example.lifesharing.mypage

import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityMyPageBinding
import com.example.lifesharing.databinding.ActivityWriteQnaBinding
import com.example.lifesharing.mypage.mypage_api.InquiryRegisterRequestBody
import com.example.lifesharing.mypage.mypage_api.InquiryRegisterResponseBody
import com.example.lifesharing.mypage.mypage_api.RegisterQnA
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

// Write QnA (문의작성)
class WriteQnAActivity : AppCompatActivity() {

    val TAG: String = "로그"

    private lateinit var binding: ActivityWriteQnaBinding

    var body: MultipartBody.Part? = null

    lateinit var pickMultipleMediaRequest: ActivityResultLauncher<PickVisualMediaRequest>

    var imageList: ArrayList<MultipartBody.Part> = ArrayList()

    var title : MutableLiveData<String> = MutableLiveData("") // reservationId를 받아서 productName에 보여주기
    var content : MutableLiveData<String> = MutableLiveData("")


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_write_qna)

        binding.activity = this
        binding.lifecycleOwner = this

        pickMultipleMediaRequest = registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(5)
        )
        { uris ->
            if (uris.isNotEmpty()) {
                for (uri in uris) {
                    val imagePath: String? = getImagePath(uri)

                    if (imagePath != null) {
                        Log.d(TAG, "pickImage: $uri, $imagePath")

                        val bitmap: Bitmap = ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(contentResolver, uri)
                        )
                        // 디코딩된 이미지를 ImageView에 설정
                        binding.writeQnaUploadedImgIv.setImageBitmap(bitmap)

                        Log.d(TAG, "imagePath: $imagePath")

                        val file = File(imagePath)
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        body = MultipartBody.Part.createFormData("multipartFiles", file.name, requestFile)

                        Log.d(TAG, "body : $body")
                        imageList.add(body!!)

                        Log.d(TAG, "이미지 : $imageList ")
                    }
                }
            } else {
                Log.e(TAG, "이미지 등록 에러")
            }
        }

        val backIv = findViewById<ImageView>(R.id.write_qna_back_iv)
        val registBtn = findViewById<Button>(R.id.write_qna_regist_btn)

        backIv.setOnClickListener {
            // 이미지뷰 클릭 시 MyPageActivity로 이동하는 코드
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        registBtn.setOnClickListener {
            val intent = Intent(this, WriteQnACompleteActivity::class.java)
            startActivity(intent)
        }
    }

    fun getImagePath(uri: Uri?): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri!!, proj, null, null, null)
        val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()

        var result = cursor?.getString(index!!)

        return result!!
    }

    fun pickImage() {
        try {
            pickMultipleMediaRequest.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } catch (e: Exception) {
            Log.d(TAG, "pickImage: ${e.message}")
        }
    }

    fun registerInquiry() {

        val qnaData = InquiryRegisterRequestBody(
            title.value.toString(),
            content.value.toString(),
        )

        Log.d(TAG, "reviewData: $qnaData")

        val retrofitWork = RegisterQnA(qnaData, imageList)

        retrofitWork.registerQna()
    }
}