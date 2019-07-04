package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_all_category_view.*
import org.jetbrains.anko.startActivity


class AllCategoryViewActivity : AppCompatActivity() {
    var backPressedTime: Long = 0
    val FINISH_INTERVAL_TIME = 2000
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.stay, R.anim.sliding_down)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(com.computer.inu.readit_appjam.R.layout.activity_all_category_view)
        iv_category_detail_arrow_up.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.stay, R.anim.sliding_down)
        }
        iv_category_detail_setting.setOnClickListener {
            startActivity<SettingCategoryActivity>()//카테고리 수정
        }
        iv_category_detail_plus.setOnClickListener {
            //카테고리 추가

        }
    }
}
