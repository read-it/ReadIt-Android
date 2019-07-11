package com.computer.inu.readit_appjam.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.computer.inu.readit_appjam.Activity.Main_Home_Contents_Setting_Activity
import com.computer.inu.readit_appjam.Activity.SearchResultActivity
import com.computer.inu.readit_appjam.Activity.WebViewActivity
import com.computer.inu.readit_appjam.Data.ContentsOverviewData
import com.computer.inu.readit_appjam.Data.ContentsSearchData
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_search_result.*
import java.util.regex.Pattern

class SearchResultsRVAdapter(var ctx: Context, var dataList: ArrayList<ContentsSearchData>) :
    RecyclerView.Adapter<SearchResultsRVAdapter.Holder>() {
    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): SearchResultsRVAdapter.Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_contents, viewgroup)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: SearchResultsRVAdapter.Holder, position: Int) {
        holder.ic_clip.visibility = View.GONE // 클립 아이콘 제거
        holder.datebox.visibility = View.GONE // 달력 아이콘 제거

        if (dataList[position].thumbnail.isNullOrEmpty()) {
            holder.thumbnail.visibility = View.GONE
        } else {
            Glide.with(ctx)
                .load(dataList[position].thumbnail)
                .into(holder.thumbnail)
        }

        if (dataList[position].title.isNullOrEmpty()) {
            holder.title.text = "제목 없음"
        } else {
            holder.title.text = dataList[position].title
        }

        holder.url.text = dataList[position].site_url

        // 정규식 적용
        if (holder.url.text.equals("알수없음")) {
            holder.url.visibility = View.GONE
        }

        holder.num_highlight.text = dataList[position].highlight_cnt.toString() + "개"
        holder.category.text = dataList[position].category_name
        if (holder.category.text == "전체") {
            holder.category.visibility = View.GONE
        }

        holder.container.setOnClickListener {
            val intent = Intent(ctx, WebViewActivity::class.java)
            intent.putExtra("url", dataList[position].contents_url)
            (ctx).startActivity(intent)

            // 최근 검색어 DB 저장
            val keyword = (ctx as SearchResultActivity).edt_searching.text.toString()
            (ctx as SearchResultActivity).addData(keyword)
        }

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

        holder.rv_item_more.setOnClickListener {
            val intent = Intent(ctx, Main_Home_Contents_Setting_Activity::class.java)
            intent.putExtra("fixed_date", dataList[position].fixed_date) // 상단고정 플래그
            intent.putExtra("scrap_flag", 0) // 스크랩 플래그
            intent.putExtra("contents_idx", dataList[position].contents_idx) // 콘텐츠 아이디
            ctx.startActivity(intent)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var container =
            itemView.findViewById(R.id.rv_item_contents_overview_container) as RelativeLayout
        var thumbnail = itemView.findViewById(R.id.img_thumbnail) as ImageView
        var title = itemView.findViewById(R.id.txt_title) as TextView
        var url = itemView.findViewById(R.id.txt_url) as TextView
        var num_highlight = itemView.findViewById(R.id.txt_highlight) as TextView
        var category = itemView.findViewById(R.id.txt_category) as TextView
        var iv_rv_read_flag = itemView.findViewById(R.id.iv_rv_read_flag) as ImageView
        var rl_contents_allview = itemView.findViewById(R.id.rl_contents_allview) as RelativeLayout
        var ic_clip = itemView.findViewById(R.id.ic_clip) as ImageView
        var datebox = itemView.findViewById(R.id.rv_item_date_box) as LinearLayout
        var rl_contents_entire_view =
            itemView.findViewById(R.id.rl_contents_entire_view) as RelativeLayout
        var rv_item_more =
            itemView.findViewById(R.id.iv_rv_item_contents_ic_more) as ImageView
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