package com.computer.inu.readit_appjam.Activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.R
import org.jetbrains.anko.notificationManager
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        pushAlarm()
        val intent = intent
        val action = intent.action
        val type = intent.type
        var sharedText = ""
        if (Intent.ACTION_SEND == action && type != null && SharedPreferenceController.getAccessToken(this).isNotEmpty()) {
            if ("text/plain" == type) {
                startActivity<MainActivity>("url" to intent.getStringExtra(Intent.EXTRA_TEXT))
                finish()
            }
        } else {
            Handler().postDelayed(Runnable {
                if (SharedPreferenceController.getAccessToken(this).isNullOrEmpty()) {
                    startActivity<WalkThroughActivity>()
                    finish()
                } else {
                    startActivity<MainActivity>()
                    finish()
                }
            }, 1500)//
        }

    }

    fun pushAlarm() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelId = "default_channel_id"
            val channelDescription = "Default Channel"
            var notificationChannel = notificationManager.getNotificationChannel(channelId)
            if (notificationChannel == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH //Set the importance level
                notificationChannel = NotificationChannel(channelId, channelDescription, importance)
                notificationChannel!!.setLightColor(Color.GREEN) //Set if it is necesssary
                notificationChannel!!.enableVibration(true) //Set if it is necesssary
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }

}
