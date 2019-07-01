package com.computer.inu.readit_appjam.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.computer.inu.readit_appjam.Adapter.ContentsRecyclerViewAdapter
import com.computer.inu.readit_appjam.Data.ContentsOverviewData

import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    lateinit var contentsRecyclerViewAdapter: ContentsRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var dataList: ArrayList<ContentsOverviewData> = ArrayList()
        dataList.add(
            ContentsOverviewData(
                "https://upload.wikimedia.org/wikipedia/commons/0/0d/Hong_Jun-pyo_at_the_Japanese_Prime_Minister%27s_Office_%28Cropped%29",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인"
            )
        )

        contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, dataList)
        rv_contents_all.adapter = contentsRecyclerViewAdapter
        rv_contents_all.layoutManager = LinearLayoutManager(context)
    }


}
