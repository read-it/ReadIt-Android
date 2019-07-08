package com.computer.inu.readit_appjam.Fragment


import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.readit_appjam.Activity.AllCategoryViewActivity
import com.computer.inu.readit_appjam.Activity.MainActivity
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.TabdataList
import com.computer.inu.readit_appjam.Adapter.ContentsRecyclerViewAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.ctx
import java.util.regex.Pattern


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
        return inflater.inflate(com.computer.inu.readit_appjam.R.layout.fragment_home, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rl_home_linkcopy_box.visibility = View.GONE

        //클립보드매니져 테스트
        var clipboard = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

        if (SharedPreferenceController.getClip(context!!).isNotEmpty()) {
            if (SharedPreferenceController.getClip(context!!).toString() == clipboard!!.text.toString()) {
                //값이 같음
            } else {
                SharedPreferenceController.clearClip(context!!)
                SharedPreferenceController.setClip(context!!, clipboard!!.text.toString())
                rl_home_linkcopy_box.visibility = View.VISIBLE
                tv_home_copy_url.text = extractUrlParts(clipboard!!.text.toString())
                Handler().postDelayed(Runnable {
                    rl_home_linkcopy_box.visibility = View.GONE
                }, 4000)//
            }
        }

        if (clipboard!!.hasPrimaryClip() && SharedPreferenceController.getClip(context!!).isEmpty()) {
            SharedPreferenceController.setClip(context!!, clipboard!!.text.toString())
            rl_home_linkcopy_box.visibility = View.VISIBLE
            tv_home_copy_url.text = extractUrlParts(clipboard!!.text.toString())
            Handler().postDelayed(Runnable {
                rl_home_linkcopy_box.visibility = View.GONE
            }, 8000)//

        }

        tv_home_confirm.setOnClickListener {
            rl_home_linkcopy_box.visibility = View.GONE
            // 링크 저장 통신해야함

        }


        var dataList: ArrayList<ContentsOverviewData> = ArrayList()


        for (i in 0..TabdataList.size - 1) {
            tl_home_categorytab.addTab(tl_home_categorytab.newTab().setText(TabdataList[i].TabName))
        }


        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "나는 누구인가",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "개발", true
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "운영팀 들어오세요!",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "리딧",
                "http://magazine.channel.daum.net/yap/71",
                0,
                "디자인", true
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "앱잼합시당",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", true
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", true
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", true
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", true
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "홍준표의 브랜딩",
                "http://www.naver.com",
                3,
                "디자인", false
            )
        )
        contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, dataList)
        rv_contents_all.adapter = contentsRecyclerViewAdapter
        rv_contents_all.layoutManager = LinearLayoutManager(context)

        /*     categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(context!!, TabdataList)
             rv_home_category_tab.adapter = categoryRecyclerViewAdapter
             val layoutManager = LinearLayoutManager(context)
             layoutManager.orientation = LinearLayoutManager.HORIZONTAL
             rv_home_category_tab.setLayoutManager(layoutManager)*/

        iv_main_category_morebutton.setOnClickListener {
            val intent = Intent(ctx, AllCategoryViewActivity::class.java)
            ctx.startActivity(intent)
            (ctx as MainActivity).overridePendingTransition(
                com.computer.inu.readit_appjam.R.anim.sliding_up,
                com.computer.inu.readit_appjam.R.anim.stay
            )
        }
    }

    internal fun extractUrlParts(testurl: String): String {
        val urlPattern =
            Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$")
        val mc = urlPattern.matcher(testurl)
        if (mc.matches()) {
            return mc.group(2).toString()
        } else {
            return "알수없음"
        }
        return "알수없음"
    }
}
