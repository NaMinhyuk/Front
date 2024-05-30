package com.example.lifesharing.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.databinding.ActivityRegistHistoryBinding
import com.example.lifesharing.mypage.model.response_body.MyRegProductList
import com.example.lifesharing.mypage.mypage_data.ProductClickListener
import com.example.lifesharing.mypage.mypage_data.RegistHistoryAdapter
import com.example.lifesharing.mypage.viewModel.RegistListViewModel
import com.example.lifesharing.product.MyItemDetailActivity


/** Registration History (등록내역) */
class RegistHistoryActivity : AppCompatActivity(), ProductClickListener {

    private lateinit var binding: ActivityRegistHistoryBinding
    private lateinit var viewModel : RegistListViewModel    // 사용자가 등록한 제품 리스트 ViewModel
    private lateinit var adapter: RegistHistoryAdapter      // 제품 리스트 리사이클러 뷰 어댑터
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistHistoryBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 뒤로가기
        binding.regHisBackIv.setOnClickListener {
            finish()
        }

        // 제품 리스트를 담을 ArrayList 초기화
        val registerLiteItem = ArrayList<MyRegProductList>()

        // 리사이클러뷰 할당 및 레이아웃 매니저 설정
        recyclerView = binding.registListView
        recyclerView.layoutManager= LinearLayoutManager(this)

        // 리사이클러뷰 어댑터 초기화 및 할당
        adapter = RegistHistoryAdapter(registerLiteItem, this)
        recyclerView.adapter = adapter

        // 어댑터에 데이터 변경을 알리는 메서드를 호출
        adapter.notifyDataSetChanged()

        // 뷰모델 초기화
        viewModel = ViewModelProvider(this).get(RegistListViewModel::class.java)
        viewModel.getRegisterList()    // 등록된 제품 리스트를 호출

        // 제품 리스트
        viewModel.registerListItem.observe(this, Observer { products ->   // 응답결과로 얻은 제품 리스트 관찰
            adapter.setItem(ArrayList(products))   // 새로운 데이터로 어댑터에 설정
        })

        // 제품 개수
        viewModel.productCountLiveData.observe(this, Observer { count ->
            binding.productCnt.text = "총 " + count + "건"
        })
    }

    override fun onItemClick(registerList: MyRegProductList) {
        val intent = Intent(this, MyItemDetailActivity::class.java)
        intent.putExtra("productId", registerList.productId)  // 상세 화면 전환 시 필요한 제품 식별자
        startActivity(intent)
    }
}