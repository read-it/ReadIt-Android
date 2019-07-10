package com.computer.inu.readit_appjam.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_category_delete_popup.*
import org.jetbrains.anko.toast

class CategoryDeletePopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_delete_popup)

        val intent = Intent()
        var idx = intent.getIntExtra("idx", 0) // 삭제 카테고리 idx

        category_del_cancel.setOnClickListener {
            finish()
        }

        category_del_ok.setOnClickListener {
            //통신 flag
            var flag = -1
            if (rbtn_1.isChecked == true)
                flag = 0
            else if (rbtn_2.isChecked == true)
                flag = 1
            toast(flag.toString())
            //finish()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

}
