package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.computer.inu.readit_appjam.Adapter.LatestSearchKeywordRVAdapter
import com.computer.inu.readit_appjam.Adapter.SearchResultsRVAdapter
import com.computer.inu.readit_appjam.DB.DBHelper
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Data.CategorySettingData
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import com.computer.inu.readit_appjam.Data.ContentsSearchData
import com.computer.inu.readit_appjam.Data.LatestSearchKeyword
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.GetCategoryResponse
import com.computer.inu.readit_appjam.Network.Get.GetSearchResponse
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_search_result.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    val REQUEST_CODE_SEARCH_RESULT_ACTIVITY = 1000
    var categoryList: ArrayList<CategorySettingData> = ArrayList()
    var temp: ArrayList<String> = ArrayList()
    var contentsList: ArrayList<ContentsSearchData> = ArrayList()

    var keyword: String = "" // 검색어
    var searchCategory: String = "" // 카테고리
    var categoryIdx: Int = -1 // 통신; category_idx

    lateinit var searchResultsRVAdapter: SearchResultsRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        // recyclerview adapter 초기화
        searchResultsRVAdapter = SearchResultsRVAdapter(ctx, contentsList)
        rv_searchResults.adapter = searchResultsRVAdapter
        rv_searchResults.layoutManager = LinearLayoutManager(ctx)

        // SearchActivity에서 카테고리 설정 후 넘어올 경우
        searchCategory = intent.getStringExtra("search_category")
        categoryIdx = intent.getIntExtra("index_to_server", -1)
        tv_result_category_name.text = searchCategory

        // 통신; categoryList에 카테고리 목록 받아오기(addAll, CategorySettingData)
        getCategory()

        edt_searching.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                keyword = edt_searching.text.toString()
                edt_searching.imeOptions = EditorInfo.IME_ACTION_SEARCH

                getSearchResult(keyword)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 카테고리 설정 버튼
        btn_result_categoryResult.setOnClickListener {
            for (i in categoryList.indices) {
                temp.add(categoryList[i].category_name)
            }
            startActivityForResult<SearchCategoryActivity>(
                REQUEST_CODE_SEARCH_RESULT_ACTIVITY,
                "category_list" to temp
            )
        }

        // 키보드 검색 버튼 동작
        edt_searching.setOnEditorActionListener { v, actionId, event ->
            if (v.getId() == R.id.edt_searching && actionId == EditorInfo.IME_ACTION_SEARCH) {
                addData(edt_searching.text.toString()) // DB에 최근검색어 추가
            }
            false
        }

        // 삭제 버튼
        btn_cancel.setOnClickListener {
            edt_searching.text = null
        }

        btn_back_to_search.setOnClickListener {
            val intent: Intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SEARCH_RESULT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val categoryIndex = data!!.getIntExtra("category_index", -1)
                for (i in temp.indices) {
                    if (i == categoryIndex) {
                        searchCategory = temp[i]
                        categoryIdx = categoryList[i].category_idx
                        break
                    }
                }
                tv_result_category_name.text = searchCategory
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

    private fun getCategory() {
        val getCategoryResponse: Call<GetCategoryResponse> = networkService.getCategoryResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(this)
        )
        getCategoryResponse.enqueue(object : Callback<GetCategoryResponse> {
            override fun onFailure(call: Call<GetCategoryResponse>, t: Throwable) {
                toast("fail")
            }

            override fun onResponse(call: Call<GetCategoryResponse>, response: Response<GetCategoryResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        categoryList.addAll(response.body()!!.data!!.category_list!!)
                    } else {
                        toast(response.body()!!.message)
                    }
                }
            }
        })

    }

    private fun getSearchResult(keyword: String) {
        val getSearchResponse: Call<GetSearchResponse> = networkService.getSearchResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(this),
            SharedPreferenceController.getCategoryIdx(this),
            categoryIdx, // category_idx 넣기
            keyword
        )
        getSearchResponse.enqueue(object : Callback<GetSearchResponse> {
            override fun onFailure(call: Call<GetSearchResponse>, t: Throwable) {
                Log.e("Fail", t.toString())
            }

            override fun onResponse(call: Call<GetSearchResponse>, response: Response<GetSearchResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        if (response.body()!!.data != null) {
                            // recyclerview 갱신
                            contentsList.addAll(response.body()!!.data!!)
                            searchResultsRVAdapter.dataList = contentsList
                            searchResultsRVAdapter.notifyDataSetChanged()

                            // view 갱신
                            rv_searchResults.visibility = View.VISIBLE
                            view_noResult.visibility = View.GONE
                        } else {
                            // view 갱신
                            view_noResult.visibility = View.VISIBLE
                            rv_searchResults.visibility = View.GONE
                        }
                    }
                }
            }
        })
    }
}
