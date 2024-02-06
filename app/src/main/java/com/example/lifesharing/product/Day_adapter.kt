package com.example.lifesharing.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lifesharing.R
import java.util.Date

class Day_adapter(val tempMonth:Int, val dayList: MutableList<Date>) : RecyclerView.Adapter<Day_adapter.DayView>() {
    val ROW =6
    class DayView(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day,parent,false)

        return DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {

        //초기화
        var day_text: TextView = holder.layout.findViewById<TextView>(R.id.calendar_day_text)


        //날짜 표시
        day_text.text = dayList[position].date.toString()
        if(tempMonth != dayList[position].month) {
            day_text.alpha=0.4f
        }

    }


    override fun getItemCount(): Int {
        return ROW*7
    }
}