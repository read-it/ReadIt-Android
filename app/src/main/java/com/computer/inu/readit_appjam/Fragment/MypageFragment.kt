package com.computer.inu.readit_appjam.Fragment


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.computer.inu.readit_appjam.Activity.ChangeProfileActivity
import com.computer.inu.readit_appjam.Activity.Mypage_Setting_alarm
import com.computer.inu.readit_appjam.Activity.SettingsPageActivity
import com.computer.inu.readit_appjam.Activity.TrashCanActivity
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.GetMyPageResponse
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.fragment_mypage.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MypageFragment : Fragment() {
    companion object {
        var scrabnumber = 0
        var hilightnumber = 0
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    var configured_img: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMyProfileList()
        addFragment(ScrabFragment())
        if (scrabnumber.toString() != "0")
            tv_scrab_number.text = scrabnumber.toString()
        ll_fragment_scrab_tab.setOnClickListener {
            tv_hilight_number.setTextColor(Color.parseColor("#80ffffff"))
            tv_hilight_text.setTextColor(Color.parseColor("#80ffffff"))
            tv_scrab_number.setTextColor(Color.parseColor("#ffffff"))
            tv_scrab_text_tab.setTextColor(Color.parseColor("#ffffff"))
            replaceFragment(ScrabFragment())
        }
        ll_fragment_hilite_tab.setOnClickListener {
            tv_hilight_number.setTextColor(Color.parseColor("#ffffff"))
            tv_hilight_text.setTextColor(Color.parseColor("#ffffff"))
            tv_scrab_number.setTextColor(Color.parseColor("#80ffffff"))
            tv_scrab_text_tab.setTextColor(Color.parseColor("#80ffffff"))
            replaceFragment(HiliteFragment())
        }

        settingsButton.setOnClickListener {
            startActivity<SettingsPageActivity>() //셋팅
        }
        iv_trash_btn.setOnClickListener {
            startActivity<TrashCanActivity>() //쓰레기통
        }
        iv_changeProfile_btn.setOnClickListener {
            startActivity<ChangeProfileActivity>("configured_img" to configured_img) //프로필 수정
        }
        iv_mypage_alarm_btn.setOnClickListener {
            startActivity<Mypage_Setting_alarm>() // 알람 설정
        }

    }

    override fun onResume() {
        super.onResume()
        getMyProfileList()
        if (scrabnumber.toString() != "0")
            tv_scrab_number.text = scrabnumber.toString()
    }


    private fun addFragment(fragment: Fragment) {
        if (scrabnumber.toString() != "0")
            tv_scrab_number.text = scrabnumber.toString()
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.add(R.id.rv_mypage_contents_all, fragment)
        transaction.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        if (scrabnumber.toString() != "0")
            tv_scrab_number.text = scrabnumber.toString()
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.rv_mypage_contents_all, fragment)
        transaction.commit()
    }

    private fun getMyProfileList() {
        val getMyProfileResponse: Call<GetMyPageResponse> = networkService.getMypageResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(context!!)
        )
        getMyProfileResponse.enqueue(object : Callback<GetMyPageResponse> {
            override fun onFailure(call: Call<GetMyPageResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetMyPageResponse>, response: Response<GetMyPageResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.data!!.profile_img!!.isNullOrEmpty()) {

                    } else {
                        Glide.with(ctx)
                            .load(response.body()!!.data!!.profile_img)
                            .into(iv_mypage_profile_image)
                        configured_img = response.body()!!.data!!.profile_img
                    }
                    ctx.toast(response.body()!!.data!!.nickname.toString())
                    tv_my_nickname.text = response.body()!!.data!!.nickname.toString()
                    tv_mypage_email_address.text = response.body()!!.data!!.email.toString()

                }
            }
        })

    }

}
