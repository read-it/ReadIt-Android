package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.computer.inu.readit_appjam.Adapter.LatestSearchKeywordRVAdapter
import com.computer.inu.readit_appjam.Adapter.SearchResultsRVAdapter
import com.computer.inu.readit_appjam.DB.DBHelper
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import com.computer.inu.readit_appjam.Data.LatestSearchKeyword
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_search_result.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class SearchResultActivity : AppCompatActivity() {

    val REQUEST_CODE_SEARCH_RESULT_ACTIVITY = 1000

    lateinit var searchResultsRVAdapter: SearchResultsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        edt_searching.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var keyword = edt_searching.text.toString()
                edt_searching.imeOptions = EditorInfo.IME_ACTION_SEARCH

                // 통신; 결과 존재 -> rv에 띄우기
                var dataList: ArrayList<ContentsOverviewData> = ArrayList()
                searchResultsRVAdapter = SearchResultsRVAdapter(ctx, dataList)
                rv_searchResults.adapter = searchResultsRVAdapter
                rv_searchResults.layoutManager = LinearLayoutManager(ctx)
                rv_searchResults.visibility = View.VISIBLE
                view_noResult.visibility = View.GONE

                // 통신; 결과 없음
                view_noResult.visibility = View.VISIBLE
                rv_searchResults.visibility = View.GONE

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btn_result_categoryResult.setOnClickListener {
            startActivityForResult<SearchCategoryActivity>(REQUEST_CODE_SEARCH_RESULT_ACTIVITY)
        }

        edt_searching.setOnEditorActionListener { v, actionId, event ->
            if (v.getId() == R.id.edt_searching && actionId == EditorInfo.IME_ACTION_SEARCH) {
                addData(edt_searching.text.toString())
                //toast("성공")
            }
            false
        }

        btn_cancel.setOnClickListener {
            edt_searching.text = null
        }

        btn_back_to_search.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SEARCH_RESULT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val categoryName = data!!.getStringExtra("category_name")
                tv_result_category_name.text = categoryName
            }
        }
    }

    /*fun onEditorAction(view: TextView, actionID: Int, event: KeyEvent){
        if(event.keyCode == KeyEvent.KEYCODE_ENTER && actionID == EditorInfo.IME_ACTION_SEARCH){
            addData(edt_searching.text.toString())
        }
    }*/

    fun addData(keyWord: String) {
        val dbHandler = DBHelper(this, null)
        dbHandler.add(keyWord)
    }

    // 검색 버튼 클릭 시 최근 검색어 내부 DB 저장
    /*override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        when (keyCode) {
            KeyEvent.KEYCODE_ENTER -> addData(edt_searching.text.toString())
        }
        return super.onKeyDown(keyCode, event)
    }*/
}
