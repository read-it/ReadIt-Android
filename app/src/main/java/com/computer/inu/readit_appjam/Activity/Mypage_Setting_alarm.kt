package com.computer.inu.readit_appjam.Activity

import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.PutPushAlermResponse
import com.computer.inu.readit_appjam.Network.Put.PutReadItTimeResponse
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_mypage__setting_alarm.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//private val NOTIFICATION_PERMISSION_CODE = 123


class Mypage_Setting_alarm : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage__setting_alarm)
        val mPickTimeBtn = findViewById<TimePicker>(R.id.timepicker_alarm)
        val textView = findViewById<TextView>(R.id.tv_alarm_text)
        if (SharedPreferenceController.getAlerm(this).isNotEmpty()) {
            if (SharedPreferenceController.getAlerm(this) == "on" && SharedPreferenceController.getReadItAlerm(this) == "on") {
                sw_push_alarm.isChecked = true
                sw_readit_time.isChecked = true
            } else if (SharedPreferenceController.getAlerm(this) == "on") {
                sw_push_alarm.isChecked = true
            } else {
                sw_push_alarm.isChecked = false
                sw_readit_time.isChecked = false
            }
        }
        iv_mypage_setting_alarm_back_btn.setOnClickListener {
            finish()
        }
        sw_push_alarm.setOnClickListener {
            SharedPreferenceController.cleaReadItAlerm(this)
            SharedPreferenceController.clearAlerm(this)
            if (sw_push_alarm.isChecked == false) {
                sw_readit_time.isChecked = false
                SharedPreferenceController.setReadItAlerm(this, "off")
                SharedPreferenceController.setAlerm(this, "off")
                timepicker_alarm.visibility = View.INVISIBLE
                PutAlermPost(0)  // 알람 하는 통신 0
                iv_alarm_image.setImageResource(R.drawable.ic_mypage_alarm_gray)
            } else {
                SharedPreferenceController.setAlerm(this, "on")
            }
        }
        sw_readit_time.setOnClickListener {
            SharedPreferenceController.cleaReadItAlerm(this)
            if (sw_push_alarm.isChecked == false) {
                SharedPreferenceController.setReadItAlerm(this, "off")
                timepicker_alarm.visibility = View.INVISIBLE
                sw_readit_time.isChecked = false
                PutAlermPost(0)  // 알람 하는 통신 1
            }
            else if (sw_readit_time.isChecked == true) {
                SharedPreferenceController.setReadItAlerm(this, "on")
                timepicker_alarm.visibility = View.VISIBLE
                PutAlermPost(1)    // 알람 하는 통신 1
                iv_alarm_image.setImageResource(R.drawable.ic_mypage_alarm_orange)
            } else if (sw_readit_time.isChecked == false) {
                SharedPreferenceController.setReadItAlerm(this, "off")
                timepicker_alarm.visibility = View.INVISIBLE
                PutAlermPost(0)   // 알람 하는 통신 0
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
        var HH: String
        var mm: String
        var Time: String

        timepicker_alarm.setOnClickListener {

            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView.text = SimpleDateFormat("HH:mm").format(cal.time)
                Time = removeStringNumber(SimpleDateFormat("HH:mm").format(cal.time))
                HH = Time.substring(0, 2)
                mm = Time.substring(2, 4)

                PutReadITimePost(1, HH, mm)
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

    fun removeStringNumber(str: String): String {
        return str.replace("[^0-9]".toRegex(), "")
    }

    private fun PutAlermPost(alarm_flag: Int) {
        val postAlarmResponse: Call<PutPushAlermResponse> =
            networkService.putPushAlermResponse(
                "application/json",
                SharedPreferenceController.getAccessToken(this),
                alarm_flag
            )
        postAlarmResponse.enqueue(object : Callback<PutPushAlermResponse> {
            override fun onFailure(call: Call<PutPushAlermResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PutPushAlermResponse>, response: Response<PutPushAlermResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()!!.message!!
                    toast(message)
                }
            }
        })

    }

    private fun PutReadITimePost(readittime_flag: Int, HH: String, mm: String) {
        var jsonObject = JSONObject()
        jsonObject.put("alarm_hour", HH)
        jsonObject.put("alarm_minute", mm)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postAlarmResponse: Call<PutReadItTimeResponse> =
            networkService.putReadItTimeResponse(
                "application/json",
                SharedPreferenceController.getAccessToken(this),
                readittime_flag,
                gsonObject
            )
        postAlarmResponse.enqueue(object : Callback<PutReadItTimeResponse> {
            override fun onFailure(call: Call<PutReadItTimeResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PutReadItTimeResponse>, response: Response<PutReadItTimeResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()!!.message!!
                    toast(message)
                }
            }
        })

    }
}
