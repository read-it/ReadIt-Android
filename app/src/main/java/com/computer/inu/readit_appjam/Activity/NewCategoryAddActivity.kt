package com.computer.inu.readit_appjam.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_new_category_add.*

class NewCategoryAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_category_add)

        category_add_cancel.setOnClickListener{
            finish()
        }

        category_add_ok.setOnClickListener{
            val newCategory = category_edit_text.text.toString() // 추가할 카테고리명
            //카테고리 추가 통신
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}
