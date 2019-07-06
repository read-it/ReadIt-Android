package com.computer.inu.readit_appjam.Fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.computer.inu.readit_appjam.Activity.AllCategoryViewActivity
import com.computer.inu.readit_appjam.Activity.MainActivity
import com.computer.inu.readit_appjam.Adapter.CategoryRecyclerViewAdapter
import com.computer.inu.readit_appjam.Adapter.ContentsRecyclerViewAdapter
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import com.computer.inu.readit_appjam.Data.HomeCategoryTab
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.ctx


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
    lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    companion object {
        var TabdataList: ArrayList<HomeCategoryTab> = ArrayList()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.computer.inu.readit_appjam.R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var dataList: ArrayList<ContentsOverviewData> = ArrayList()


        TabdataList.add(
            HomeCategoryTab("전체")
        )
        TabdataList.add(
            HomeCategoryTab("개발")
        )
        TabdataList.add(
            HomeCategoryTab("브랜딩")
        )
        TabdataList.add(
            HomeCategoryTab("스타트업")
        )
        TabdataList.add(
            HomeCategoryTab("맛집")
        )
        TabdataList.add(
            HomeCategoryTab("페북")
        )
        TabdataList.add(
            HomeCategoryTab("인스타")
        )
        TabdataList.add(
            HomeCategoryTab("유투브")
        )


        for (i in 0..TabdataList.size - 1) {
            tl_home_categorytab.addTab(tl_home_categorytab.newTab().setText(TabdataList[i].TabName))
        }


        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", true
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", true
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", true
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", true
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", true
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", false
            )
        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
                3,
                "디자인", true
            )

        )
        dataList.add(
            ContentsOverviewData(
                "https://avatars1.githubusercontent.com/u/41554049?s=400&u=9de365b84e00e2f8faa17c070f286930c1c7b21e&v=4",
                "홍준표의 브랜딩",
                "brunch.com",
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

}
