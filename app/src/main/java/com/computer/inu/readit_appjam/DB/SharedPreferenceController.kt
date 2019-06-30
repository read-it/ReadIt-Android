package com.computer.inu.readit_appjam.DB

import android.content.Context

object SharedPreferenceController {

    private val LOGIN = "LOGIN"

    fun setToken(context: Context, authorization: String) {
        val pref = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("u_id", authorization)
        editor.commit()
    }

    fun getToken(context: Context): String {
        val pref = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
        return pref.getString("u_id", "")
    }

    fun clearToken(context: Context) {
        val pref = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }

}