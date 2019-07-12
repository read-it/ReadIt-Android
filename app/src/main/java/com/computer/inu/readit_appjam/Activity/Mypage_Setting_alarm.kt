package com.computer.inu.readit_appjam.Activity

import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.TimePicker
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_mypage__setting_alarm.*

//private val NOTIFICATION_PERMISSION_CODE = 123


class Mypage_Setting_alarm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage__setting_alarm)
        val mPickTimeBtn = findViewById<TimePicker>(R.id.timepicker_alarm)
        val textView = findViewById<TextView>(R.id.tv_alarm_text)

        iv_mypage_setting_alarm_back_btn.setOnClickListener {
            finish()
        }
        sw_push_alarm.setOnClickListener {

            if (sw_push_alarm.isChecked == false) {
                sw_readit_time.isChecked = false
                iv_alarm_image.setImageResource(R.drawable.ic_mypage_alarm_gray)
            }
        }
        sw_readit_time.setOnClickListener {
            if (sw_push_alarm.isChecked == false)
                sw_readit_time.isChecked = false
            else if (sw_readit_time.isChecked == true) {
                iv_alarm_image.setImageResource(R.drawable.ic_mypage_alarm_orange)
            } else if (sw_readit_time.isChecked == false) {
                iv_alarm_image.setImageResource(R.drawable.ic_mypage_alarm_gray)
            }
        }
        /* tv_alarm_text.setOnClickListener {
             if(sw_readit_time.isChecked==true)
                 tv_alarm_text.setTextColor()
         }*/
        /*timepicker_alarm.setOnClickListener {
            if(sw_push_alarm.isChecked == false)
                timepicker_alarm.isEnabled = false

        }*/

        timepicker_alarm.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }
}
