package com.computer.inu.readit_appjam.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.readit_appjam.Activity.SettingsPageActivity

import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.fragment_mypage.*
import org.jetbrains.anko.support.v4.startActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MypageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addFragment(ScrabFragment())
        ll_fragment_scrab_tab.setOnClickListener {
            replaceFragment(ScrabFragment())
        }
        ll_fragment_hilite_tab.setOnClickListener {
            replaceFragment(HiliteFragment())
        }
        settingsButton.setOnClickListener {
            startActivity<SettingsPageActivity>()
        }
    }
    private fun addFragment(fragment: Fragment){

        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.add(R.id.fl_sub_act_fragment, fragment)
        transaction.commit()
    }
    private fun replaceFragment(fragment: Fragment){
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fl_sub_act_fragment, fragment)
        transaction.commit()
    }
}