package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.Fragment.HomeFragment.Companion.sort
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_main_home__more_btn_.*

class MainHome_More_btn_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home__more_btn_)

        when (sort) {
            1 -> {
                tv_home_more_new.setTextColor(Color.parseColor("#ff4106"))
                tv_home_more_unread.setTextColor(Color.parseColor("#191919"))
                tv_home_more_older.setTextColor(Color.parseColor("#191919"))
                tv_home_more_time.setTextColor(Color.parseColor("#191919"))
            }
            2 -> {
                tv_home_more_new.setTextColor(Color.parseColor("#191919"))
                tv_home_more_unread.setTextColor(Color.parseColor("#ff4106"))
                tv_home_more_older.setTextColor(Color.parseColor("#191919"))
                tv_home_more_time.setTextColor(Color.parseColor("#191919"))
            }
            3 -> {
                tv_home_more_new.setTextColor(Color.parseColor("#191919"))
                tv_home_more_unread.setTextColor(Color.parseColor("#191919"))
                tv_home_more_older.setTextColor(Color.parseColor("#ff4106"))
                tv_home_more_time.setTextColor(Color.parseColor("#191919"))
            }
            4 -> {
                tv_home_more_new.setTextColor(Color.parseColor("#191919"))
                tv_home_more_unread.setTextColor(Color.parseColor("#191919"))
                tv_home_more_older.setTextColor(Color.parseColor("#191919"))
                tv_home_more_time.setTextColor(Color.parseColor("#ff4106"))
            }
        }

        ll_newly_order.setOnClickListener {
            val intent: Intent = Intent()
            sort = 1
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        ll_donot_read_order.setOnClickListener {
            val intent: Intent = Intent()
            sort = 2
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        ll_old_order.setOnClickListener {
            val intent: Intent = Intent()
            sort = 3
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        ll_long_time_order.setOnClickListener {
            val intent: Intent = Intent()
            sort = 4
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}
