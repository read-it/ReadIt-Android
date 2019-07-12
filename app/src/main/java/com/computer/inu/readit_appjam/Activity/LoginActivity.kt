package com.computer.inu.readit_appjam.Activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.AnimationUtils
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Post.PostSigninResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.notificationManager
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.computer.inu.readit_appjam.R.layout.activity_login)

        pushAlarm()
        edt_login_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_login_id.text.toString() != "" && edt_login_pw.text.toString() != "") {
                    btn_submitLogin.isClickable = true
                    btn_submitLogin.setImageResource(com.computer.inu.readit_appjam.R.drawable.btn_login_orange)
                } else {
                    btn_submitLogin.isClickable = false
                    btn_submitLogin.setImageResource(com.computer.inu.readit_appjam.R.drawable.btn_login_gray)
                }
            }
        })
        edt_login_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_login_id.text.toString() != "" && edt_login_pw.text.toString() != "") {
                    btn_submitLogin.isClickable = true
                    btn_submitLogin.setImageResource(com.computer.inu.readit_appjam.R.drawable.btn_login_orange)
                } else {
                    btn_submitLogin.isClickable = false
                    btn_submitLogin.setImageResource(com.computer.inu.readit_appjam.R.drawable.btn_login_gray)
                }
            }
        })
// 인텐트 정보가 있는 경우 실행
        //공유하기 테스트 입니다.
        val intent = intent
        val action = intent.action
        val type = intent.type
        var sharedText = ""

        if (Intent.ACTION_SEND == action && type != null && SharedPreferenceController.getAccessToken(this).isNotEmpty()) {
            if ("text/plain" == type) {
                startActivity<MainActivity>("url" to intent.getStringExtra(Intent.EXTRA_TEXT))
                finish()
            }
        } else if (SharedPreferenceController.getAccessToken(this).isNotEmpty()) {
            startActivity<MainActivity>() // 자동로그인
            finish()
        }


        btn_submitLogin.setOnClickListener {

            val login_id = edt_login_id.text.toString()
            val login_pw = edt_login_pw.text.toString()

            if (isValid(login_id, login_pw))
                SigninPost()
            //startActivity<MainActivity>()
            // 통신 (editText에 에러메시지 띄어주기)
        }

        txt_signUp.setOnClickListener {
            startActivity<SignupActivity>()
            finish()
        }
    }

    fun isValid(u_id: String, u_pw: String): Boolean {
        val animTransUp = AnimationUtils.loadAnimation(this, com.computer.inu.readit_appjam.R.anim.anim_translate_up)
        if (u_id == "") {
            edt_login_id.requestFocus()
            img_circleID.startAnimation(animTransUp)

        } else if (u_pw == "") {
            edt_login_pw.requestFocus()
            img_circlePW.startAnimation(animTransUp)
        } else return true
        return false
    }

    // 키보드 (hardkeyboard만 가능)
    /*override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            lp.bottomMargin = 235
            view_signup.addView(container_signup, lp)
            setContentView(view_signup)
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            lp.bottomMargin = 135
            view_signup.addView(container_signup, lp)
            setContentView(view_signup)
        }
    }*/
    fun pushAlarm() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelId = "default_channel_id"
            val channelDescription = "Default Channel"
            var notificationChannel = notificationManager.getNotificationChannel(channelId)
            if (notificationChannel == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH //Set the importance level
                notificationChannel = NotificationChannel(channelId, channelDescription, importance)
                notificationChannel!!.setLightColor(Color.GREEN) //Set if it is necesssary
                notificationChannel!!.enableVibration(true) //Set if it is necesssary
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }
    private fun SigninPost() {
        var jsonObject = JSONObject()
        jsonObject.put("email", edt_login_id.text.toString())
        jsonObject.put("password", edt_login_pw.text.toString())

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSignInResponse: Call<PostSigninResponse> =
            networkService.postSigninResponse("application/json", gsonObject)
        postSignInResponse.enqueue(object : Callback<PostSigninResponse> {
            override fun onFailure(call: Call<PostSigninResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PostSigninResponse>, response: Response<PostSigninResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()!!.message!!
                    if (message == "로그인 성공") {
                        // 토큰 저장
                        SharedPreferenceController.clearAccessToken(this@LoginActivity)
                        SharedPreferenceController.setAccessToken(
                            applicationContext,
                            response.body()!!.data.accesstoken.toString()
                        )
                        startActivity<MainActivity>()
                        finish()
                    } else {
                        toast(message)
                    }
                }
            }
        })

    }
}
