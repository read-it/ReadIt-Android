package com.computer.inu.readit_appjam.Adapter

import android.arch.core.util.Function
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.selection.SelectionTracker
import com.bumptech.glide.Glide
import com.computer.inu.readit_appjam.Activity.MainActivity.Companion.SettingFlag
import com.computer.inu.readit_appjam.Activity.Main_Home_Contents_Setting_Activity
import com.computer.inu.readit_appjam.Activity.WebViewActivity
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.PutReadContents
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class ContentsRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<ContentsOverviewData>) :
    RecyclerView.Adapter<ContentsRecyclerViewAdapter.Holder>() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    private val MAXIMUM_SELECTION = 5
    private lateinit var selectionTracker: SelectionTracker<Long>
    lateinit var selectionFun: Function<Long, Boolean>

    init {
        setHasStableIds(true) //하나의 Item을 식별하기 위한 고유값(ID)으로 설정하면됩니다. Key타입을 결정하였다면, Adapter에게 Id를 이용해 Item을 식별하겠다는 설정을 하도록합니다.
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {

        val view: View =
            LayoutInflater.from(ctx).inflate(com.computer.inu.readit_appjam.R.layout.rv_item_contents, viewGroup, false)



        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindTo(position, selectionFun.apply(getItemId(position)))
        if (dataList[position].thumbnail.isNullOrEmpty()) {
            holder.thumbnail.visibility = View.GONE
        } else {
            //썸네일
            Glide.with(ctx)
                .load(dataList[position].thumbnail)
                .into(holder.thumbnail)
        }

        if (dataList[position].thumbnail.isNullOrEmpty()) {
            holder.thumbnail.visibility = View.GONE
        }
        val drawable = ctx.getDrawable(com.computer.inu.readit_appjam.R.drawable.imagerounding) as GradientDrawable
        holder.thumbnail.setBackground(drawable)
        holder.thumbnail.setClipToOutline(true)

        if (dataList[position].title.isNullOrEmpty()) {
            holder.title.text = "제목없음"
        } else {
            holder.title.text = dataList[position].title

        }

        holder.url.text = dataList[position].site_url // 정규식 적용
        if (dataList[position].fixed_date.isNullOrEmpty()) {
            holder.ic_clip.visibility = View.GONE
        } else {
            holder.ic_clip.visibility = View.VISIBLE
        }

        holder.num_highlight.text = dataList[position].highlight_cnt.toString() + "개"
        if (dataList[position].highlight_cnt.toString() == "0") {
            holder.rv_item_hilightnumber_box.visibility = View.GONE
        }
        holder.category.text = dataList[position].category_name
        if (holder.category.text == "전체") {
            holder.category.visibility = View.GONE
        }
        holder.txt_date.text = dataList[position].after_create_date

        holder.container.setOnClickListener {
            ContentsReadPost(dataList[position].contents_idx)  //읽는 통신
            val intent = Intent(ctx, WebViewActivity::class.java)
            intent.putExtra("url", dataList[position].contents_url)
            ctx.startActivity(intent)
        }

        holder.rv_item_more.setOnClickListener {
            val intent = Intent(ctx, Main_Home_Contents_Setting_Activity::class.java)
            intent.putExtra("fixed_date", dataList[position].fixed_date) //상단고정 플래그
            intent.putExtra("scrap_flag", dataList[position].scrap_flag) // 스크랩 플래그
            intent.putExtra("contents_idx", dataList[position].contents_idx) // 콘텐츠 아이디
            SettingFlag = 1

            ctx.startActivity(intent)
        }
        //   var dm = ctx.resources.displayMetrics
        //   var size = Math.round(24*dm.density)

        val lp = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val liControl1 = holder.rl_contents_allview.getLayoutParams() as RelativeLayout.LayoutParams


        if (dataList[position].read_flag == 1) {
            liControl1.setMargins(60, 0, 0, 0)    // liControl1객체로 width와 hight등 파라미터를 다 설정가능
            holder.rl_contents_allview.setLayoutParams(liControl1)
            holder.iv_rv_read_flag.visibility = View.INVISIBLE
        } else if (dataList[position].read_flag == 0) {
            liControl1.setMargins(10, 0, 0, 0)    // liControl1객체로 width와 hight등 파라미터를 다 설정가능
            holder.rl_contents_allview.setLayoutParams(liControl1)
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(position: Int, isSelected: Boolean) {
            container.setBackgroundColor(
                if (isSelected) {
                    itemView.isActivated = true
                    Color.parseColor("#dedede")
                } else {
                    itemView.isActivated = false
                    Color.parseColor("#ffffff")
                }
            )
            /*      thumbnail.setColorFilter(
                      if (isSelected) {
                          itemView.isActivated = true
                          Color.parseColor("#88000000")
                      } else {
                          itemView.isActivated = false
                          Color.parseColor("#00000000")
                      })*/

            rl_contents_entire_view.setBackgroundColor(
                if (isSelected) {
                    itemView.isActivated = true
                    Color.parseColor("#dedede")
                } else {
                    itemView.isActivated = false
                    Color.parseColor("#ffffff")
                }
            )
            //  numberView.text = "# $position"
            //  imageView.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY)
        }
        var container =
            itemView.findViewById(com.computer.inu.readit_appjam.R.id.rv_item_contents_overview_container) as RelativeLayout
        var thumbnail = itemView.findViewById(com.computer.inu.readit_appjam.R.id.img_thumbnail) as ImageView
        var title = itemView.findViewById(com.computer.inu.readit_appjam.R.id.txt_title) as TextView
        var url = itemView.findViewById(com.computer.inu.readit_appjam.R.id.txt_url) as TextView
        var num_highlight = itemView.findViewById(com.computer.inu.readit_appjam.R.id.txt_highlight) as TextView
        var category = itemView.findViewById(com.computer.inu.readit_appjam.R.id.txt_category) as TextView
        var iv_rv_read_flag = itemView.findViewById(com.computer.inu.readit_appjam.R.id.iv_rv_read_flag) as ImageView
        var rl_contents_allview =
            itemView.findViewById(com.computer.inu.readit_appjam.R.id.rl_contents_allview) as RelativeLayout
        var rv_item_hilightnumber_box =
            itemView.findViewById(com.computer.inu.readit_appjam.R.id.rv_item_hilightnumber_box) as LinearLayout
        var rl_contents_entire_view =
            itemView.findViewById(com.computer.inu.readit_appjam.R.id.rl_contents_entire_view) as RelativeLayout
        var rv_item_more =
            itemView.findViewById(com.computer.inu.readit_appjam.R.id.iv_rv_item_contents_ic_more) as ImageView
        var ic_clip = itemView.findViewById(com.computer.inu.readit_appjam.R.id.ic_clip) as ImageView
        var txt_date = itemView.findViewById(com.computer.inu.readit_appjam.R.id.txt_date) as TextView
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

    private fun ContentsReadPost(idx: Int) {
        val postReadContent: Call<PutReadContents> =
            networkService.putReadContentsResponse(
                "application/json",
                SharedPreferenceController.getAccessToken(ctx),
                idx
            )
        postReadContent.enqueue(object : Callback<PutReadContents> {
            override fun onFailure(call: Call<PutReadContents>, t: Throwable) {
            }

            override fun onResponse(call: Call<PutReadContents>, response: Response<PutReadContents>) {
                if (response.isSuccessful) {

                }
            }
        })

    }
}