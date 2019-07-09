package com.computer.inu.readit_appjam.Activity

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.TabdataList
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import kotlinx.android.synthetic.main.activity_all_category_view.*
import org.jetbrains.anko.startActivity


class AllCategoryViewActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(
            com.computer.inu.readit_appjam.R.anim.stay,
            com.computer.inu.readit_appjam.R.anim.sliding_down
        )
    }

    val pkgName = packageName
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(com.computer.inu.readit_appjam.R.layout.activity_all_category_view)
        addCategory()
        FullScreencall()
        iv_category_detail_arrow_up.setOnClickListener {
            finish()
            overridePendingTransition(
                com.computer.inu.readit_appjam.R.anim.stay,
                com.computer.inu.readit_appjam.R.anim.sliding_down
            )
        }
        iv_category_detail_setting.setOnClickListener {
            startActivity<SettingCategoryActivity>()//카테고리 수정
        }
        iv_category_detail_plus.setOnClickListener {
            //카테고리 추가

        }
    }

    fun FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
        }
    }

    fun addCategory() {
        for (i in 0..TabdataList.size - 1) {
            if (TabdataList[i].TabName!!.isNotEmpty()) {
                var obj = i
                when (obj) {
                    1 -> tv_all_category_1.text = TabdataList[i].TabName.toString()
                    2 -> tv_all_category_2.text = TabdataList[i].TabName.toString()
                    3 -> tv_all_category_3.text = TabdataList[i].TabName.toString()
                    4 -> tv_all_category_4.text = TabdataList[i].TabName.toString()
                    5 -> tv_all_category_5.text = TabdataList[i].TabName.toString()
                    6 -> tv_all_category_6.text = TabdataList[i].TabName.toString()
                    7 -> tv_all_category_7.text = TabdataList[i].TabName.toString()
                    8 -> tv_all_category_8.text = TabdataList[i].TabName.toString()
                }

            }
        }
    }

}


