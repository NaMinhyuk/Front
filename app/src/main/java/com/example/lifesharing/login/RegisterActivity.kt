package com.example.lifesharing.login

import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Path
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivityRegisterBinding
import com.example.lifesharing.login.model.request_body.RegisterRequestBody
import com.example.lifesharing.login.viewModel.RegisterViewModel
import com.example.lifesharing.service.work.RegisterWork
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RegisterActivity : AppCompatActivity() {

    val TAG: String = "로그"
    lateinit var binding: ActivityRegisterBinding
    val registerViewModel : RegisterViewModel by viewModels()

     var imagePath: String?=null

    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")
    var name: MutableLiveData<String> = MutableLiveData("")
    var checkPassword: MutableLiveData<String> = MutableLiveData("")
    var phone: MutableLiveData<String> = MutableLiveData("")
    var verifiedNumber: MutableLiveData<String> = MutableLiveData("")

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    lateinit var mediaPath: Path

    lateinit var body: MultipartBody.Part
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = registerViewModel
        binding.activity = this
        binding.lifecycleOwner = this

        pickMedia = registerForActivityResult(/* contract = */
            ActivityResultContracts.PickVisualMedia()) /* callback = */
        {uri ->
            if (uri != null) {
                Log.d(TAG, "pickImage: $uri")
                imagePath = uri.toString()
                Log.d(TAG, "pickImage: $imagePath 가 잘 들어왔나요 ??")
                //var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imagePath!!.toUri())
                var bitmap: Bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, imagePath!!.toUri()))
                binding.userProfile.setImageBitmap(bitmap)

                var imagePath : String = getImagePath(uri)

                Log.d(TAG, "imagePath가 잘찍할까요 ?? 이거를 서버에 보내주면 되는데~ $imagePath ")

                val file = File(imagePath)
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                body = MultipartBody.Part.createFormData("multipartFile", file.name, requestFile)

            } else {
                Log.d(TAG, "pickImage: no media selected")
            }
        }

        setObserve()
    }

    fun registerWithImage() {
        val registerUserData = RegisterRequestBody(
            email.value.toString(),
            password.value.toString(),
            name.value.toString(),
            phone.value.toString()
        )


        val retrofitWork = RegisterWork(registerUserData, body)
        retrofitWork.registerWork()
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
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } catch (e: Exception) {
            Log.d(TAG, "${e.message}")
        }

    }



    fun setObserve() {
        registerViewModel.showLoginActivity.observe(this) {
            if (it) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}
