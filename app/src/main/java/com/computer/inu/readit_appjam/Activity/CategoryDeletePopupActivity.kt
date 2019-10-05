package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Delete.DeleteCategoryResponse
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_category_delete_popup.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDeletePopupActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var idx = -1
    var default_idx = -1
    var flag = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_delete_popup)

        val intent = getIntent()
        idx = intent.getIntExtra("idx", 0) // 삭제 카테고리 idx
        default_idx = intent.getIntExtra("default_idx", 0)

        category_del_cancel.setOnClickListener {
            finish()
        }

        category_del_ok.setOnClickListener {
            //통신 flag
            //카테고리만 삭제, 콘텐츠와 함께 삭제 선택박스 비활성화
            /*
            if (rbtn_1.isChecked == true)
                flag = 0
            else if (rbtn_2.isChecked == true)
                flag = 1
            */
            deleteCategoryResponse()
            Handler().postDelayed(Runnable {
                finish()
            }, 200)

        }
    }

    private fun deleteCategoryResponse(){
        var jsonObject = JSONObject()
        jsonObject.put("default_idx", default_idx)
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val deleteCategoryResponse: Call<DeleteCategoryResponse> = networkService.deleteCategoryResponse(
            "application/json", SharedPreferenceController.getAccessToken(this), idx, 1, gsonObject)
        deleteCategoryResponse.enqueue(object : Callback<DeleteCategoryResponse> {
            override fun onFailure(call: Call<DeleteCategoryResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<DeleteCategoryResponse>, response: Response<DeleteCategoryResponse>) {
                if (response.isSuccessful) {

                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

}
