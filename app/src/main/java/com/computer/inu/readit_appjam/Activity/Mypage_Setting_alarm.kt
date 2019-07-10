package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_mypage__setting_alarm.*
import org.jetbrains.anko.backgroundDrawable

private val NOTIFICATION_PERMISSION_CODE = 123


class Mypage_Setting_alarm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage__setting_alarm)
        iv_mypage_setting_alarm_back_btn.setOnClickListener {
            finish()
        }
        sw_push_alarm.setOnClickListener {
            if (sw_push_alarm.isChecked == false)
                sw_readit_time.isChecked = false

        }
        sw_readit_time.setOnClickListener {
            if (sw_push_alarm.isChecked == false)
                sw_readit_time.isChecked = false
        }
        iv_alarm_image.setOnClickListener {
            if (sw_readit_time.isChecked == true)
                iv_alarm_image.setBackgroundResource(R.drawable.ic_mypage_alarm_orange)
            else
                iv_alarm_image.setBackgroundResource(R.drawable.ic_mypage_alarm_gray)
        }

    }
}