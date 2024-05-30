package com.example.lifesharing.messenger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.messenger.model.response_body.CreateChatRoomResponse
import com.example.lifesharing.messenger.model.response_body.MessengerRoomListTempResponseBody
import com.example.lifesharing.service.api.RetrofitAPIwithToken
import retrofit2.Call
import retrofit2.Response

class MakeRoomViewModel(application: Application): AndroidViewModel(application) {

    // Retrofit을 사용하여 HTTP 요청 수행
    private val service = RetrofitAPIwithToken.retrofit()

    val roomCreationStatus = MutableLiveData<Boolean>()    // 채팅방 생성 결과 관찰 객체
    val errorMessage = MutableLiveData<String>()           // 채팅방 생성 실패 시 에러 메시지 전달 객체
    val existingRoomId = MutableLiveData<Long?>()          // 기존에 존재하는 채팅방 ID 관찰 객체

    val TAG : String = "로그"

    /** 채팅방 생성 요청 - 송신자의 ID와 제품 ID를 인자로 받아 채팅방 생성 API 요청을 보냄 */
    fun createChatRoom(sender : Long, productId : Long){
        service.createChatRoom(sender, productId).enqueue(object : retrofit2.Callback<CreateChatRoomResponse>{
            override fun onResponse(
                call: Call<CreateChatRoomResponse>,
                response: Response<CreateChatRoomResponse>
            ) {
                // 채팅방 생성 요청 성공 시
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    roomCreationStatus.value = true
                    existingRoomId.value = response.body()?.result?.roomId  // 채팅방 생성 성공 시 roomId 설정
                }
                // 채팅방 생성 요청 실패 시
                else {
                    errorMessage.value = "채팅방 생성 실패: ${response.message()}"
                    roomCreationStatus.value = false
                }
            }

            override fun onFailure(call: Call<CreateChatRoomResponse>, t: Throwable) {
                errorMessage.value = "네트워크 오류: ${t.message}"
                roomCreationStatus.value = false
            }

        })
    }

    /** 해당 제품에 대한 채팅방을 만들지 결정하는 함수 */
    fun handleChatRoomCreation(sender: Long, productId: Long) {
        // 먼저 기존의 채팅방이 있는지 확인
        checkExistingRoom(sender, productId) { exists, roomId ->
            if (exists && roomId != null) {
                // 존재하는 채팅방 ID를 LiveData로 전달
                existingRoomId.value = roomId
            } else {
                // 채팅방 생성
                createChatRoom(sender, productId)
            }
        }
    }

    /** 해당 제품에 대한 채팅방이 존재하는지 확인하는 함수 */
    private fun checkExistingRoom(userId: Long, productId: Long, callback: (Boolean, Long?) -> Unit) {

        // 제품 아이디와 사용자 아아디를 인자로 받아 해당 사용자의 모든 채팅방 목록을 조회
        service.getMessengerRoomListTemp(userId).enqueue(object : retrofit2.Callback<MessengerRoomListTempResponseBody> {
            override fun onResponse(call: Call<MessengerRoomListTempResponseBody>, response: Response<MessengerRoomListTempResponseBody>) {
                if (response.isSuccessful) {   // 성공적으로 조회가 되었을 경우
                    val room = response.body()?.result?.find { it.productId?.toLong() == productId }   // ResponseBody에서 제품 아이디에 해당하는 채팅방을 찾음
                    callback(room != null, room?.roomId?.toLong())    // 존재여부와 함께 콜백 함수 호출
                } else {
                    errorMessage.value = "채팅방 확인 실패: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<MessengerRoomListTempResponseBody>, t: Throwable) {
                errorMessage.value = "네트워크 오류: ${t.message}"
                callback(false, null)
            }
        })
    }
}