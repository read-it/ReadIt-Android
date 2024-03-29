package com.computer.inu.readit_appjam.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.computer.inu.readit_appjam.Activity.SearchActivity
import com.computer.inu.readit_appjam.Data.LatestSearchKeyword
import com.computer.inu.readit_appjam.R

class LatestSearchKeywordRVAdapter(var ctx: Context, var dataList: ArrayList<LatestSearchKeyword>) :
    RecyclerView.Adapter<LatestSearchKeywordRVAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_latest_search_keywords, viewGroup, false)

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.searchKeyword.text = dataList[position].keyword
        holder.deleteKeyword.setOnClickListener {
            // DB 삭제
            (ctx as SearchActivity).deleteData((ctx as SearchActivity).dbHandler, holder.searchKeyword.text.toString())
            // arrayList 갱신
            dataList.removeAt(position)
            // 뷰 갱신
            notifyDataSetChanged()
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var searchKeyword = itemView.findViewById(R.id.tv_keyword) as TextView
        var deleteKeyword = itemView.findViewById(R.id.img_delete) as ImageView
    }
}