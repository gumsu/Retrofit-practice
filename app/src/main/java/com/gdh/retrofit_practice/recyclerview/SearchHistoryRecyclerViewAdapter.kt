package com.gdh.retrofit_practice.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gdh.retrofit_practice.R
import com.gdh.retrofit_practice.model.SearchData

class SearchHistoryRecyclerViewAdapter: RecyclerView.Adapter<SearchItemViewHolder>() {
    private var searchHistoryList: ArrayList<SearchData> = ArrayList()

    // 뷰홀더가 메모리에 올라갔을 때, 뷰홀더와 레이아웃을 연결시켜준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val searchItemViewHolder = SearchItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_search_item, parent, false))
        return searchItemViewHolder
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val dataItem: SearchData = this.searchHistoryList[position]
        holder.bindWithView(dataItem)
    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }

    // 외부에서 어답터에 데이터 배열을 넣어준다.
    fun submitList(searchHistoryList: ArrayList<SearchData>){
        this.searchHistoryList = searchHistoryList
    }
}