package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        tv_setting_change_password.setOnClickListener {
            startActivity<ChangePasswordActivity>()
        }

    }

}
