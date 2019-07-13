package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.TabdataList
import com.computer.inu.readit_appjam.Adapter.AdapterSpinner1
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Post.PostContentsAddResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_dial_log.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DialLogActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        setContentView(com.computer.inu.readit_appjam.R.layout.activity_dial_log)
        //var arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, arrayList)
        var adapterSpinner1 = AdapterSpinner1(this, TabdataList)

        //Adapter 적용
        spinner2.setAdapter(adapterSpinner1)

        rl_dial_comfirm.setOnClickListener {
            adapterSpinner1.getItemId(spinner2.selectedItemPosition)
            AddContentsPost(
                intent.getStringExtra("url"),
                adapterSpinner1.getItemId(spinner2.selectedItemPosition).toInt()
            )
            Handler().postDelayed(Runnable {
                finish()
            }, 500)//

        }

    }

    private fun AddContentsPost(url: String, idx: Int) {
        var jsonObject = JSONObject()
        jsonObject.put("contents_url", url)
        jsonObject.put("category_idx", idx)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postContentAddResponse: Call<PostContentsAddResponse> =
            networkService.postContentsAddResponse(
                "application/json",
                SharedPreferenceController.getAccessToken(this),
                gsonObject
            )
        postContentAddResponse.enqueue(object : Callback<PostContentsAddResponse> {
            override fun onFailure(call: Call<PostContentsAddResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PostContentsAddResponse>, response: Response<PostContentsAddResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()!!.message!!

                }
            }
        })

    }

}
