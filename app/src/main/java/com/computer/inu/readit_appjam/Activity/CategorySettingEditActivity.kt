package com.computer.inu.readit_appjam.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.PutCategoryNameChange
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_category_setting_edit.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategorySettingEditActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_setting_edit)

        val intent = getIntent()
        val pos = intent.getIntExtra("category_idx", 0)

        //  category_edit_text.setText(intent.getStringExtra("category_name"))
        category_edit_text.setHint(intent.getStringExtra("category_name"))

        category_edit_ok.setOnClickListener {
            val i = Intent()
            i.putExtra("pos", pos)
            i.putExtra("name", category_edit_text.text.toString())
            ChagngeCategoryName(pos)    //서버통신
            //    setResult(Activity.RESULT_OK, i)
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

    private fun ChagngeCategoryName(categoryidx: Int) {
        var jsonObject = JSONObject()
        jsonObject.put("category_name", category_edit_text.text.toString())


        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val putChangeCategoryNameResponse: Call<PutCategoryNameChange> =
            networkService.putChangeCategoryNameResponse(
                "application/json",
                SharedPreferenceController.getAccessToken(this),
                categoryidx,
                gsonObject
            )
        putChangeCategoryNameResponse.enqueue(object : Callback<PutCategoryNameChange> {
            override fun onFailure(call: Call<PutCategoryNameChange>, t: Throwable) {
            }

            override fun onResponse(call: Call<PutCategoryNameChange>, response: Response<PutCategoryNameChange>) {
                if (response.isSuccessful) {
                    val message = response.body()!!.message!!
                    toast(message)

                }
            }
        })

    }
}
