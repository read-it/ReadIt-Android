package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.Adapter.Setting_Guide_Walkthrough_Adapter
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_walk_through__setting__guide_.*

class WalkThrough_Setting_Guide_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk_through__setting__guide_)

        configureMainTab()
    }

    private fun configureMainTab() {
        vp_setting_guide_walkthrough.adapter = Setting_Guide_Walkthrough_Adapter(supportFragmentManager, 5)
        vp_setting_guide_walkthrough.offscreenPageLimit = 2
        tl_setting_guide_walkthrough_dot_indicator.setupWithViewPager(vp_setting_guide_walkthrough)
    }
}
