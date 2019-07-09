package com.computer.inu.readit_appjam.Fragment


import android.arch.core.util.Function
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.computer.inu.readit_appjam.Activity.AllCategoryViewActivity
import com.computer.inu.readit_appjam.Activity.MainActivity
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.TabdataList
import com.computer.inu.readit_appjam.Activity.SearchActivity
import com.computer.inu.readit_appjam.Adapter.ContentsRecyclerViewAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
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
    private val MAXIMUM_SELECTION = 5
    private lateinit var selectionTracker: SelectionTracker<Long>


    private val itemDetailsLookup = object : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = rv_contents_all.findChildViewUnder(e.x, e.y)
            if (view != null) {
                val holder = rv_contents_all.getChildViewHolder(view)
                (holder as? ContentsRecyclerViewAdapter).apply {
                    return object : ItemDetails<Long>() {
                        override fun getSelectionKey() = holder.itemId
                        override fun getPosition() = holder.adapterPosition
                    }
                }
            }
            return null
        }
    }

    private val selectionPredicate = object : SelectionTracker.SelectionPredicate<Long>() {
        override fun canSelectMultiple(): Boolean {
            return true
        }

        override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
            return if (selectionTracker.selection.size() >= MAXIMUM_SELECTION && nextState) {
                toast("최대 선택 갯수 입니다.")
                false
            } else {
                true
            }
        }

        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
            return true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectionTracker.onSaveInstanceState(outState!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nv_home_nestedscrollview.post(Runnable { nv_home_nestedscrollview.scrollTo(0, 0) })

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
                "개발", true, 0
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "운영팀 들어오세요!",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false, 1
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "리딧",
                "http://magazine.channel.daum.net/yap/71",
                0,
                "디자인", true, 2
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "앱잼합시당",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false, 3
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", true, 4
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false, 5
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", true, 6
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false, 7
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", true, 8
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", false, 9
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "이태원 맛집 베스트",
                "http://magazine.channel.daum.net/yap/71",
                3,
                "디자인", true, 10
            )
        )
        val add = dataList.add(
            ContentsOverviewData(
                "https://avatars2.githubusercontent.com/u/41554049?s=460&v=4",
                "홍준표의 브랜딩",
                "http://www.naver.com",
                3,
                "디자인", false, 11
            )
        )

        contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, dataList)
        contentsRecyclerViewAdapter.apply {
            selectionFun = Function { key ->
                selectionTracker.isSelected(key)
            }
        }
        rv_contents_all.adapter = contentsRecyclerViewAdapter
        rv_contents_all.layoutManager = LinearLayoutManager(context)
        selectionTracker = SelectionTracker.Builder(
            "selection-demo",
            rv_contents_all,
            StableIdKeyProvider(rv_contents_all),
            itemDetailsLookup,
            StorageStrategy.createLongStorage()
        )
            .withSelectionPredicate(selectionPredicate)
            .build()

        /*  selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
              override fun onSelectionChanged() {
                  title = if (selectionTracker.hasSelection()) {
                      "${selectionTracker.selection.size()} / $MAXIMUM_SELECTION selected"
                  } else {
                      "recyclerview-selection"
                  }
              }
          })*/

        iv_main_category_morebutton.setOnClickListener {
            val intent = Intent(ctx, AllCategoryViewActivity::class.java)
            ctx.startActivity(intent)
            (ctx as MainActivity).overridePendingTransition(
                R.anim.sliding_up,
                R.anim.stay
            )
        }

        btn_search.setOnClickListener {
            startActivity<SearchActivity>()
        }

        selectionTracker.onRestoreInstanceState(savedInstanceState)
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
