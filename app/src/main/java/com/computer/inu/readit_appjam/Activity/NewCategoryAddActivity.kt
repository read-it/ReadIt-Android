package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.TABCATEGORYFLAG
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Post.PostCategoryAddResponse
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_new_category_add.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewCategoryAddActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    lateinit var newCategory: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_category_add)

        category_add_cancel.setOnClickListener {
            finish()
        }

        category_add_ok.setOnClickListener {
            newCategory = category_edit_text.text.toString() // 추가할 카테고리명
            //val intent = Intent(this, ContentsToCategoryActivity::class.java)
            //intent.putExtra("name", newCategory)
            //startActivity(intent)
            postCategoryAddResponse()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    private fun postCategoryAddResponse() {
        val tmp_array = ArrayList<Int>()
        var jsonObject = JSONObject()
        jsonObject.put("category_name", newCategory)
        jsonObject.put("contents_idx", tmp_array)
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postCategoryAddResponse: Call<PostCategoryAddResponse> =
            networkService.postCategoryAddResponse(
                "application/json",
                SharedPreferenceController.getAccessToken(this), gsonObject
            )
        postCategoryAddResponse.enqueue(object : Callback<PostCategoryAddResponse> {
            override fun onFailure(call: Call<PostCategoryAddResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PostCategoryAddResponse>, response: Response<PostCategoryAddResponse>) {
                if (response.isSuccessful) {
                    //toast(response.body()!!.message)
                    TABCATEGORYFLAG = 1
                    finish()
                }
            }
        })

    }
}
