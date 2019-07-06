package com.computer.inu.readit_appjam.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import com.computer.inu.readit_appjam.R


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
        holder.url.text = dataList[position].url
        holder.num_highlight.text = dataList[position].highlight.toString() + "개"
        holder.category.text = dataList[position].category
        holder.container.setOnClickListener {
            // 웹뷰로 ㄱㄱㄱ
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
    }
}