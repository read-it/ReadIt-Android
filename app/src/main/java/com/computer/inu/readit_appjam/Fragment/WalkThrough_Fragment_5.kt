package com.computer.inu.readit_appjam.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.readit_appjam.Activity.LoginActivity
import com.computer.inu.readit_appjam.Activity.MainActivity
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.fragment_walkthrough_5.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import java.util.zip.Inflater

class WalkThrough_Fragment_5 : Fragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_walkthrough_start.setOnClickListener {
            startActivity<LoginActivity>()
            activity!!.finish()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = inflater.inflate(R.layout.fragment_walkthrough_5, container, false);

        /*val message = arguments!!.getString(EXTRA_MESSAGE)

        var textView: TextView = view!!.findViewById(R.id.text)*/
        /*textView!!.text = message*/

        return view
    }
}
