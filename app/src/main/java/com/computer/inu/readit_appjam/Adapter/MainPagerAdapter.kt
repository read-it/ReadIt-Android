package com.computer.inu.readit_appjam.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.computer.inu.readit_appjam.Fragment.MypageFragment
import com.computer.inu.readit_appjam.Fragment.HomeFragment


class MainPagerAdapter(fm: FragmentManager, private val num_frament: Int) : FragmentStatePagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment? {
        return when (p0) {
            0 -> HomeFragment()
            1 -> MypageFragment()
            else -> null

        }
    }
    override fun getCount(): Int {
        return num_frament
    }
}