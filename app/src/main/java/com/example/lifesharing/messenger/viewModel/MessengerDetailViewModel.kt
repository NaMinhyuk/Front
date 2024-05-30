package com.example.lifesharing.messenger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.BuildConfig
// import com.gmail.bishoybasily.stomp.lib.Event
import okhttp3.OkHttpClient


class MessengerDetailViewModel(application: Application): AndroidViewModel(application) {

    var userName: MutableLiveData<String> = MutableLiveData("")

    private val BASE_URL = BuildConfig.BASE_URLS

    val url = "wss://$BASE_URL/stomp/chat"

    val intervalMills = 1000L

    val client = OkHttpClient()

//    val stompClient = com.gmail.bishoybasily.stomp.lib.StompClient(client, intervalMills)
//
//    var stompConnection = stompClient.connect().subscribe {
//        when (it.type) {
//            Event.Type.OPENED -> {
//
//            }
//
//            Event.Type.CLOSED -> {
//
//            }
//            Event.Type.ERROR -> {
//
//            }
//
//            else -> {}
//        }
//    }


 //stomp는 진짜 몰겠다 ㅋㅋ
//    fun runStomp(roomId: Int, UserId: Int) {
//        stompClient.connect()
//    }
//
//    fun getChatRoomList() {
//
//    }
}