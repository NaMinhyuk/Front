package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.databinding.FragmentQnaWaitingBinding
import com.example.lifesharing.mypage.model.response_body.InquiryItem
import com.example.lifesharing.mypage.mypage_data.QnaListClickListener
import com.example.lifesharing.mypage.mypage_data.QnaWaitListAdapter
import com.example.lifesharing.mypage.viewModel.QnaListViewModel

class QnAWaitingFragment : Fragment(), QnaListClickListener {

    private lateinit var binding: FragmentQnaWaitingBinding
    private lateinit var adapter: QnaWaitListAdapter   // 문의 답변 대기 리스트 리사이클러뷰 어댑터
    private lateinit var viewModel: QnaListViewModel   // 문의 목록 조회 ViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentQnaWaitingBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val qnaListItem = ArrayList<InquiryItem>()   // 문의 아이템 리스트를 초기화

        recyclerView = binding.inquiryList    // 문의 리사이클러뷰 할당
        recyclerView.layoutManager = LinearLayoutManager(context)   // 레이아웃 매니저 설정

        adapter = QnaWaitListAdapter(qnaListItem, this)   // 문의 목록 리사이클러뷰 어댑터 설정
        recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()

        // 뷰모델 초기화
        viewModel = ViewModelProvider(requireActivity()).get(QnaListViewModel::class.java)

        // 문의 리스트 - 결과를 관찰하여 변경된 데이터로 어댑터 업대이트
        viewModel.qnaListItem.observe(viewLifecycleOwner, Observer { items ->
            adapter.setItem(ArrayList(items))
        })

        if (viewModel.qnaListItem.value.isNullOrEmpty()) {
            viewModel.getQnaList()     // 조회된 문의 목록이 null이라면 새로 데이터를 가져옴
        }

    }

    override fun onItemClick(qnaList: InquiryItem) {
        val intent = Intent(activity, QnaDetailActivity::class.java)
        intent.putExtra("inquiryId", qnaList.inquiryId)
        intent.putExtra("createdAt", qnaList.createdAt)
        startActivity(intent)
    }
}