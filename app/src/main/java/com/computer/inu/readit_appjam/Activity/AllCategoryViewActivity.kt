package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.TabdataList
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.idx
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
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
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.category_down)
        all_category_container.visibility = View.VISIBLE
        all_category_container.startAnimation(animation)
        addCategory()
        //FullScreencall()
        iv_category_detail_arrow_up.setOnClickListener {

            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.category_up)
            all_category_container.visibility = View.GONE
            all_category_container.startAnimation(animation)

            Handler().postDelayed(Runnable {
                finish()
            }, 200)//

            /*overridePendingTransition(
                com.computer.inu.readit_appjam.R.anim.stay,
                com.computer.inu.readit_appjam.R.anim.sliding_down
            )*/
        }
        iv_category_detail_setting.setOnClickListener {
            startActivity<SettingCategoryActivity>()//카테고리 수정
            finish()
        }
        iv_category_detail_plus.setOnClickListener {
            val intent = Intent(this, NewCategoryAddActivity::class.java)
            startActivity(intent)
            finish()

        }

        container_touch.setOnClickListener {
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.category_up)
            all_category_container.visibility = View.GONE
            all_category_container.startAnimation(animation)

            Handler().postDelayed(Runnable {
                setResult(Activity.RESULT_OK, intent)
                finish()
            }, 200)//
        }
        setCategory()

        /* setResult(Activity.RESULT_OK, intent)
         finish()*/
    }

    /*fun FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
        }
    }*/

    fun addCategory() {
        for (i in 0..TabdataList.size - 1) {
            if (TabdataList[i].TabName!!.isNotEmpty()) {
                var obj = i
                when (obj) {
                    0 -> tv_all_category_1.text = TabdataList[i].TabName.toString()
                    1 -> tv_all_category_2.text = TabdataList[i].TabName.toString()
                    2 -> tv_all_category_3.text = TabdataList[i].TabName.toString()
                    3 -> tv_all_category_4.text = TabdataList[i].TabName.toString()
                    4 -> tv_all_category_5.text = TabdataList[i].TabName.toString()
                    5 -> tv_all_category_6.text = TabdataList[i].TabName.toString()
                    6 -> tv_all_category_7.text = TabdataList[i].TabName.toString()
                    7 -> tv_all_category_8.text = TabdataList[i].TabName.toString()
                }

            }
        }
    }

    fun setCategory() {
        tv_all_category_1.setOnClickListener {
            idx = TabdataList[0].category_idx!!
            intent.putExtra("result", TabdataList[0].category_idx)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        tv_all_category_2.setOnClickListener {
            idx = TabdataList[1].category_idx!!
            intent.putExtra("result", TabdataList[1].category_idx)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        tv_all_category_3.setOnClickListener {
            idx = TabdataList[2].category_idx!!
            intent.putExtra("result", TabdataList[2].category_idx)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        tv_all_category_4.setOnClickListener {
            idx = TabdataList[3].category_idx!!
            intent.putExtra("result", TabdataList[3].category_idx)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        tv_all_category_5.setOnClickListener {
            idx = TabdataList[4].category_idx!!
            intent.putExtra("result", TabdataList[4].category_idx)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        tv_all_category_6.setOnClickListener {
            idx = TabdataList[5].category_idx!!
            intent.putExtra("result", TabdataList[5].category_idx)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        tv_all_category_7.setOnClickListener {
            idx = TabdataList[6].category_idx!!
            intent.putExtra("result", TabdataList[6].category_idx)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        tv_all_category_8.setOnClickListener {
            idx = TabdataList[7].category_idx!!
            intent.putExtra("result", TabdataList[7].category_idx)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}


