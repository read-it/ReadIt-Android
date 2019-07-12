package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.R
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable {
            startActivity<WalkThroughActivity>()
            finish()
        }, 2000)//

    }
}
