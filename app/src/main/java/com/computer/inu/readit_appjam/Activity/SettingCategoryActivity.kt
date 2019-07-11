package com.computer.inu.readit_appjam.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.computer.inu.readit_appjam.Adapter.CategorySettingRvAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Data.CategorySettingData
import com.computer.inu.readit_appjam.Interface.CategoryItemTouchHelperCallback
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.GetCategoryResponse
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_setting_category.*
import kotlinx.android.synthetic.main.rv_category_setting_contents.view.*
import org.jetbrains.anko.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingCategoryActivity : AppCompatActivity(), CategorySettingRvAdapter.CallbackInterface,
    CategorySettingRvAdapter.OnStartDragListener {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var categorySettingRvAdapter: CategorySettingRvAdapter

    lateinit var dataList: ArrayList<CategorySettingData>

    lateinit var mItemTouchHelper: ItemTouchHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_category)

        dataList = ArrayList()
/*
        dataList.add(
            CategorySettingData(
                false,
                1,
                "개발"
            )
        )

        dataList.add(
            CategorySettingData(
                false,
                2,

                "디자인"
            )
        )

        dataList.add(
            CategorySettingData(
                false,
                3,

                "스시맛집"
            )
        )

        dataList.add(
            CategorySettingData(
                false,
                4,

                "공유오피스"
            )
        )

        dataList.add(
            CategorySettingData(
                false,
                5,

                "페북"
            )
        )

        dataList.add(
            CategorySettingData(
                false,
                6,

                "인스타"
            )
        )

        dataList.add(
            CategorySettingData(
                false,
                7,

                "핀터레스트"
            )
        )

        dataList.add(
            CategorySettingData(
                false,
                8,

                "유투브"
            )
        )

        categorySettingRvAdapter = CategorySettingRvAdapter(this, dataList, this)
        //원래 자리
        rv_category_setting.layoutManager = LinearLayoutManager(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_category_setting.setLayoutManager(layoutManager)

        val mCallback: CategoryItemTouchHelperCallback = CategoryItemTouchHelperCallback(categorySettingRvAdapter)
        mItemTouchHelper = ItemTouchHelper(mCallback)
        mItemTouchHelper.attachToRecyclerView(rv_category_setting)

        rv_category_setting.adapter = categorySettingRvAdapter */


         category_btn_del.setOnClickListener {
             var idx = -1
             for (data in dataList) {
                 if (data.checkbox == true) {
                     idx = data.category_idx
                 }
             }

             val intent = Intent(this, CategoryDeletePopupActivity::class.java)
             intent.putExtra("idx", idx)
             startActivity(intent)

         }

        getCategory()

        iv_setting_category_back.setOnClickListener {
            finish()
        }
    }


    fun visibleDeleteBtn() {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.down_to_up)
        category_btn_del.visibility = View.VISIBLE
        category_btn_del.startAnimation(animation)

    }

    fun goneBtns(){

        for (i in 0..(dataList.size-1)) {
            rv_category_setting.getChildAt(i).category_setting_edit.visibility = View.GONE
            rv_category_setting.getChildAt(i).category_setting_sort.visibility = View.GONE
        }
        //categorySettingRvAdapter.notifyDataSetChanged()
    }

    fun gonefirst(idx : Int){
        rv_category_setting.getChildAt(idx).category_setting_edit.visibility = View.GONE
        rv_category_setting.getChildAt(idx).category_setting_sort.visibility = View.GONE
    }



    fun goneDeleteBtn() {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.up_to_down)
        category_btn_del.visibility = View.GONE
        category_btn_del.startAnimation(animation)

        for (i in 0..(dataList.size - 1)) {
            rv_category_setting.getChildAt(i).category_setting_edit.visibility = View.VISIBLE
            rv_category_setting.getChildAt(i).category_setting_sort.visibility = View.VISIBLE
        }
    }



    override fun onHandelSelection(pos: Int, name: String) {
        val intent = Intent(this, CategorySettingEditActivity::class.java)
        intent.putExtra("idx", dataList[pos].category_idx)
        intent.putExtra("name", dataList[pos].category_name)
        intent.putExtra("pos", pos)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Log.e("check", data!!.getStringExtra("name"))
                dataList[data!!.getIntExtra("pos", 0)].category_name = data.getStringExtra("name")
                categorySettingRvAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onStartDrag(holder: CategorySettingRvAdapter.Holder) {
        mItemTouchHelper.startDrag(holder)
    }

    private fun getCategory() {
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
                       var serverList = response.body()!!.data!!.category_list!!
                        for(i in 0..serverList.size - 1)
                        dataList.add(CategorySettingData(serverList[i].category_idx, serverList[i].category_name, false))

                        categorySettingRvAdapter = CategorySettingRvAdapter(ctx, dataList, this@SettingCategoryActivity
                        )
                        //원래 자리
                        rv_category_setting.layoutManager = LinearLayoutManager(this@SettingCategoryActivity)
                        val layoutManager = LinearLayoutManager(this@SettingCategoryActivity)
                        layoutManager.orientation = LinearLayoutManager.VERTICAL
                        rv_category_setting.setLayoutManager(layoutManager)
                        rv_category_setting.adapter = categorySettingRvAdapter
                        val mCallback: CategoryItemTouchHelperCallback =
                            CategoryItemTouchHelperCallback(categorySettingRvAdapter)
                        mItemTouchHelper = ItemTouchHelper(mCallback)
                        mItemTouchHelper.attachToRecyclerView(rv_category_setting)


                        /* contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, response.body()!!.undefinedData!!.contents_list!!)
                         rv_contents_all.adapter = contentsRecyclerViewAdapter
                         rv_contents_all.layoutManager = LinearLayoutManager(context)
   */
                    }
                }
            }
        })

    }
}
