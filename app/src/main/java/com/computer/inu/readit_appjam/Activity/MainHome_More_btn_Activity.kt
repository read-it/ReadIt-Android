package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.computer.inu.readit_appjam.Fragment.HomeFragment.Companion.sort
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_all_category_view.*
import kotlinx.android.synthetic.main.activity_main__home__contents__setting_.*
import kotlinx.android.synthetic.main.activity_main_home__more_btn_.*

class MainHome_More_btn_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home__more_btn_)

        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.down_to_up)
        home_more_container.visibility = View.VISIBLE
        home_more_container.startAnimation(animation)

        home_more_back.setOnClickListener {
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.up_to_down)
            home_more_container.visibility = View.GONE
            home_more_container.startAnimation(animation)

            Handler().postDelayed(Runnable {
                finish()
            }, 200)//
        }

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

    override fun onBackPressed() {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.up_to_down)
        home_more_container.visibility = View.GONE
        home_more_container.startAnimation(animation)

        Handler().postDelayed(Runnable {
            super.onBackPressed()
        }, 200)//
    }
}
