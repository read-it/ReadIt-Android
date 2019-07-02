package com.computer.inu.readit_appjam.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_settings_page.*
import org.jetbrains.anko.startActivity

class SettingsPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_page)
        iv_setting_back.setOnClickListener {
            finish()
        }
        tv_setting_change_profile.setOnClickListener {
            val changeProfileIntent = Intent(this, ChangeProfileActivity::class.java)
            startActivity(changeProfileIntent)
        }
        tv_setting_trash_can.setOnClickListener {
            val trashCanActivityIntent = Intent(this, TrashCanActivity::class.java)
            startActivity(trashCanActivityIntent)
        }
    }

}
