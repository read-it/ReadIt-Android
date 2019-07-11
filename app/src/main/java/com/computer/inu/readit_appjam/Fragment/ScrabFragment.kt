package com.computer.inu.readit_appjam.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.readit_appjam.Adapter.ScrabContentsRecyclerViewAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.DataXX
import com.computer.inu.readit_appjam.Network.Get.GetMypageScrapList
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.fragment_scrab.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ScrabFragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var scrabContentsRecyclerViewAdapter: ScrabContentsRecyclerViewAdapter
    var data = ArrayList<DataXX>()

    // lateinit var contentsRecyclerViewAdapter: MypageScrabContentsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scrab, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getScrabList()
    }

    override fun onResume() {
        super.onResume()
        getScrabList()
    }

    private fun getScrabList() {
        val getScrabListResponseResponse: Call<GetMypageScrapList> = networkService.getMypageScrapResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(context!!)
        )
        getScrabListResponseResponse.enqueue(object : Callback<GetMypageScrapList> {
            override fun onFailure(call: Call<GetMypageScrapList>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetMypageScrapList>, response: Response<GetMypageScrapList>) {
                if (response.isSuccessful) {
                    data.clear()
                    data = response.body()!!.data!!
                    if (data.isNotEmpty()) {
                        rl_scarb_nocontents.visibility = View.GONE
                    } else {
                        rl_scarb_nocontents.visibility = View.VISIBLE
                    }
                    scrabContentsRecyclerViewAdapter = ScrabContentsRecyclerViewAdapter(context!!, data)
                    scrabContentsRecyclerViewAdapter.notifyDataSetChanged()
                    rv_scrab_fragment.adapter = scrabContentsRecyclerViewAdapter
                    rv_scrab_fragment.layoutManager = LinearLayoutManager(context)

                }
            }
        })

    }

}
