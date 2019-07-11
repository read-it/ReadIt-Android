package com.computer.inu.readit_appjam.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.PutCategoryNameResponse
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_category_setting_edit.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategorySettingEditActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var idx = -1
    lateinit var name : String
    lateinit var ch_name : String

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_setting_edit)

        val intent = getIntent()
        val pos = intent.getIntExtra("pos", 0)
        idx = intent.getIntExtra("category_idx", 0)
        name = intent.getStringExtra("category_name")
        category_edit_text.setText(name)
        category_edit_text.setHint(name)

        category_edit_ok.setOnClickListener {
            putCategoryNameResponse()
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

    private fun putCategoryNameResponse(){
        var jsonObject = JSONObject()
        jsonObject.put("category_name", category_edit_text.text.toString())
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val putCategoryNameResponse: Call<PutCategoryNameResponse> = networkService.putCategoryNameResponse(
            "application/json", SharedPreferenceController.getAccessToken(this), idx, gsonObject)
        putCategoryNameResponse.enqueue(object : Callback<PutCategoryNameResponse> {
            override fun onFailure(call: Call<PutCategoryNameResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PutCategoryNameResponse>, response: Response<PutCategoryNameResponse>) {
                if (response.isSuccessful) {
                    toast(response.body()!!.message)
                }
            }
        })
    }

}
