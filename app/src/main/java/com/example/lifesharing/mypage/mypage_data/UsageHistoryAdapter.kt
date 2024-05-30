package com.example.lifesharing.mypage.mypage_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lifesharing.R
import com.example.lifesharing.mypage.interfaces.ReviewWriteClickListener
import com.example.lifesharing.mypage.interfaces.UsageItemClickListener
import com.example.lifesharing.mypage.model.response_body.UsageHistoryResult
import java.text.NumberFormat
import java.util.Locale

/** 이용 내역 리스트 리사이클러 뷰 어댑터 */
class UsageHistoryAdapter (
    private var historyList : ArrayList<UsageHistoryResult>,
    private val reviewListener : ReviewWriteClickListener,
    private val itemListener: UsageItemClickListener
)
    : RecyclerView.Adapter<UsageHistoryAdapter.ViewHolder>() {
    class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        val deposit_accept : ImageView = itemView.findViewById(R.id.deposit_accpet)
        val image : ImageView = itemView.findViewById(R.id.img)
        val location : TextView = itemView.findViewById(R.id.product_location)
        val name : TextView = itemView.findViewById(R.id.product_name_tv)
        val totalTime : TextView = itemView.findViewById(R.id.usage_period)
        val amount : TextView = itemView.findViewById(R.id.usage_price)
        val deposit : TextView = itemView.findViewById(R.id.usage_product_deposit)
        val reviewButton : TextView = itemView.findViewById(R.id.btn_write_review)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsageHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.usage_history_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsageHistoryAdapter.ViewHolder, position: Int) {
        val history = historyList[position]
        holder.location.text = history.location
        holder.name.text = history.productName
        holder.totalTime.text = history.totalTime

        // 가격 천 단위로 끊기
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        holder.amount.text = formatter.format(history.amount)
        holder.deposit.text = formatter.format(history.deposit)

//        if (history.deposit != null) {
//            Glide.with(holder.itemView.context).load(R.drawable.deposit_accept).into(holder.deposit_accept)
//            holder.deposit.setTextColor(Color.parseColor("#000000"))
//        } else {
//            Glide.with(holder.itemView.context).load(R.drawable.not_deposit_accept).into(holder.deposit_accept)
//        }

        Glide.with(holder.itemView.context).load(R.drawable.not_deposit_accept).into(holder.deposit_accept)

        Glide.with(holder.itemView.context).load(history.productImage).into(holder.image)   // 제품 이미지

        // 리뷰 작성 버튼 클릭 리스너 설정
        holder.reviewButton.setOnClickListener {
            reviewListener.onReviewWrite(history)
        }

        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            itemListener.onItemClick(history)
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun setItems(newData: ArrayList<UsageHistoryResult>) {
        historyList.clear()
        historyList.addAll(newData)
        notifyDataSetChanged()
    }

}
