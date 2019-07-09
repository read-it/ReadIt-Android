package com.computer.inu.readit_appjam.Activity

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_mypage__setting_alarm.*
import android.widget.Toast
import android.content.pm.PackageManager
import android.support.annotation.NonNull
import android.Manifest.permission
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

private val NOTIFICATION_PERMISSION_CODE = 123


class Mypage_Setting_alarm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage__setting_alarm)
        iv_mypage_setting_alarm_back_btn.setOnClickListener {
            finish()
        }

    }
}