package com.example.lifesharing.messenger.userChat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.databinding.ChatReceiverItemBinding
import com.example.lifesharing.databinding.ChatSenderItemBinding
import com.example.lifesharing.messenger.model.ChatItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** 채팅 메시지를 화면에 표시하는데 사용, 송신자와 수신자에 따라 다른 레이아웃을 사용
 * 현재 사용자의 id를 받아 메시지가 현재 사용자에 의해 송신되었는지 수신되었는지 판단
 */
class MessengerUserChatRecyclerViewAdapter(private val userId: Int) : RecyclerView.Adapter< RecyclerView.ViewHolder>() {

    var chatList : ArrayList<ChatItem> = ArrayList()

    // 송신자 측 ViewHolder
    inner class SenderChatItemViewHolder(private val binding: ChatSenderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // ChatItem 객체를 받아 해당 채팅 데이터를 뷰에 설정
        fun bind(chatItem: ChatItem) {
            val getDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault()) // 서버에서 오는 날짜 형식에 맞춤
            val date = getDateFormat.parse(chatItem.createdAt) // 문자열을 Date 객체로 파싱

            val displayDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())  // 표시할 날짜 형식

            binding.chatSenderMessage.text = chatItem.message
            binding.chatSenderTime.text = displayDateFormat.format(date) // Date 객체를 원하는 형식의 문자열로 변환
        }
    }

    // 수신자 측 ViewHolder
    inner class ReceiverChatItemViewHolder(private val binding: ChatReceiverItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // ChatItem 객체를 받아 해당 채팅 데이터를 뷰에 설정
        fun bind(chatItem: ChatItem) {
            val getDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault()) // 서버에서 오는 날짜 형식에 맞춤
            val date = getDateFormat.parse(chatItem.createdAt) // 문자열을 Date 객체로 파싱

            val displayDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())  // 표시할 날짜 형식

            binding.chatReceiverMessage.text = chatItem.message
            binding.chatReceiverTime.text = displayDateFormat.format(date) // Date 객체를 원하는 형식의 문자열로 변환
        }
    }

    companion object {
        private const val MY_CHAT = 1
        private const val OTHER_CHAT = 2
    }

    // 각 채팅 항목의 뷰 타입을 결정하는 함수
    override fun getItemViewType(position: Int): Int {
        return if (userId == chatList[position].userId?.toInt())  // 사용자 아이디가 메시지 송신자의 ID와 일치한다면 MY_CHAT 반환
            MY_CHAT
        else OTHER_CHAT  // 일치하지 않는다면 OTHER_CHAT 반환

    }

    override fun getItemCount(): Int {
        return chatList.size
    }


    // getItemViewType에서 반환된 결과 값에 따라 호출되는 ViewHolder 구분
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // 사용자 아이디가 메시지 송신자의 ID와 일치
        return if (viewType == MY_CHAT) {
            SenderChatItemViewHolder(   // 송신자 측 화면
                ChatSenderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        // 사용자 아이디가 메시지 송신자의 ID와 불일치
        else {
            ReceiverChatItemViewHolder(   // 수신자 측 화면
                ChatReceiverItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    // 데이터를 바인딩하기 위한 ViewHolder와 바인딩 할 데이터 항목의 위치를 인자로 받음
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = chatList[position]    // 채팅 내역 리스트에서 position에 해당하는 ChatItem 객체를 가져옴

        // getItemViewType을 호출
        if (getItemViewType(position) == MY_CHAT) {   // 현재 사용자가 메시지의 송신자인 경우
            (holder as SenderChatItemViewHolder).bind(item)    // SenderChatItemViewHolder 타입으로 holder를 캐스팅하고 bind 함수를 호출해 뷰에 설정
        }
        else {  // 현재 사용자가 메시지의 수신자인 경우
            (holder as ReceiverChatItemViewHolder).bind(item)  // ReceiverChatItemViewHolder 타입으로 holder를 캐스팅하고 bind 함수를 호출해 뷰에 설정
        }

    }

    // 새로운 채팅을 채팅 목록에 추가하는 함수
    fun addItem(chatItem: ChatItem) {
        chatList.add(chatItem)
        notifyItemInserted(chatList.size - 1)    // 새 채팅이 리스트의 마지막 위치에 삽입됨을 알림 (chatList.size-1를 통해 항상 리스트의 마지막에 추가되도록)
    }

    fun updateList(newList: List<ChatItem>) {
        // 날짜 형식을 파싱할 준비
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())

        // 날짜로 정렬 (최신 메시지가 리스트의 마지막에 오도록)
        val sortedList = newList.sortedBy { chatItem ->
            dateFormat.parse(chatItem.createdAt) // createdAt을 Date 객체로 변환
        }

        chatList.clear()
        chatList.addAll(sortedList)   // 기존 목록을 업데이트
        notifyDataSetChanged()        // 어댑터에 전체 데이터가 변경됨을 알림
    }

    // 마지막 아이템을 업데이트하는 함수
    fun updateLastItem(chatItem: ChatItem) {
        if (chatList.isNotEmpty()) {
            chatList[chatList.size - 1] = chatItem
            notifyItemChanged(chatList.size - 1)
        }
    }
}