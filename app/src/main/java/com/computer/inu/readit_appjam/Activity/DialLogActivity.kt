package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.TabdataList
import com.computer.inu.readit_appjam.Adapter.AdapterSpinner1
import kotlinx.android.synthetic.main.activity_dial_log.*


class DialLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        setContentView(com.computer.inu.readit_appjam.R.layout.activity_dial_log)

        //var arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayList)
        var adapterSpinner1 = AdapterSpinner1(this, TabdataList)

        //Adapter 적용
        spinner2.setAdapter(adapterSpinner1)

        rl_dial_comfirm.setOnClickListener {
            finish()
        }

    }
}
