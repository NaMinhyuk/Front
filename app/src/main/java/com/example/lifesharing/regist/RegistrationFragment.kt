package com.example.lifesharing.regist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifesharing.R
import com.example.lifesharing.databinding.FragmentRegistrationBinding
import com.example.lifesharing.product.MyItemDetailActivity
import com.example.lifesharing.product.view_model.MyProductViewModel
import com.example.lifesharing.product.data.MyItemAdapter
import com.example.lifesharing.product.data.MyItemData
import com.example.lifesharing.product.interfaces.OnItemClickListener
import com.example.lifesharing.search.SearchActivity

/** MY아이템 View*/
class RegistrationFragment : Fragment(), OnItemClickListener {

    lateinit var binding: FragmentRegistrationBinding    // 뷰바인딩 선언
    private lateinit var myItemAdapter: MyItemAdapter    // 리사이클러뷰 어댑터 선언
    private lateinit var viewModel: MyProductViewModel   // ViewModel 인스턴스 선언

    val TAG:String = "로그"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        // 검색 버튼
        binding.registHomeSearchBtn.setOnClickListener{
            val intent = Intent(requireContext(), SearchActivity::class.java)    // Intent를 이용하여 화면 전환
            startActivity(intent)
        }

        // RecyclerView 초기화
        initRecycler()

        viewModel = ViewModelProvider(requireActivity()).get(MyProductViewModel::class.java)    // MyProductViewModel의 인스턴스를 가져와 viewModel 변수에 할당

        return binding.root
    }

    private fun initRecycler() {
        myItemAdapter = MyItemAdapter(ArrayList(), this)
        binding.myItemRv.adapter = myItemAdapter    // 리사이클러뷰에 어댑터 할당
    }

    //뷰가 생성된 후 추가적인 초기화를 진행하는 메소드
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // myProducts 라이브 데이터를 관찰하며, 변경 사항이 있을 때마다 UI를 업데이트
        viewModel.myProducts.observe(viewLifecycleOwner, Observer { products ->
            val myItems = products?.map { product ->
                // ProductResultDTO를 NewRegistItemData로 변환
                MyItemData(
                    id = product.productId,
                    img = product.imageUrl ?: R.drawable.camara.toString(), // 이미지 URL이 null이면 기본 사진 넣어주기
                    location = product.location,
                    reviewCount = product.reviewCount,
                    score = product.score,
                    name = product.name,
                    deposit = product.deposit,
                    dayPrice = product.dayPrice
                )
            } ?: listOf()
            myItemAdapter.setItems(ArrayList(myItems)) // 변환된 데이터로 RecyclerView를 업데이트
        })


        val registbtn = binding.registHomeRegistbtn
        registbtn.setOnClickListener {
            // Intent를 사용하여 Activity로 화면 전환
            val intent = Intent(requireActivity(), Regist_Add_Activity::class.java)
            startActivity(intent)
        }

        viewModel.getMyItemProduct() // 데이터 로딩 시작
    }

    // 리사이클러뷰의 아이템 클릭 인터페이스 구현
    override fun onItemClicked(position: Int, item: MyItemData) {
        val intent = Intent(context, MyItemDetailActivity::class.java).apply {   // 상세화면으로 전환
            putExtra("productId", item.id)    // 전환 시에 필요한 제품 식별자 정보 함께 전달
        }
        startActivity(intent)
    }
}