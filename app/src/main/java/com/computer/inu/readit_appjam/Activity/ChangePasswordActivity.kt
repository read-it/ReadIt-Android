package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.Put_Edit_Password_Response
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_change_password.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        mypage_change_password_back_btn.setOnClickListener {
            finish()
        }
        btn_change_password.setOnClickListener {
            EditPassword()
        }
    }

    private fun EditPassword() {
        var jsonObject = JSONObject()
        jsonObject.put("originalPassword", edt_original_password.text.toString())
        jsonObject.put("newPassword", edt_new_password.text.toString())
        jsonObject.put("reNewPassword", edt_password_confirm.text.toString())

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postEditPasswordResponse: Call<Put_Edit_Password_Response> =
            networkService.putEditPasswordResponse(
                "application/json",
                SharedPreferenceController.getAccessToken(this),
                gsonObject
            )
        postEditPasswordResponse.enqueue(object : Callback<Put_Edit_Password_Response> {
            override fun onFailure(call: Call<Put_Edit_Password_Response>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<Put_Edit_Password_Response>,
                response: Response<Put_Edit_Password_Response>
            ) {
                if (response.isSuccessful) {
                    finish()
                }
            }
        })

    }
}
