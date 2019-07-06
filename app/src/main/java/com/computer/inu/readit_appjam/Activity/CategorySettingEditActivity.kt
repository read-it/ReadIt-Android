package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Window
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_category_setting_edit.*

class CategorySettingEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_setting_edit)

        val intent = getIntent()
        val pos = intent.getIntExtra("pos", 0)
        category_edit_text.setText(intent.getStringExtra("name"))

        category_edit_ok.setOnClickListener {
            val i = Intent()
            i.putExtra("pos", pos)
            i.putExtra("name", category_edit_text.text.toString())
            setResult(Activity.RESULT_OK, i)
            finish()
        }

        category_edit_cancel.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

}
