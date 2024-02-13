package com.example.lifesharing.regist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityRegistAddBinding
import com.example.lifesharing.product.Product_Detail_Reserve_Activity
import com.example.lifesharing.regist.model.request_body.ProductRegisterRequestBody
import com.example.lifesharing.regist.model.response_body.ProductRegisterResponseBody
import com.example.lifesharing.service.work.RegisterProduct
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import kotlinx.coroutines.*
import okhttp3.ResponseBody

class Regist_Add_Activity : AppCompatActivity() {

    val TAG:String = "로그"


    //private lateinit var pickMedia: ActivityResultLauncher<ActivityResultContracts.PickMultipleVisualMedia>

    var body: MultipartBody.Part?=null

    val imagePaths = mutableListOf<String>()

    var categoryId : Int?=null

    lateinit var pickMultipleMediaRequest: ActivityResultLauncher<PickVisualMediaRequest>

    var productName: MutableLiveData<String> = MutableLiveData("")
    var day_price: MutableLiveData<String> = MutableLiveData("")
    var time_price: MutableLiveData<String> = MutableLiveData("")
    var deposit : MutableLiveData<String> = MutableLiveData("")
    var lendingPeriod : MutableLiveData<String> = MutableLiveData("2.23(월)-4.13(금)(8일)")
    var product_text : MutableLiveData<String> = MutableLiveData("")

    var imageList : ArrayList<MultipartBody.Part> = ArrayList<MultipartBody.Part>()

    lateinit var binding: ActivityRegistAddBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist_add)
        //binding.viewModel = loginViewModel
        binding.activity = this
        binding.lifecycleOwner = this

        //카테고리 전역변수
        val category_checkarray = arrayOf(0,0,0,0,0,0,0,0)


        //송하영 단일 미디어 항목 코드 변형
        pickMultipleMediaRequest = registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(5))
        { uris ->
            if (uris.isNotEmpty()) {

                for (uri in uris) {
                    val imagePath: String? = getImagePath(uri)

                    if (imagePath != null) {
                        Log.d(TAG, "pickImage: $uri")
                        Log.d(TAG, "pickImage: $imagePath 가 잘 들어왔나요 ??")

                        // 이미지 디코딩 화면에 보여주기 위함
                        val bitmap: Bitmap = ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(contentResolver, uri)
                        )

                        // 디코딩된 이미지를 ImageView에 설정
                        binding.registImage1.setImageBitmap(bitmap)

                        // 이미지 경로를 서버에 보낼 준비
                        Log.d(TAG, "imagePath가 잘 찍힐까요? 이거를 서버에 보내주면 되는데~ $imagePath")

                        val file = File(imagePath)
                        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        body = MultipartBody.Part.createFormData("files", file.name, requestFile)

                        Log.d(TAG, "body : $body")
                        imageList.add(body!!)

                        Log.d(TAG, "이미지리스트 들어왔나여ㅛ? $imageList")

                    }
                }

                Log.d(TAG, "onCreate:  ")
            } else {
                Log.d(TAG, "onCreate: ")
            }

            // Intent에서 값 가져오기
            val receivedIntent = intent
            val time = receivedIntent.getIntExtra("시간", 0) // 기본값은 0입니다.
            val setting = receivedIntent.getStringExtra("설정")

//            받아온 값 사용하기
//            예를 들어, TextView에 설정된 시간과 설정 표시
//            val textView = findViewById<TextView>(R.id.textView)
//            textView.text = "설정된 시간: $time 시 $setting"
        }





        binding.registBackBtn.setOnClickListener {
            finish()
        }

        binding.registSetTimeBtn.setOnClickListener {
            val intent = Intent(this, Regist_SetTime_Activity::class.java)
            startActivity(intent)
        }

        binding.registImageIv.setOnClickListener {
            pickImage()
        }
        binding.registBottomBtn.setOnClickListener {
            registerProduct()
        }

        binding.registImage1Xbtn.setOnClickListener {
            binding.registImage1.visibility = View.GONE
            binding.registImage1Xbtn.visibility = View.GONE
        }

        //카테고리ID 지정
        binding.registCategory1.setOnClickListener {
            category_checkarray[0] = 1
            for (i in 0..7) {
                if (i == 0) continue
                else {
                    category_checkarray[i] = 0
                    resetCategory(i)
                }
            }
            Log.d(TAG, "1 click ${categoryId}")
            categoryId = 1
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
            Log.d(TAG, "2 click ${categoryId}")
            categoryId = 2
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
            Log.d(TAG, "3 click ${categoryId}")
            categoryId = 3
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
            Log.d(TAG, "4 click ${categoryId}")
            categoryId = 4
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
            Log.d(TAG, "5 click ${categoryId}")
            categoryId = 5
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
            Log.d(TAG, "6 click ${categoryId}")
            categoryId = 6
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
            Log.d(TAG, "7 click ${categoryId}")
            categoryId = 7
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
            Log.d(TAG, "8 click ${categoryId}")
            categoryId = 8
            binding.registCategory8.setTextColor(Color.parseColor("#1277ED"))
            binding.registCategory8.setBackgroundResource(R.drawable.regist_category_click8)
        }


    }

    fun resetCategory(check : Int) {
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


    fun getImagePath(uri: Uri?): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri!!, proj,null, null, null )
        val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()

        var result = cursor?.getString(index!!)

        return result!!
    }

    fun pickImage() {
        try {
            pickMultipleMediaRequest.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } catch (e: Exception) {
            Log.d(TAG, " ${e.message} ")
        }
    }

    fun registerProduct() {
        val productInfo = ProductRegisterRequestBody(
            categoryId,
            productName.value.toString(),
            product_text.value.toString(),
            day_price.value?.toInt(),
            time_price.value?.toInt(),
            deposit.value?.toInt(),
            lendingPeriod.value.toString()
        )

        Log.d(TAG, "유저정보: $productInfo")

        Log.d(TAG, "이미지리스트 잘들어왔나요?: $imageList")

        val retrofitWork = RegisterProduct(productInfo, imageList)

        retrofitWork.registerProduct()

    }
}
