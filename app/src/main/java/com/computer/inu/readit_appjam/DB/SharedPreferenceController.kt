package com.computer.inu.readit_appjam.DB

import android.content.Context

object SharedPreferenceController {

    private val LOGIN = "LOGIN"
    private val CLIPDATA = "CLIPDATA"

    // 토큰
    fun setAccessToken(context: Context, authorization: String) {
        val pref = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("access_token", authorization)
        editor.commit()
    }

    fun getAccessToken(context: Context): String {
        val pref = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
        return pref.getString("access_token", "")
    }

    fun clearAccessToken(context: Context) {
        val pref = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }

    // 클립보드 데이터
    fun setClip(context: Context, authorization: String) {
        val pref = context.getSharedPreferences(CLIPDATA, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("clip_data", authorization)
        editor.commit()
    }

    fun getClip(context: Context): String {
        val pref = context.getSharedPreferences(CLIPDATA, Context.MODE_PRIVATE)
        return pref.getString("clip_data", "")
    }

    fun clearClip(context: Context) {
        val pref = context.getSharedPreferences(CLIPDATA, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }

}