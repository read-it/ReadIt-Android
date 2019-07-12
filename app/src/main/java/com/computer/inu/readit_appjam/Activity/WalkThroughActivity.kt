package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.Adapter.Walkthrough_Viewpager_Adapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_walk_through.*

class WalkThroughActivity : AppCompatActivity() {

    /*private lateinit var viewpager: ViewPager
    private lateinit var tabs: TabLayout*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk_through)
        SharedPreferenceController.clearAccessToken(this)
        configureMainTab()
    }

    private fun configureMainTab() {
        vp_walkthrough.adapter = Walkthrough_Viewpager_Adapter(supportFragmentManager, 5)
        vp_walkthrough.offscreenPageLimit = 2
        tl_walkthrough_dot_indicator.setupWithViewPager(vp_walkthrough)
    }
}

