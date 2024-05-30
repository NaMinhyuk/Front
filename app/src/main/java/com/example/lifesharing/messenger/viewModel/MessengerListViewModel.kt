package com.example.lifesharing.messenger.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MessengerListViewModel(application: Application): AndroidViewModel(application) {

    var showChatActivity: MutableLiveData<Boolean> = MutableLiveData(false)

    fun userChat() {
        showChatActivity.value = true
    }
}