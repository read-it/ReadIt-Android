package com.computer.inu.readit_appjam.Fragment


import android.app.Activity
import android.arch.core.util.Function
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.bumptech.glide.Glide
import com.computer.inu.readit_appjam.Activity.*
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.AllCategoryFlag
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.SettingFlag
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.TABCATEGORYFLAG
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.TabdataList
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.idx
import com.computer.inu.readit_appjam.Adapter.ContentsRecyclerViewAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import com.computer.inu.readit_appjam.Data.HomeCategoryTab
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.GetMainStorageResponse
import com.computer.inu.readit_appjam.Network.Get.GetSortCategoryResponse
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Post.PostContentsAddResponse
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var contentsRecyclerViewAdapter: ContentsRecyclerViewAdapter
    lateinit var Tab: TabLayout.Tab
    //private val MAXIMUM_SELECTION = 5
    //private lateinit var selectionTracker: SelectionTracker<Long>
    var data = ArrayList<ContentsOverviewData>()
    val REQUEST_CODE_SUB_ACTIVITY = 7777
    val REQUEST_CODE_ALL_CATEGORY_ACTIVITY = 7777
    companion object {
        var Tab_positon = 0
        var sort: Int = 1
    }

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    /*private val itemDetailsLookup = object : ItemDetailsLookup<Long>() {
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
                // toast("최대 선택 갯수 입니다.")
                false
            } else {
                true
            }
        }

        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
            return true
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //loading progress bar
        //refresh bar
        getMainStorage()
        swipeRefreshLo.setOnRefreshListener(this)
        swipeRefreshLo.setColorSchemeColors(resources.getColor(R.color.tomato))
        tl_home_categorytab.tabRippleColor = null
        nv_home_nestedscrollview.post(Runnable { nv_home_nestedscrollview.scrollTo(0, 0) })


        //Tab!!.select()
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
                tv_home_copy_url.text = clipboard!!.text.toString()
                Handler().postDelayed(Runnable {
                    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.up_to_down)
                    rl_home_linkcopy_box.visibility = View.GONE
                    rl_home_linkcopy_box.startAnimation(animation)
                }, 4000)//
            }
        }

        if (clipboard!!.hasPrimaryClip() && SharedPreferenceController.getClip(context!!).isEmpty()) {
            SharedPreferenceController.setClip(context!!, clipboard!!.text.toString())
            rl_home_linkcopy_box.visibility = View.VISIBLE
            tv_home_copy_url.text = extractUrlParts(clipboard!!.text.toString())
            Handler().postDelayed(Runnable {
                rl_home_linkcopy_box.visibility = View.GONE
            }, 4000)//

        }
        tl_home_categorytab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                Handler().postDelayed(Runnable {
                    //loading progress bar
                    data.clear()
                    contentsRecyclerViewAdapter.notifyDataSetChanged()
                    loading_progress.visibility = View.VISIBLE
                    Tab_positon = tab.position
                    idx = TabdataList[tab.position].category_idx!!
                    getSortCategory(TabdataList[tab.position].category_idx!!, sort)
                    Tab = tl_home_categorytab.getTabAt(Tab_positon)!!

                }, 200)//

            }
        })

        tv_home_confirm.setOnClickListener {
            rl_home_linkcopy_box.visibility = View.GONE
            AddContentsPost(clipboard!!.text.toString())// 링크 저장 통신해야함
            Handler().postDelayed(Runnable {
                getSortCategory(idx, sort)
            }, 500)

        }



        iv_home_list_sorting.setOnClickListener {
            val intent = Intent(ctx, MainHome_More_btn_Activity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SUB_ACTIVITY)
        }



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
            startActivityForResult(intent, REQUEST_CODE_ALL_CATEGORY_ACTIVITY)
            /*(ctx as MainActivity).overridePendingTransition(
                R.anim.sliding_up,
                R.anim.stay
            )*/
        }

        btn_search.setOnClickListener {
            startActivity<SearchActivity>()
            //tl_home_categorytab.getTabAt(1)!!.select()

        }


    }

    override fun onResume() {
        super.onResume()
        if (SettingFlag == 1) {
            //tl_home_categorytab.getTabAt(data!!.getIntExtra("index",0))!!.select()
            getSortCategory(idx, sort)
            SettingFlag = 0

        }
        if(TABCATEGORYFLAG == 1){

                getMainTabStorage()

                TABCATEGORYFLAG=0

        }
        var clipboard = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        if (SharedPreferenceController.getClip(context!!).isNotEmpty()) {
            if (SharedPreferenceController.getClip(context!!).toString() == clipboard!!.text.toString()) {
                //값이 같음
            } else {
                SharedPreferenceController.clearClip(context!!)
                SharedPreferenceController.setClip(context!!, clipboard!!.text.toString())
                rl_home_linkcopy_box.visibility = View.VISIBLE
                tv_home_copy_url.text = clipboard!!.text.toString()
                Handler().postDelayed(Runnable {
                    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.up_to_down)
                    rl_home_linkcopy_box.visibility = View.GONE
                    rl_home_linkcopy_box.startAnimation(animation)
                }, 4000)//
            }
        }
       // getMainTabStorage()
        if(AllCategoryFlag==1){
            Handler().postDelayed(Runnable {
                val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.up_to_down)
                rl_home_linkcopy_box.visibility = View.GONE
                rl_home_linkcopy_box.startAnimation(animation)
            }, 4000)//
            getSortCategory(idx, sort)
            tl_home_categorytab.getTabAt(idx)?.select()
            AllCategoryFlag=0
        }

        tl_home_categorytab.getTabAt(idx)?.select()
        // getSortCategory(TabdataList[tab_positon].category_idx!!, sort)
        // getMainStorage()
        //getSortCategory(idx, sort)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SUB_ACTIVITY) { //정렬 할때
                getSortCategory(idx, sort)
        }
        if (requestCode == REQUEST_CODE_ALL_CATEGORY_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                tl_home_categorytab.getTabAt(data!!.getIntExtra("index", 0))!!.select()
                getSortCategory(idx, sort)
                AllCategoryFlag=1
            }    //all category
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

    private fun getMainStorage() {
        val getMainstorageResponseResponse: Call<GetMainStorageResponse> = networkService.getMainStorageResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(context!!)
        )
        getMainstorageResponseResponse.enqueue(object : Callback<GetMainStorageResponse> {
            override fun onFailure(call: Call<GetMainStorageResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetMainStorageResponse>, response: Response<GetMainStorageResponse>) {
                if (response.isSuccessful) {
                    Glide.with(this@HomeFragment)
                        .load(response.body()!!.data!!.profile_img)
                        .into(iv_friend_mypicture)
                    tv_home_myname.text = response.body()!!.data!!.nickname
                    TabdataList.clear()
                    for (i in 0..response.body()!!.data!!.category_list!!.size - 1) {
                        TabdataList.add(
                            HomeCategoryTab(
                                response.body()!!.data!!.category_list?.get(i)?.category_name,
                                response.body()!!.data!!.category_list?.get(i)?.category_idx
                            )
                        )
                    }
                    SharedPreferenceController.setCategoryIdx(
                        ctx,
                        response.body()!!.data!!.category_list!![0].category_idx!!
                    )
                    tl_home_categorytab.removeAllTabs()
                    for (i in 0..TabdataList.size - 1) {
                        tl_home_categorytab.addTab(tl_home_categorytab.newTab().setText(TabdataList[i].TabName))
                    }

                    tv_home_contents_number.text = response.body()!!.data!!.total_count.toString() + "개"
                    tv_home_unread_count.text = response.body()!!.data!!.unread_count.toString() + "개"
                    data.clear()
                    data = response.body()!!.data!!.contents_list!!
                    contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, data)
                    contentsRecyclerViewAdapter.notifyDataSetChanged()
                             rv_contents_all.adapter = contentsRecyclerViewAdapter
                             rv_contents_all.layoutManager = LinearLayoutManager(context)
                             /*contentsRecyclerViewAdapter.apply {
                                 selectionFun = Function { key ->
                                     selectionTracker.isSelected(key)
                                 }
                             }
                             selectionTracker = SelectionTracker.Builder(
                                 "selection-demo",
                                 rv_contents_all,
                                 StableIdKeyProvider(rv_contents_all),
                                 itemDetailsLookup,
                                 StorageStrategy.createLongStorage()
                             )
                                 .withSelectionPredicate(selectionPredicate)
                                 .build()
         */
                }
            }
        })

    }

    private fun getMainTabStorage() {
        val getMainstorageResponseResponse: Call<GetMainStorageResponse> = networkService.getMainStorageResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(context!!)
        )
        getMainstorageResponseResponse.enqueue(object : Callback<GetMainStorageResponse> {
            override fun onFailure(call: Call<GetMainStorageResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetMainStorageResponse>, response: Response<GetMainStorageResponse>) {
                if (response.isSuccessful) {
                    Glide.with(this@HomeFragment)
                        .load(response.body()!!.data!!.profile_img)
                        .into(iv_friend_mypicture)
                    tv_home_myname.text = response.body()!!.data!!.nickname
                    TabdataList.clear()
                    for (i in 0..response.body()!!.data!!.category_list!!.size - 1) {
                        TabdataList.add(
                            HomeCategoryTab(
                                response.body()!!.data!!.category_list?.get(i)?.category_name,
                                response.body()!!.data!!.category_list?.get(i)?.category_idx
                            )
                        )
                    }
                    SharedPreferenceController.setCategoryIdx(
                        ctx,
                        response.body()!!.data!!.category_list!![0].category_idx!!
                    )
                    tl_home_categorytab.removeAllTabs()
                    for (i in 0..TabdataList.size - 1) {
                        tl_home_categorytab.addTab(tl_home_categorytab.newTab().setText(TabdataList[i].TabName))
                    }

                    /*         rv_contents_all.adapter = contentsRecyclerViewAdapter
                             rv_contents_all.layoutManager = LinearLayoutManager(context)
                             contentsRecyclerViewAdapter.apply {
                                 selectionFun = Function { key ->
                                     selectionTracker.isSelected(key)
                                 }
                             }
                             selectionTracker = SelectionTracker.Builder(
                                 "selection-demo",
                                 rv_contents_all,
                                 StableIdKeyProvider(rv_contents_all),
                                 itemDetailsLookup,
                                 StorageStrategy.createLongStorage()
                             )
                                 .withSelectionPredicate(selectionPredicate)
                                 .build()
         */
                }
            }
        })

    }
    private fun getOnlyMainContent() {
        val getMainstorageResponseResponse: Call<GetMainStorageResponse> = networkService.getMainStorageResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(context!!)
        )
        getMainstorageResponseResponse.enqueue(object : Callback<GetMainStorageResponse> {
            override fun onFailure(call: Call<GetMainStorageResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetMainStorageResponse>, response: Response<GetMainStorageResponse>) {
                if (response.isSuccessful) {
                    contentsRecyclerViewAdapter.dataList.clear()
                    contentsRecyclerViewAdapter.dataList.addAll(response!!.body()!!.data!!.contents_list!!)
                    contentsRecyclerViewAdapter.notifyDataSetChanged()
                    /*    rv_contents_all.adapter = contentsRecyclerViewAdapter
                        rv_contents_all.layoutManager = LinearLayoutManager(context)
                        contentsRecyclerViewAdapter.apply {
                            selectionFun = Function { key ->
                                selectionTracker.isSelected(key)
                            }
                        }
                        selectionTracker = SelectionTracker.Builder(
                            "selection-demo",
                            rv_contents_all,
                            StableIdKeyProvider(rv_contents_all),
                            itemDetailsLookup,
                            StorageStrategy.createLongStorage()
                        )
                            .withSelectionPredicate(selectionPredicate)
                            .build()
    */
                }
            }
        })

    }

    private fun getSortCategory(category_idx: Int, sort: Int) {

        val getSortCategoryResponseResponse: Call<GetSortCategoryResponse> = networkService.getSortCategoryResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(context!!), category_idx, sort
        )
        getSortCategoryResponseResponse.enqueue(object : Callback<GetSortCategoryResponse> {
            override fun onFailure(call: Call<GetSortCategoryResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetSortCategoryResponse>, response: Response<GetSortCategoryResponse>) {
                if (response.isSuccessful) {

                    tv_home_contents_number.text = response.body()!!.data!!.total_count.toString() + "개"
                    tv_home_unread_count.text = response.body()!!.data!!.unread_count.toString() + "개"
                    loading_progress.visibility = View.GONE
                    data = response.body()!!.data!!.contents_list!!

                    contentsRecyclerViewAdapter.notifyDataSetChanged()
                    contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, data)
                    contentsRecyclerViewAdapter.notifyDataSetChanged()
                    rv_contents_all.adapter = contentsRecyclerViewAdapter
                    rv_contents_all.layoutManager = LinearLayoutManager(context)
                    /*contentsRecyclerViewAdapter.apply {
                        selectionFun = Function { key ->
                            selectionTracker.isSelected(key)
                        }
                    }

                    selectionTracker = SelectionTracker.Builder(
                        "selection-demo",
                        rv_contents_all,
                        StableIdKeyProvider(rv_contents_all),
                        itemDetailsLookup,
                        StorageStrategy.createLongStorage()
                    )
                        .withSelectionPredicate(selectionPredicate)
                        .build()*/

                }
            }
        })

    }

    private fun getOnlyChangeSortCategory(category_idx: Int, sort: Int) {
        val getSortCategoryResponseResponse: Call<GetSortCategoryResponse> = networkService.getSortCategoryResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(context!!), category_idx, sort
        )
        getSortCategoryResponseResponse.enqueue(object : Callback<GetSortCategoryResponse> {
            override fun onFailure(call: Call<GetSortCategoryResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetSortCategoryResponse>, response: Response<GetSortCategoryResponse>) {
                if (response.isSuccessful) {
                    tv_home_contents_number.text = response.body()!!.data!!.total_count.toString() + "개"
                    tv_home_unread_count.text = response.body()!!.data!!.unread_count.toString() + "개"
                    data.clear()
                    data = response.body()!!.data!!.contents_list!!

                    contentsRecyclerViewAdapter.notifyDataSetChanged()
                    contentsRecyclerViewAdapter = ContentsRecyclerViewAdapter(context!!, data)
                    contentsRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
        })

    }

    private fun AddContentsPost(url: String) {
        var jsonObject = JSONObject()
        jsonObject.put("contents_url", url)
        jsonObject.put("category_idx", SharedPreferenceController.getCategoryIdx(ctx))

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postContentAddResponse: Call<PostContentsAddResponse> =
            networkService.postContentsAddResponse(
                "application/json",
                SharedPreferenceController.getAccessToken(context!!),
                gsonObject
            )
        postContentAddResponse.enqueue(object : Callback<PostContentsAddResponse> {
            override fun onFailure(call: Call<PostContentsAddResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PostContentsAddResponse>, response: Response<PostContentsAddResponse>) {
                if (response.isSuccessful) {
                    val message = response.body()!!.message!!
                }
            }
        })

    }

    override fun onRefresh() {
        //새로고침 코드
        getSortCategory(idx, sort)
        swipeRefreshLo.isRefreshing = false
    }
}
