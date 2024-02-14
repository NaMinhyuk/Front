package com.example.lifesharing.mypage.review

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityRegisterBinding
import com.example.lifesharing.databinding.ActivityRegisterReviewBinding
import com.example.lifesharing.mypage.review.model.request_body.ReviewRequestBody
import com.example.lifesharing.service.work.RegisterReviewWork
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class RegisterReviewActivity : AppCompatActivity() {

    val TAG: String = "로그"

    var startCount : Int=0

    var productName : MutableLiveData<String> = MutableLiveData("") // reservationId를 받아서 productName에 보여주기
    var content : MutableLiveData<String> = MutableLiveData("")

    var contentLength : MutableLiveData<String> = MutableLiveData("")
    var body: MultipartBody.Part?=null

    lateinit var binding : ActivityRegisterReviewBinding

    lateinit var pickMultipleMediaRequest: ActivityResultLauncher<PickVisualMediaRequest>

    var imageList : ArrayList<MultipartBody.Part> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_review)

        binding.activity = this
        binding.lifecycleOwner = this

        pickMultipleMediaRequest = registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(5)
        ) {
            uris ->
                if (uris.isNotEmpty()) {
                    for (uri in uris) {
                        val imagePath: String? = getImagePath(uri)

                        if (imagePath != null) {
                            Log.d(TAG, "pickImage: $uri, $imagePath")

                            val bitmap: Bitmap = ImageDecoder.decodeBitmap(
                                ImageDecoder.createSource(contentResolver, uri)
                            )
                            // 디코딩된 이미지를 ImageView에 설정
                            binding.registImage1.setImageBitmap(bitmap)

                            Log.d(TAG, "imagePath: $imagePath")

                            val file = File(imagePath)
                            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                            body = MultipartBody.Part.createFormData("files", file.name, requestFile)

                            Log.d(TAG, "body : $body")
                            imageList.add(body!!)

                            Log.d(TAG, "이미지 : $imageList ")
                        }
                    }
                }
            else {
                    Log.e(TAG, "이미지 등록 에러")
            }
        }

        binding.reviewStartIv1.setOnClickListener {

            startCount = 1

            binding.reviewStartIv1.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv2.setImageResource(R.drawable.review_star)
            binding.reviewStartIv3.setImageResource(R.drawable.review_star)
            binding.reviewStartIv4.setImageResource(R.drawable.review_star)
            binding.reviewStartIv5.setImageResource(R.drawable.review_star)
            binding.reviewStartCount.text = "(1/5)"
        }

        binding.reviewStartIv2.setOnClickListener {

            startCount = 2

            binding.reviewStartIv1.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv2.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv3.setImageResource(R.drawable.review_star)
            binding.reviewStartIv4.setImageResource(R.drawable.review_star)
            binding.reviewStartIv5.setImageResource(R.drawable.review_star)
            binding.reviewStartCount.text = "(2/5)"
        }

        binding.reviewStartIv3.setOnClickListener {

            startCount = 3

            binding.reviewStartIv1.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv2.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv3.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv4.setImageResource(R.drawable.review_star)
            binding.reviewStartIv5.setImageResource(R.drawable.review_star)
            binding.reviewStartCount.text = "(3/5)"
        }

        binding.reviewStartIv4.setOnClickListener {

            startCount = 4

            binding.reviewStartIv1.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv2.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv3.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv4.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv5.setImageResource(R.drawable.review_star)
            binding.reviewStartCount.text = "(4/5)"
        }

        binding.reviewStartIv5.setOnClickListener {

            startCount = 5

            binding.reviewStartIv1.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv2.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv3.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv4.setImageResource(R.drawable.colored_star)
            binding.reviewStartIv5.setImageResource(R.drawable.colored_star)
            binding.reviewStartCount.text = "(5/5)"
        }



    }

    fun getImagePath(uri: Uri?): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri!!, proj, null, null,null)
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

    fun registerReview() {

        val reviewData = ReviewRequestBody(
            score = startCount,
            content = content.value.toString()
        )

        Log.d(TAG, "reviewData: $reviewData")

        val retrofitWork = RegisterReviewWork(28, reviewData, imageList)

        retrofitWork.registerReview()
    }

}