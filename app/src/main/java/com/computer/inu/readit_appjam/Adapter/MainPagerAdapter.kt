package com.computer.inu.readit_appjam.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.computer.inu.readit_appjam.Fragment.HomeFragment
import com.computer.inu.readit_appjam.Fragment.MypageFragment

class MainPagerAdapter(fm: FragmentManager, private val num_frament: Int) : FragmentStatePagerAdapter(fm) {
/*
companion object {
    private var allProductMainFragment: AllProductMainFragment? = null
    private var newProductMainFragment: NewProductMainFragment? = null
    private var endProductMainFragment: EndProductMainFragment? =null

    @Synchronized
    fun getAllProductMainFragment(): AllProductMainFragment{
        if (allProductMainFragment==null) allProductMainFragment= AllProductMainFragment()
        return allProductMainFragment!!
    }
    @Synchronized
    fun getEndProductMainFragment(): EndProductMainFragment{
        if (endProductMainFragment==null) endProductMainFragment= EndProductMainFragment()
        return endProductMainFragment!!
    }

    @Synchronized
    fun getnewProductMainFragment(): NewProductMainFragment{
        if (newProductMainFragment==null) newProductMainFragment= NewProductMainFragment()
        return newProductMainFragment!!
    }
}
*/


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