package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.R

class MainHome_More_btn_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home__more_btn_)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(
            com.computer.inu.readit_appjam.R.anim.stay,
            com.computer.inu.readit_appjam.R.anim.up_to_down
        )
    }
}
