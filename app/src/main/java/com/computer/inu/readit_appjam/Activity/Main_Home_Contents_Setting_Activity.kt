package com.computer.inu.readit_appjam.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.PutContentsScrabResponse
import com.computer.inu.readit_appjam.Network.Put.PutDeleteContentResponse
import com.computer.inu.readit_appjam.Network.Put.PutMakeFixContentResponse
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_main__home__contents__setting_.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Main_Home_Contents_Setting_Activity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main__home__contents__setting_)
        var fixed_date = intent.getStringExtra("fixed_date")
        if (fixed_date.isNullOrEmpty()) {
            tv_home_contents_top_fix.text = "상단고정"
        } else {
            tv_home_contents_top_fix.text = "상단고정 해제"
        }

        if (intent.getIntExtra("scrap_flag", -1) == 1) {
            tv_home_contents_scrab.text = "스크랩 해제"
        } else {
            tv_home_contents_scrab.text = "스크랩"
        }


        ll_contents_delete.setOnClickListener {
            putDeleteContentResponse()
            finish()
        }

        ll_contents_category_modify.setOnClickListener {
            val pos = intent.getIntExtra("pos", 0)
            val category_idx = intent.getIntExtra("category_idx", 0)
            val contents_idx = intent.getIntExtra("contents_idx", 0)
            val intent_to = Intent(this, CategoryMoveActivity::class.java)
            intent_to.putExtra("pos", pos)
            intent_to.putExtra("category_idx", category_idx)
            intent_to.putExtra("contents_idx", contents_idx)
            startActivity(intent_to)
        }
        tv_home_contents_top_fix.setOnClickListener {
            putMakeFixContentResponse()  //상단고정
            finish()
        }
        ll_contents_scrab.setOnClickListener {
            putMakeScrabContentResponse()  //콘텐츠 스크랩
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    private fun putMakeFixContentResponse() {
        val putMakeFixContentResponse: Call<PutMakeFixContentResponse> = networkService.putMakeFixContentResponse(
            "application/json", SharedPreferenceController.getAccessToken(this), intent.getIntExtra("contents_idx", -1)
        )
        putMakeFixContentResponse.enqueue(object : Callback<PutMakeFixContentResponse> {
            override fun onFailure(call: Call<PutMakeFixContentResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<PutMakeFixContentResponse>,
                response: Response<PutMakeFixContentResponse>
            ) {
                if (response.isSuccessful) {

                }
            }
        })

    }

    private fun putMakeScrabContentResponse() {
        val putMakeScrabContentResponse: Call<PutContentsScrabResponse> = networkService.putContentsScrabtResponse(
            "application/json", SharedPreferenceController.getAccessToken(this), intent.getIntExtra("contents_idx", -1)
        )
        putMakeScrabContentResponse.enqueue(object : Callback<PutContentsScrabResponse> {
            override fun onFailure(call: Call<PutContentsScrabResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<PutContentsScrabResponse>,
                response: Response<PutContentsScrabResponse>
            ) {
                if (response.isSuccessful) {

                }
            }
        })

    }

    private fun putDeleteContentResponse() {
        val putDeleteContentResponse: Call<PutDeleteContentResponse> = networkService.putdeleteResponse(
            "application/json", SharedPreferenceController.getAccessToken(this), intent.getIntExtra("contents_idx", -1)
        )
        putDeleteContentResponse.enqueue(object : Callback<PutDeleteContentResponse> {
            override fun onFailure(call: Call<PutDeleteContentResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<PutDeleteContentResponse>,
                response: Response<PutDeleteContentResponse>
            ) {
                if (response.isSuccessful) {
                    toast(response.body()!!.message)
                }
            }
        })

    }

}
