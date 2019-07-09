package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_search_category.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList



class SearchCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_category)

        // 통신 -> 카테고리 목록 받아오기

        var dataList: ArrayList<String> = ArrayList()

        dataList.add("제발")
        dataList.add("됐으면")
        dataList.add("좋겟슴미다ㅜ")
        dataList.add("후")

        val picker: NumberPicker = picker_search
        val data = arrayOfNulls<String>(dataList.size)

        dataList.toArray(data)

        picker.wrapSelectorWheel = false // 순환 방지
        picker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS // editText 막기

        picker.minValue = 0
        picker.maxValue = (dataList.size - 1)
        picker.displayedValues = data

        picker.setOnValueChangedListener { picker, oldVal, newVal ->
            val picked = picker.value

            // 이렇게 하면 안되지만 일단...
            picker.setOnClickListener {
                // picked 값 갖고 이전 activity 카테고리명 수정
                val intent: Intent = Intent()
                intent.putExtra("category_name", picked)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}
