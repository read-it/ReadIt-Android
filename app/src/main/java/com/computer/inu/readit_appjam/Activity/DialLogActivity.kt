package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_dial_log.*
import java.util.*


class DialLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(com.computer.inu.readit_appjam.R.layout.activity_dial_log)

        var arrayList: ArrayList<String> = ArrayList()
        arrayList.add("1번")
        arrayList.add("2번")
        arrayList.add("3번")
        arrayList.add("4번")
        arrayList.add("5번")
        arrayList.add("6번")

        var arrayAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item, arrayList
        )

        spinner2.adapter = arrayAdapter

        tv_dialog_ok.setOnClickListener {
            finish()
        }

    }
}
