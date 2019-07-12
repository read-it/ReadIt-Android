package com.computer.inu.readit_appjam.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.readit_appjam.Adapter.ScrabContentsRecyclerViewAdapter
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.ContentsInfoX
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HiliteFragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var scrabContentsRecyclerViewAdapter: ScrabContentsRecyclerViewAdapter
    var data = ArrayList<ContentsInfoX>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hilite, container, false)
    }
    /*  private fun getScrabList() {
          val getHilightListResponseResponse: Call<GetRealHilightList> = networkService.getMyHilightResponse(
              "application/json",
              SharedPreferenceController.getAccessToken(context!!)
          )
          getHilightListResponseResponse.enqueue(object : Callback<GetRealHilightList> {
              override fun onFailure(call: Call<GetRealHilightList>, t: Throwable) {
              }

              override fun onResponse(call: Call<GetRealHilightList>, response: Response<GetRealHilightList>) {
                  if (response.isSuccessful) {
                      data.clear()
                      data = response.body()!!.data
                      if (data.isNotEmpty()) {
                          rl_scarb_nocontents.visibility = View.GONE
                      } else {
                          rl_scarb_nocontents.visibility = View.VISIBLE
                      }
                      MypageFragment.scrabnumber = response.body()!!.data!!.size
                      scrabContentsRecyclerViewAdapter = ScrabContentsRecyclerViewAdapter(context!!, data)
                      scrabContentsRecyclerViewAdapter.notifyDataSetChanged()
                      rv_scrab_fragment.adapter = scrabContentsRecyclerViewAdapter
                      rv_scrab_fragment.layoutManager = LinearLayoutManager(context)

                  }
              }
          })

      }*/
}
