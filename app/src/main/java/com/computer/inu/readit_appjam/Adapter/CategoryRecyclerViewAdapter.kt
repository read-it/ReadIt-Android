package com.computer.inu.readit_appjam.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.computer.inu.readit_appjam.Data.HomeCategoryTab
import com.computer.inu.readit_appjam.R
import org.jetbrains.anko.toast

class CategoryRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<HomeCategoryTab>) :
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.Holder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_category_tab_name, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tabname.text = dataList[position].TabName
        holder.tabname.setOnClickListener {
            //ctx.toast(dataList[position].TabName.toString())
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tabname = itemView.findViewById(R.id.rv_item_tab_name) as TextView

    }
}