package com.computer.inu.readit_appjam.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern
import android.content.res.Configuration.HARDKEYBOARDHIDDEN_YES
import android.content.res.Configuration.HARDKEYBOARDHIDDEN_NO
import android.content.res.Configuration
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Button
import android.widget.RelativeLayout
import com.computer.inu.readit_appjam.R


class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        var checkValid_id: Boolean = false
        var checkValid_pw: Boolean = false
        var checkValid_pwCheck: Boolean = false
        btn_submitSignup.isClickable = false

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
            )
                Toast.makeText(this, "닉네임 activity", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
        }
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
}
