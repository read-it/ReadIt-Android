package com.computer.inu.readit_appjam.Activity

import android.app.Service
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import com.computer.inu.readit_appjam.Data.SoftKeyboard
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Post.PostSignupResponse
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    //키보드 설정
    //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        var checkValid_id: Boolean = false
        var checkValid_pw: Boolean = false
        var checkValid_pwCheck: Boolean = false
        btn_submitSignup.isClickable = false

        //onConfigurationChanged(configuration)

        // 키보드 동작 라이브러리 사용
        /*val mUnregister = KeyboardVisibilityEvent.registerEventListener(this, KeyboardVisibilityEventListener(){
            @Override
            fun onVisibilityChanged(isOpen: Boolean){
                val lp = container_signup.layoutParams as RelativeLayout.LayoutParams
                lp.bottomMargin = 235
                container_signup.layoutParams = lp
            }
        })*/

        var controlManager = getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        var softKeyboard = SoftKeyboard(view_signup, controlManager)

        //softKeyboard.setSoftKeyboardCallback(new Soft)

        edt_signup_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val emailPattern = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"

                if (!Pattern.matches(emailPattern, s.toString()))
                    tv_idError.setText("* 유효한 이메일이 아닙니다.")
                else {
                    tv_idError.setText(null)
                    checkValid_id = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_signup_id.text.toString() != "" && edt_signup_pw.text.toString() != "" && edt_signup_pwCheck.text.toString() != "") {
                    btn_submitSignup.isClickable = true
                    btn_submitSignup.setImageResource(R.drawable.btn_login_orange)
                } else {
                    btn_submitSignup.setImageResource(R.drawable.btn_login_gray)
                    btn_submitSignup.isClickable = false
                }
            }
        })

        edt_signup_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val pwPattern = "^[a-zA-Z0-9!@.#$%^&*?_~]{8,12}$"

                if (!Pattern.matches(pwPattern, s.toString()))
                    tv_pwError.setText("* 영어, 숫자 조합 8-12자리 입력해주세요.")
                else {
                    tv_pwError.setText(null)
                    checkValid_pw = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_signup_id.text.toString() != "" && edt_signup_pw.text.toString() != "" && edt_signup_pwCheck.text.toString() != "") {
                    btn_submitSignup.setImageResource(R.drawable.btn_login_orange)
                    btn_submitSignup.isClickable = true
                } else {
                    btn_submitSignup.setImageResource(R.drawable.btn_login_gray)
                    btn_submitSignup.isClickable = false
                }
            }
        })

        edt_signup_pwCheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (edt_signup_pwCheck.text.toString().equals(edt_signup_pw.text.toString())) {
                    tv_pwCheckError.setText(null)
                    checkValid_pwCheck = true
                } else
                    tv_pwCheckError.setText("* 비밀번호가 일치하지 않습니다.")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edt_signup_id.text.toString() != "" && edt_signup_pw.text.toString() != "" && edt_signup_pwCheck.text.toString() != "") {
                    btn_submitSignup.setImageResource(R.drawable.btn_login_orange)
                    btn_submitSignup.isClickable = true
                } else {
                    btn_submitSignup.setImageResource(R.drawable.btn_login_gray)
                    btn_submitSignup.isClickable = false
                }
            }
        })

        btn_submitSignup.setOnClickListener {

            val signup_id = edt_signup_id.text.toString()
            val signup_pw = edt_signup_pw.text.toString()
            val signup_pwCheck = edt_signup_pwCheck.text.toString()

            if (isValid(
                    signup_id,
                    signup_pw,
                    signup_pwCheck
                ) && checkValid_id == true && checkValid_pw == true && checkValid_pwCheck == true
            ) {
                SignUpPost()
                startActivity<LoginActivity>()
            }

            else
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
        }

        /*TedKeyboardObserver(this).listen {
            val lp = container_signup.layoutParams as RelativeLayout.LayoutParams
            lp.bottomMargin = 135
            container_signup.layoutParams = lp
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun isValid(u_id: String, u_pw: String, u_pwCheck: String): Boolean {
        val animTransUp = AnimationUtils.loadAnimation(this, R.anim.anim_translate_up)

        if (u_id == "") {
            edt_signup_id.requestFocus()
            img_circle_signupID.startAnimation(animTransUp)

        } else if (u_pw == "") {
            edt_signup_pw.requestFocus()
            img_circle_signupPW.startAnimation(animTransUp)
        } else if (u_pwCheck == "") {
            edt_signup_pwCheck.requestFocus()
            img_circle_signupPWCheck.startAnimation(animTransUp)
        } else if (u_pw != u_pwCheck) {
            img_circle_signupPWCheck.startAnimation(animTransUp)
        } else
            return true
        return false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val lp = container_signup.layoutParams as RelativeLayout.LayoutParams

        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            lp.bottomMargin = 235
            container_signup.layoutParams = lp
            //view_signup.addView(container_signup, lp)
            //setContentView(view_signup)
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            lp.bottomMargin = 135
            container_signup.layoutParams = lp
            //view_signup.addView(container_signup, lp)
            //setContentView(view_signup)
        }
    }

    private fun SignUpPost() {
        //Json 형식의 객체 만들기
        var jsonObject = JSONObject()
        jsonObject.put("email", edt_signup_id.text.toString())
        jsonObject.put("password", edt_signup_pw.text.toString())
        jsonObject.put("repassword", edt_signup_pwCheck.text.toString())

        //Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSignUpResponse: Call<PostSignupResponse> =
            networkService.postSignupResponse("application/json", gsonObject)
        postSignUpResponse.enqueue(object : Callback<PostSignupResponse> {
            override fun onFailure(call: Call<PostSignupResponse>, t: Throwable) {
            }

            //통신 성공 시 수행되는 메소드
            override fun onResponse(call: Call<PostSignupResponse>, response: Response<PostSignupResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()!!.message!!
                    if (message == "회원가입 성공") {
                        toast(message)
                        startActivity<MainActivity>()
                    } else {
                        toast(message)
                    }
                }
            }
        })
    }
}
