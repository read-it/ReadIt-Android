package com.computer.inu.readit_appjam.Adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.computer.inu.readit_appjam.Fragment.*

class Walkthrough_Viewpager_Adapter (fm: FragmentManager?,  val num_fragment: Int) : FragmentStatePagerAdapter(fm){
    override fun getItem(p0: Int): Fragment? {
       // var fragment: Walkthrough_Viewpager_Adapter = Walkthrough_Viewpager_Adapter()

        return when (p0) {
            0 -> WalkThrough_Fragment_1()
            1 -> WalkThrough_Fragment_2()
            2 -> WalkThrough_Fragment_3()
            3 -> WalkThrough_Fragment_4()
            4 -> WalkThrough_Fragment_5()
            else -> null
        }
        //fragment.arguments = bundle

    }

    override fun getCount(): Int {
        return num_fragment
    }

}