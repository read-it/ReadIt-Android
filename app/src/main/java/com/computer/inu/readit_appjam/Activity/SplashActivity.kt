package com.computer.inu.readit_appjam.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.R
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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
