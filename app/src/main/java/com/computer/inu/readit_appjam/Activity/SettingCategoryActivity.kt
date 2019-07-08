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
import com.computer.inu.readit_appjam.Data.CategorySettingData
import com.computer.inu.readit_appjam.Interface.CategoryItemTouchHelperCallback
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_setting_category.*
import kotlinx.android.synthetic.main.rv_category_setting_contents.*

class SettingCategoryActivity : AppCompatActivity(), CategorySettingRvAdapter.CallbackInterface,
    CategorySettingRvAdapter.OnStartDragListener {

    lateinit var categorySettingRvAdapter: CategorySettingRvAdapter

    lateinit var dataList: ArrayList<CategorySettingData>

    lateinit var mItemTouchHelper: ItemTouchHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_category)

        dataList = ArrayList()

        dataList.add(
            CategorySettingData(
                1,
                false,
                "개발"
            )
        )

        dataList.add(
            CategorySettingData(
                2,
                false,
                "디자인"
            )
        )

        dataList.add(
            CategorySettingData(
                3,
                false,
                "스시맛집"
            )
        )

        dataList.add(
            CategorySettingData(
                4,
                false,
                "공유오피스"
            )
        )

        dataList.add(
            CategorySettingData(
                5,
                false,
                "페북"
            )
        )

        dataList.add(
            CategorySettingData(
                6,
                false,
                "인스타"
            )
        )

        dataList.add(
            CategorySettingData(
                7,
                false,
                "핀터레스트"
            )
        )

        dataList.add(
            CategorySettingData(
                8,
                false,
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

        rv_category_setting.adapter = categorySettingRvAdapter

        val array: ArrayList<String> = ArrayList()
        category_btn_del.setOnClickListener {
            for (data in dataList) {
                if (data.checkbox == true) {
                    array.add(data.category_name)
                }
            }
            for (data in array) {
                Log.e("data check", data)
            }
        }

        iv_setting_category_back.setOnClickListener {
            finish()
        }
    }


    fun visibleDeleteBtn() {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.down_to_up)
        category_btn_del.visibility = View.VISIBLE
        category_btn_del.startAnimation(animation)
    }

    fun goneDeleteBtn() {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.up_to_down)
        category_btn_del.visibility = View.GONE
        category_btn_del.startAnimation(animation)
    }

    fun dataChange() {
        categorySettingRvAdapter.notifyDataSetChanged()
    }

    fun checkLock(){
        category_cb.isClickable = false
    }

    override fun onHandelSelection(pos: Int, name: String) {
        val intent = Intent(this, CategorySettingEditActivity::class.java)
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


}
