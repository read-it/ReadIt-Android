package com.computer.inu.readit_appjam.DB

import android.content.Context

object SharedPreferenceController {

    private val LOGIN = "LOGIN"
    private val REFRESH = "REFRESH"
    private val CLIPDATA = "CLIPDATA"
    private val categoryIdx = "AllCategory"
    private val workthrough = "workthrough"
    private val PushAlerm = "PushAlerm"
    private val READITTIME = "READITTIME"
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

    fun setRefreshToken(context: Context, authorization: String) {
        val pref = context.getSharedPreferences(REFRESH, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("refresh_token", authorization)
        editor.commit()
    }

    fun getRefreshToken(context: Context): String {
        val pref = context.getSharedPreferences(REFRESH, Context.MODE_PRIVATE)
        return pref.getString("refresh_token_token", "")
    }

    fun clearRefreshToken(context: Context) {
        val pref = context.getSharedPreferences(REFRESH, Context.MODE_PRIVATE)
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

    // 전체 카테고리 idx
    fun setCategoryIdx(context: Context, idx: Int) {
        val pref = context.getSharedPreferences(categoryIdx, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("categoryIdx", idx)
        editor.commit()
    }

    fun getCategoryIdx(context: Context): Int {
        val pref = context.getSharedPreferences(categoryIdx, Context.MODE_PRIVATE)
        return pref.getInt("categoryIdx", -1)
    }

    fun clearCategoryIdx(context: Context) {
        val pref = context.getSharedPreferences(categoryIdx, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }

    // 알람
    fun setAlerm(context: Context, Alerm: String) {
        val pref = context.getSharedPreferences(PushAlerm, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("PushAlerm", Alerm)
        editor.commit()
    }

    fun getAlerm(context: Context): String {
        val pref = context.getSharedPreferences(PushAlerm, Context.MODE_PRIVATE)
        return pref.getString("PushAlerm", "")
    }

    fun clearAlerm(context: Context) {
        val pref = context.getSharedPreferences(PushAlerm, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }

    //리딧알림
    fun setReadItAlerm(context: Context, READIT: String) {
        val pref = context.getSharedPreferences(READITTIME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("READITTIME", READIT)
        editor.commit()
    }

    fun getReadItAlerm(context: Context): String {
        val pref = context.getSharedPreferences(READITTIME, Context.MODE_PRIVATE)
        return pref.getString("READITTIME", "")
    }

    fun cleaReadItAlerm(context: Context) {
        val pref = context.getSharedPreferences(READITTIME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }
}