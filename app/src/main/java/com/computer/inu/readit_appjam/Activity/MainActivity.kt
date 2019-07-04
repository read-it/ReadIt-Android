package com.computer.inu.readit_appjam.Activity

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.computer.inu.readit_appjam.Adapter.MainPagerAdapter
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    private var clipboard: ClipboardManager? = null
    var sharedText = ""
    var backPressedTime: Long = 0
    val FINISH_INTERVAL_TIME = 2000
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onBackPressed() {
        var tempTime = System.currentTimeMillis()
        var intervalTime = tempTime - backPressedTime

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.computer.inu.readit_appjam.R.layout.activity_main)
        //공유하기 테스트 입니다.
        val intent = intent
        val action = intent.action
        val type = intent.type

// 인텐트 정보가 있는 경우 실행
        if (Intent.ACTION_SEND == action && type != null) {
            if ("text/plain" == type) {
                sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                /*   AlertDialog.Builder(this)
                       .setTitle("콘텐츠가 저장되었습니다.")
                       .setMessage(sharedText)
                       .setPositiveButton(android.R.string.ok, null)
                       .setCancelable(false)
                       .create()
                       .show()*/
                startActivity<DialLogActivity>()
            }
        }
        //공유하기 테스트 끝

        //클립보드매니져 테스트
        clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        if (clipboard?.hasText()!!) {
            toast(clipboard!!.text)
        } else {
            toast("없음")
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

}
