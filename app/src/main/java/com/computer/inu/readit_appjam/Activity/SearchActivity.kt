package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.computer.inu.readit_appjam.Adapter.LatestSearchKeywordRVAdapter
import com.computer.inu.readit_appjam.DB.DBHelper
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Data.CategorySettingData
import com.computer.inu.readit_appjam.Data.LatestSearchKeyword
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.GetCategoryResponse
import com.computer.inu.readit_appjam.Network.Get.GetSearchResponse
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var keywordRecyclerViewAdapter: LatestSearchKeywordRVAdapter

    val REQUEST_CODE_SEARCH_ACTIVITY = 1000
    val REQUEST_CODE_RESULT_ACTIVITY = 2000
    val dbHandler = DBHelper(this, null) // 최근 검색어 Database Handler
    var dataList: ArrayList<LatestSearchKeyword> = ArrayList()
    var categoryList: ArrayList<CategorySettingData> = ArrayList()
    var temp: ArrayList<String> = ArrayList()
    var searchCategory: String = ""
    var categoryIdx: Int = -1 // 통신; category_idx


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val cursor = dbHandler.getAllKeyword()
        categoryIdx = SharedPreferenceController.getCategoryIdx(this)
        // list <- DB(LatestSearchKeyword)
        toList(dataList)

        // 통신; categoryList에 카테고리 목록 받아오기(addAll, CategorySettingData)
        getCategory()

        keywordRecyclerViewAdapter = LatestSearchKeywordRVAdapter(this, dataList)
        rv_latestSearchKeywords.adapter = keywordRecyclerViewAdapter
        rv_latestSearchKeywords.layoutManager = LinearLayoutManager(this)

        // 최근 검색어 없을 경우 textview 숨기기
        if (dataList == null) {
            tv_title_keyword.text = ""
        }

        edt_search.setOnClickListener {
            startActivityForResult<SearchResultActivity>(
                REQUEST_CODE_RESULT_ACTIVITY,
                "search_category" to searchCategory,
                "index_to_server" to categoryIdx
            )
        }

        btn_categoryChoice.setOnClickListener {
            // intent 보내기 위한 string 타입의 arraylist 작성
            temp.clear()
            for (i in categoryList.indices) {
                temp.add(categoryList[i].category_name)
            }
            startActivityForResult<SearchCategoryActivity>(REQUEST_CODE_SEARCH_ACTIVITY, "category_list" to temp)
        }

        btn_back.setOnClickListener {
            finish()
        }

    }

    // 데이터 삭제 함수
    fun deleteData(dbHelper: DBHelper, keyWord: String) {
        val cursor = dbHelper.getAllKeyword()

        if (cursor != null) {
            for (k in 1..cursor.count) {
                cursor.moveToNext()
                if (keyWord == cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_KEYWORD))) {
                    dbHelper.delete(keyWord)
                    break
                }
            }
        }
    }

    fun toList(dataList: ArrayList<LatestSearchKeyword>) {
        val dbHandler = DBHelper(this, null)
        val cursor = dbHandler.getAllKeyword()

        if (cursor != null) {
            for (k in 1..cursor.count) {
                cursor.moveToNext()
                dataList.add(
                    LatestSearchKeyword(
                        cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_KEYWORD))
                    )
                )
            }
        }

        /*cursor!!.moveToFirst()
        dataList.add(
            LatestSearchKeyword(
                cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_KEYWORD))
            )
        )
        while (cursor.moveToNext()) {
            dataList.add(
                LatestSearchKeyword(
                    cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_KEYWORD))
                )
            )
        }*/
        cursor?.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SEARCH_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val categoryIndex = data!!.getIntExtra("category_index", -1) // 내부 array
                for (i in temp.indices) {
                    if (i == categoryIndex) {
                        searchCategory = temp[i]
                        categoryIdx = categoryList[i].category_idx
                        break
                    }
                }
                tv_category_name.text = searchCategory
            }
        }

        if (requestCode == REQUEST_CODE_RESULT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                dataList.clear()
                toList(dataList)
                keywordRecyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getCategory() {
        val getCategoryResponse: Call<GetCategoryResponse> = networkService.getCategoryResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(this)
        )
        getCategoryResponse.enqueue(object : Callback<GetCategoryResponse> {
            override fun onFailure(call: Call<GetCategoryResponse>, t: Throwable) {
                toast("왜 안돼 ㅜ")
            }

            override fun onResponse(call: Call<GetCategoryResponse>, response: Response<GetCategoryResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        categoryList.clear()
                        categoryList.addAll(response.body()!!.data!!.category_list!!)
                    } else {
                        toast(response.body()!!.message)
                    }
                }
            }
        })

    }
}
