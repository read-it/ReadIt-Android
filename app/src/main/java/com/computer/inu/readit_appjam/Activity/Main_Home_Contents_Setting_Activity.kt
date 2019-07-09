package com.computer.inu.readit_appjam.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_main__home__contents__setting_.*

class Main_Home_Contents_Setting_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main__home__contents__setting_)
        ll_contents_category_modify.setOnClickListener {
            val intent = Intent(this, CategoryMoveActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }
}
