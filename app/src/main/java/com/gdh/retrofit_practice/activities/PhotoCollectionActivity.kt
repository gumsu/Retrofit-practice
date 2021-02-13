package com.gdh.retrofit_practice.activities

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.gdh.retrofit_practice.R
import com.gdh.retrofit_practice.model.Photo
import com.gdh.retrofit_practice.model.SearchData
import com.gdh.retrofit_practice.recyclerview.PhotoGridRecyclerViewAdapter
import com.gdh.retrofit_practice.utils.Constants.TAG
import com.gdh.retrofit_practice.utils.SharedPrefManager
import kotlinx.android.synthetic.main.activity_photo_collection.*
import java.util.*
import kotlin.collections.ArrayList

class PhotoCollectionActivity: AppCompatActivity(), SearchView.OnQueryTextListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    // 데이터
    var photoList = ArrayList<Photo>()

    // 검색 기록 배열
    private var searchHistoryList = ArrayList<SearchData>()

    // 어댑터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter

    // 서치뷰
    private lateinit var mySearchView: SearchView

    // 서치뷰 editText
    private lateinit var mySearchViewEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)

        Log.d(TAG, "PhotoCollectionActivity - onCreate() called ")

        val bundle = intent.getBundleExtra("array_bundle")
        val searchTerm = intent.getStringExtra("search_term")
        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

        Log.d(TAG, "PhotoCollectionActivity - onCreat() called / searchTerm : $searchTerm, photoList.count() : ${photoList.count()} ")

        search_history_mode_switch.setOnCheckedChangeListener(this)
        clear_search_history_button.setOnClickListener(this)

        top_app_bar.title = searchTerm.toString()

        // 액티비티에서 어떤 액션바를 사용할 지 설정한다.
        setSupportActionBar(top_app_bar)

        this.photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()
        this.photoGridRecyclerViewAdapter.submitList(photoList)

        my_photo_recycler_view.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        my_photo_recycler_view.adapter = this.photoGridRecyclerViewAdapter

        // 저장된 검색 기록 가져오기
        this.searchHistoryList = SharedPrefManager.getSearchHistoryList() as ArrayList<SearchData>

        this.searchHistoryList.forEach {
            Log.d(TAG, "저장된 검색 기록 - it.term : ${it.term}, it.timestamp: ${it.timestamp} ")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onCreateOptionMenu() called")

        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        this.mySearchView= menu?.findItem(R.id.search_menu_item)?.actionView as SearchView
        this.mySearchView.apply {
            this.queryHint = "검색어를 입력해주세요"

            this.setOnQueryTextListener(this@PhotoCollectionActivity)
            this.setOnQueryTextFocusChangeListener { _, hasExpanded ->
                when(hasExpanded){
                    true -> {
                        Log.d(TAG, "onCreateOptionsMenu: 서치뷰 열림")
                        linear_search_history_view.visibility = View.VISIBLE
                    }
                    false -> {
                        Log.d(TAG, "onCreateOptionsMenu: 서치뷰 닫힘")
                        linear_search_history_view.visibility = View.INVISIBLE
                    }
                }
            }
            // 서치뷰에서 EditText를 가져온다.
            mySearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }
        this.mySearchViewEditText.apply { 
            this.filters = arrayOf(InputFilter.LengthFilter(12))
            this.setTextColor(Color.WHITE)
            this.setHintTextColor(Color.WHITE)
        }
        return true
    }

    // 서치뷰 검색어 입력 이벤트
    // 검색버튼이 클릭되었을 때
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onQueryTextSubmit:  called / query: $query")

        if(!query.isNullOrEmpty()){
            this.top_app_bar.title = query

            //TODO:: api 호출
            //TODO:: 검색어 저장

            val newSearchData = SearchData(term = query, timestamp = Date().toString())
            this.searchHistoryList.add(newSearchData)

            SharedPrefManager.storeSearchHistoryList(this.searchHistoryList)
        }
//        this.mySearchView.setQuery("", false)
//        this.mySearchView.clearFocus()
        this.top_app_bar.collapseActionView()

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onQueryTextChange: called / newText: $newText")
        
//        val userInputText = newText ?: ""
        val userInputText = newText.let { 
            it
        }?: ""
        
        if(userInputText.count() == 12){
            Toast.makeText(this, "검색어는 12자까지만 입력가능합니다.", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
        when(switch){
            search_history_mode_switch -> {
                if (isChecked == true) {
                    Log.d(TAG, "onCheckedChanged: 검색어 저장 기능 ON")
                } else {
                    Log.d(TAG, "onCheckedChanged: 검색어 저장 기능 OFF")
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when(view){
            clear_search_history_button -> {
                Log.d(TAG, "onClick: 검색 기록 삭제 버튼이 클릭되었다.")
            }
        }
    }
}