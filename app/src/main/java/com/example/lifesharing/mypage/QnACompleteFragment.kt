package com.example.lifesharing.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import com.example.lifesharing.mypage.mypage_data.QnACompleteListAdapter
import com.example.lifesharing.mypage.mypage_data.QnAListData

class QnACompleteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QnACompleteListAdapter   // 답변 완료 리스트 리사이클러뷰 어댑터


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qna_complete, container, false)

        // 리사이클러뷰 초기화
        recyclerView = view.findViewById(R.id.qna_complete_rv)
        adapter = QnACompleteListAdapter(getSampleQnAListData()) // 더미 데이터로 어댑터 초기화
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    // 더미 데이터 - 관리자 계정이 따로 없기데 더미데이터로 대체
    private fun getSampleQnAListData(): List<QnAListData> {
        val sampleData = mutableListOf<QnAListData>()
        sampleData.add(QnAListData("문의1", "24.02.01", "이것은 첫 번째 문의 내역입니다."))
        sampleData.add(QnAListData("문의2", "24.02.02", "이것은 두 번째 문의 내역입니다."))
        sampleData.add(QnAListData("문의3", "24.02.03", "이것은 세 번째 문의 내역입니다."))
        sampleData.add(QnAListData("문의4", "24.02.03", "이것은 네 번째 문의 내역입니다."))
        sampleData.add(QnAListData("문의5", "24.02.03", "이것은 다섯 번째 문의 내역입니다."))
        sampleData.add(QnAListData("문의6", "24.02.03", "이것은 여섯 번째 문의 내역입니다."))

        return sampleData
    }
}