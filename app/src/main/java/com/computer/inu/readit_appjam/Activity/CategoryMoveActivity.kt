package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.computer.inu.readit_appjam.Adapter.CategoryMoveAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Data.CategorySettingData
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.GetCategoryResponse
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.PutChangeCategoryResponse
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_category_move.*
import org.jetbrains.anko.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMoveActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var categoryMoveAdapter: CategoryMoveAdapter

    lateinit var dataList: ArrayList<CategorySettingData>

        var category_idx : Int = -1
        var idx_from : Int = -1
        var idx_to : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_move)
        dataList = ArrayList()

        category_idx = intent.getIntExtra("category_idx", 0)
        idx_from = intent.getIntExtra("contents_idx", 0)
        getCategory(category_idx)



        iv_move_category_back.setOnClickListener {
            finish()
        }

        iv_move_category_complete.setOnClickListener {
            putChangeCategoryResponse()
        }

    }

    private fun getCategory(category_idx : Int) {
        val getCategoryResponse: Call<GetCategoryResponse> = networkService.getCategoryResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(this)
        )
        getCategoryResponse.enqueue(object : Callback<GetCategoryResponse> {
            override fun onFailure(call: Call<GetCategoryResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetCategoryResponse>, response: Response<GetCategoryResponse>) {
                if (response.isSuccessful) {

                    if (response.body()!!.status == 200) {
                        dataList.clear()
                        var serverList = response.body()!!.data!!.category_list!!
                            for (i in 0..serverList.size - 1)
                                dataList.add(
                                    CategorySettingData(
                                        serverList[i].category_idx,
                                        serverList[i].category_name,
                                        false
                                    )
                                )


                        categoryMoveAdapter = CategoryMoveAdapter(ctx, dataList)
                        //원래 자리
                        rv_category_move.layoutManager = LinearLayoutManager(this@CategoryMoveActivity)
                        val layoutManager = LinearLayoutManager(this@CategoryMoveActivity)
                        layoutManager.orientation = LinearLayoutManager.VERTICAL
                        rv_category_move.setLayoutManager(layoutManager)
                        rv_category_move.adapter = categoryMoveAdapter


                        /* contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, response.body()!!.undefinedData!!.contents_list!!)
                         rv_contents_all.adapter = contentsRecyclerViewAdapter
                         rv_contents_all.layoutManager = LinearLayoutManager(context)   */

                        for(i in 0..dataList.size - 1){
                            if(dataList[i].category_idx == category_idx)
                                dataList[i].checkbox = true

                        }
                        var ch_pos = -1
                        for(i in 0..dataList.size -1){
                            if(dataList[i].checkbox == true)
                                ch_pos = i
                            break
                        }
                        idx_to = dataList[ch_pos].category_idx
                    }
                }
            }
        })

    }

    private fun putChangeCategoryResponse() {
        val putChangeCategoryResponse: Call<PutChangeCategoryResponse> = networkService.putChangeCategoryResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(this), idx_from, idx_to)

        putChangeCategoryResponse.enqueue(object : Callback<PutChangeCategoryResponse> {
            override fun onFailure(call: Call<PutChangeCategoryResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PutChangeCategoryResponse>, response: Response<PutChangeCategoryResponse>) {
                if (response.isSuccessful) {

                }
            }
        })

    }

}
