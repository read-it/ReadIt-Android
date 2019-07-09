package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.computer.inu.readit_appjam.Adapter.LatestSearchKeywordRVAdapter
import com.computer.inu.readit_appjam.DB.DBHelper
import com.computer.inu.readit_appjam.Data.LatestSearchKeyword
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class SearchActivity : AppCompatActivity() {

    lateinit var keywordRecyclerViewAdapter: LatestSearchKeywordRVAdapter

    val REQUEST_CODE_SEARCH_ACTIVITY = 1000
    val REQUEST_CODE_RESULT_ACTIVITY = 2000
    val dbHandler = DBHelper(this, null) // 최근 검색어 Database Handler
    var dataList: ArrayList<LatestSearchKeyword> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var searchCategory: String = ""
        val cursor = dbHandler.getAllKeyword()

        // list <- DB(LatestSearchKeyword)
        toList(dataList)

        keywordRecyclerViewAdapter = LatestSearchKeywordRVAdapter(this, dataList)
        rv_latestSearchKeywords.adapter = keywordRecyclerViewAdapter
        rv_latestSearchKeywords.layoutManager = LinearLayoutManager(this)

        // 최근 검색어 없을 경우 textview 숨기기
        if (dataList == null) {
            tv_title_keyword.text = ""
        }

        edt_search.setOnClickListener {
            startActivityForResult<SearchResultActivity>(REQUEST_CODE_RESULT_ACTIVITY)
        }

        btn_categoryChoice.setOnClickListener {
            startActivityForResult<SearchCategoryActivity>(REQUEST_CODE_SEARCH_ACTIVITY)
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
                val categoryName = data!!.getStringExtra("category_name")
                tv_category_name.text = categoryName
            }
        }

        if (requestCode == REQUEST_CODE_RESULT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                toList(dataList)
            }
        }
    }
}
