package com.computer.inu.readit_appjam.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.computer.inu.readit_appjam.Adapter.TrashCanRecyclerViewAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.DataXXXX
import com.computer.inu.readit_appjam.Network.Get.GetTrashCantResponse
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_trash_can.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrashCanActivity : AppCompatActivity() {
    lateinit var trashCanRecyclerViewAdapter: TrashCanRecyclerViewAdapter
    var data = ArrayList<DataXXXX>()
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trash_can)
        getTrashCanPost()
        iv_mypage_trashcan_back_btn.setOnClickListener {
            finish()
        }
    }

    private fun getTrashCanPost() {
        val getTrashCanPostResponse: Call<GetTrashCantResponse> = networkService.getTrashCanResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(this)
        )
        getTrashCanPostResponse.enqueue(object : Callback<GetTrashCantResponse> {
            override fun onFailure(call: Call<GetTrashCantResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetTrashCantResponse>, response: Response<GetTrashCantResponse>) {
                if (response.isSuccessful) {
                    data.clear()
                    data = response.body()!!.data!!
                    if (data.isEmpty()) {
                        rl_empty_trashcan_nocontents.visibility = View.VISIBLE
                    } else
                        rl_empty_trashcan_nocontents.visibility = View.GONE
                    trashCanRecyclerViewAdapter = TrashCanRecyclerViewAdapter(this@TrashCanActivity, data)
                    trashCanRecyclerViewAdapter.notifyDataSetChanged()
                    rl_trashcan.adapter = trashCanRecyclerViewAdapter
                    rl_trashcan.layoutManager = LinearLayoutManager(this@TrashCanActivity)

                }
            }
        })

    }
}
