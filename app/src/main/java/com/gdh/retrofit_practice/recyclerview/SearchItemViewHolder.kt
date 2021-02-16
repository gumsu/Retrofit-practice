package com.gdh.retrofit_practice.recyclerview

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gdh.retrofit_practice.model.SearchData
import com.gdh.retrofit_practice.utils.Constants.TAG
import kotlinx.android.synthetic.main.layout_search_item.view.*

class SearchItemViewHolder(itemView: View, searchRecyclerViewInterface: ISearchHistoryRecyclerView): RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var mySearchRecyclerViewInterface: ISearchHistoryRecyclerView

    // 뷰 가져오기
    private val searchItemTextView = itemView.search_term_text
    private val whenSearchedTextView = itemView.when_searched_text
    private val deleteSearchBtn = itemView.delete_search_btn
    // 몸통의 클릭 이벤트 처리를 위해
    private val constraintSearchItem = itemView.constraint_search_item

    init {
        Log.d(TAG, "SearchItemViewHolder - init() called ")
        // onClick() 리스너 연결 처리
        deleteSearchBtn.setOnClickListener(this)
        constraintSearchItem.setOnClickListener(this)
        this.mySearchRecyclerViewInterface = searchRecyclerViewInterface
    }

    // 데이터와 뷰를 묶는다.
    fun bindWithView(searchItem: SearchData){
        Log.d(TAG, "SearchItemViewHolder - bindWithView() called")

        whenSearchedTextView.text = searchItem.timestamp
        searchItemTextView.text = searchItem.term
    }

    override fun onClick(view: View?) {
        Log.d(TAG, "SearchItemViewHolder - onClick() called ")
        when(view){
            deleteSearchBtn -> {
                Log.d(TAG, "SearchItemViewHolder -  검색 삭제 버튼 클릭")
                this.mySearchRecyclerViewInterface.onSearchItemDeleteClicked(adapterPosition)
            }
            constraintSearchItem -> {
                Log.d(TAG, "SearchItemViewHolder -  검색 아이템 클릭")
                this.mySearchRecyclerViewInterface.onSearchItemClicked(adapterPosition)
            }
        }
    }
}