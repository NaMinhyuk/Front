package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.GlobalApplication
import com.example.lifesharing.R
import com.example.lifesharing.mypage.mypage_api.InquiryDTO
import com.example.lifesharing.mypage.mypage_data.QnAWaitListAdapter
import com.example.lifesharing.mypage.mypage_data.QnAListData

class QnAWaitingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QnAWaitListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_qna_waiting, container, false)

        // 리사이클러뷰 초기화
        recyclerView = view.findViewById(R.id.qna_waiting_rv)
        adapter = QnAWaitListAdapter(GlobalApplication.getQnaListData()) // 더미 데이터로 어댑터 초기화
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    private fun onItemClick(qnaItem: InquiryDTO) {
        val intent = Intent(requireContext(), QnA_Answer_Activity::class.java)
        intent.putExtra("inquiryId", qnaItem.inquiryId)
        startActivity(intent)
    }

    // 더미 데이터
//    private fun getSampleQnAListData(): ArrayList<InquiryDTO> {
//        val sampleData = mutableListOf<QnAListData>()
//        sampleData.add(QnAListData("문의1", "24.02.01", "이것은 첫 번째 문의 내역입니다."))
//        sampleData.add(QnAListData("문의2", "24.02.02", "이것은 두 번째 문의 내역입니다."))
//        sampleData.add(QnAListData("문의3", "24.02.03", "이것은 세 번째 문의 내역입니다."))
//        sampleData.add(QnAListData("문의4", "24.02.03", "이것은 네 번째 문의 내역입니다."))
//        sampleData.add(QnAListData("문의5", "24.02.03", "이것은 다섯 번째 문의 내역입니다."))
//        sampleData.add(QnAListData("문의6", "24.02.03", "이것은 여섯 번째 문의 내역입니다."))
//
//        var listData = GlobalApplication.getQnaListData()
//
//        return
//    }
}
