package com.computer.inu.readit_appjam.Activity

import android.content.ClipboardManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.computer.inu.readit_appjam.Adapter.MainPagerAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Data.HomeCategoryTab
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    private var clipboard: ClipboardManager? = null
    var backPressedTime: Long = 0
    val FINISH_INTERVAL_TIME = 2000
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    companion object {
        var idx: Int = 0
        var TabdataList: ArrayList<HomeCategoryTab> = ArrayList()
        var SettingFlag = 0
        var AllCategoryFlag = 0
        var TABCATEGORYFLAG = 0
    }
    override fun onBackPressed() {
        var tempTime = System.currentTimeMillis()
        var intervalTime = tempTime - backPressedTime

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            ActivityCompat.finishAffinity(this)
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            toast("한번 더 누르면 종료됩니다.")

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.computer.inu.readit_appjam.R.layout.activity_main)
        idx = SharedPreferenceController.getCategoryIdx(this)
        //   Log.e("token",FirebaseInstanceId.getInstance().getToken())
        tl_main_categoty.tabRippleColor = null
        //공유하기 테스트 입니다.

        //FullScreencall()
// 인텐트 정보가 있는 경우 실행

        if (intent.getStringExtra("url").isNullOrEmpty()) {
        } else {
            var url = intent.getStringExtra("url")
            startActivity<DialLogActivity>("url" to url)
        }


        configureMainTab()
    }

    private fun configureMainTab() {
        vp_main.adapter = MainPagerAdapter(supportFragmentManager, 2)
        vp_main.offscreenPageLimit = 2
        tl_main_categoty.setupWithViewPager(vp_main)

        val navCategoryMainLayout: View =
            (this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                com.computer.inu.readit_appjam.R.layout.navaigation_category,
                null,
                false
            )
        tl_main_categoty.getTabAt(0)!!.customView =
            navCategoryMainLayout.findViewById(com.computer.inu.readit_appjam.R.id.rl_category_main) as RelativeLayout
        tl_main_categoty.getTabAt(1)!!.customView =
            navCategoryMainLayout.findViewById(com.computer.inu.readit_appjam.R.id.rl_category_mypage) as RelativeLayout

    }

    // 시계 나오게 수정해야함
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

}
