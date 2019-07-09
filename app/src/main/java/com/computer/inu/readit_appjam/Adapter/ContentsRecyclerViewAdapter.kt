package com.computer.inu.readit_appjam.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.readit_appjam.Activity.MainActivity
import com.computer.inu.readit_appjam.Activity.MainHome_More_btn_Activity
import com.computer.inu.readit_appjam.Activity.Main_Home_Contents_Setting_Activity
import com.computer.inu.readit_appjam.Activity.WebViewActivity
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import com.computer.inu.readit_appjam.R
import java.util.regex.Pattern


class ContentsRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<ContentsOverviewData>) :
    RecyclerView.Adapter<ContentsRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(com.computer.inu.readit_appjam.R.layout.rv_item_contents, viewGroup, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //썸네일

        Glide.with(ctx)
            .load(dataList[position].thumbnail)
            .into(holder.thumbnail)

        holder.title.text = dataList[position].title

        holder.url.text = extractUrlParts(dataList[position].url) // 정규식 적용

        if (holder.url.text.equals("알수없음")) {
            holder.url.visibility = View.GONE
        }
        holder.num_highlight.text = dataList[position].highlight.toString() + "개"
        if (dataList[position].highlight.toString() == "0") {
            holder.rv_item_hilightnumber_box.visibility = View.GONE
        }
        holder.category.text = dataList[position].category

        holder.container.setOnClickListener {
            val intent = Intent(ctx, WebViewActivity::class.java)
            intent.putExtra("url", dataList[position].url)
            (ctx).startActivity(intent)
        }
        //   var dm = ctx.resources.displayMetrics
        //   var size = Math.round(24*dm.density)

        val lp = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val liControl1 = holder.rl_contents_allview.getLayoutParams() as RelativeLayout.LayoutParams


        if (dataList[position].read_judge == true) {
            liControl1.setMargins(60, 0, 0, 0)    // liControl1객체로 width와 hight등 파라미터를 다 설정가능
            holder.rl_contents_allview.setLayoutParams(liControl1)
            holder.iv_rv_read_flag.visibility = View.INVISIBLE
        } else if (dataList[position].read_judge == false) {
            liControl1.setMargins(10, 0, 0, 0)    // liControl1객체로 width와 hight등 파라미터를 다 설정가능
            holder.rl_contents_allview.setLayoutParams(liControl1)
        }

        holder.rv_item_more.setOnClickListener {
            val intent = Intent(ctx, Main_Home_Contents_Setting_Activity::class.java)
            ctx.startActivity(intent)

        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var container =
            itemView.findViewById(com.computer.inu.readit_appjam.R.id.rv_item_contents_overview_container) as RelativeLayout
        var thumbnail = itemView.findViewById(com.computer.inu.readit_appjam.R.id.img_thumbnail) as ImageView
        var title = itemView.findViewById(com.computer.inu.readit_appjam.R.id.txt_title) as TextView
        var url = itemView.findViewById(com.computer.inu.readit_appjam.R.id.txt_url) as TextView
        var num_highlight = itemView.findViewById(com.computer.inu.readit_appjam.R.id.txt_highlight) as TextView
        var category = itemView.findViewById(com.computer.inu.readit_appjam.R.id.txt_category) as TextView
        var iv_rv_read_flag = itemView.findViewById(com.computer.inu.readit_appjam.R.id.iv_rv_read_flag) as ImageView
        var rl_contents_allview = itemView.findViewById(R.id.rl_contents_allview) as RelativeLayout
        var rv_item_hilightnumber_box = itemView.findViewById(R.id.rv_item_hilightnumber_box) as LinearLayout
        var rv_item_more = itemView.findViewById(R.id.iv_rv_item_contents_ic_more) as ImageView
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