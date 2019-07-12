package com.computer.inu.readit_appjam.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.computer.inu.readit_appjam.Activity.CategorySettingEditActivity
import android.widget.*
import com.computer.inu.readit_appjam.Activity.SettingCategoryActivity
import com.computer.inu.readit_appjam.Data.CategorySettingData
import com.computer.inu.readit_appjam.Interface.CategoryItemTouchHelperCallback
import com.computer.inu.readit_appjam.R
import org.jetbrains.anko.startActivity
import java.util.*

class CategoryMoveAdapter(
    var ctx: Context,
    var dataList: ArrayList<CategorySettingData>
) :
    RecyclerView.Adapter<CategoryMoveAdapter.Holder>(){

    //어댑터 값 인텐트로 넘기주기 위한 콜백


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_category_setting_contents, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.edit_btn.visibility = View.GONE
        holder.sort_btn.visibility = View.GONE
        holder.checkbox.isChecked = dataList[position].checkbox
        holder.category_name.text = dataList[position].category_name

        holder.checkbox_btn.setOnClickListener {
            if (holder.checkbox.isChecked == false) {
                var flag = true
                for (idx in 0..dataList.size - 1) {
                    if (dataList[idx].checkbox == true) {
                        dataList[idx].checkbox = false
                        notifyItemChanged(idx)
                    }
                }
                holder.checkbox.isChecked = true

                Log.e("pos", position.toString())
                dataList[position].checkbox = true
            }
        }


    }



    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkbox = itemView.findViewById(R.id.category_cb) as RadioButton
        var category_name = itemView.findViewById(R.id.category_setting_name) as TextView
        var checkbox_btn = itemView.findViewById(R.id.rl_btn) as RelativeLayout
        var edit_btn = itemView.findViewById(R.id.category_setting_edit) as ImageView
        var sort_btn = itemView.findViewById(R.id.category_setting_sort) as ImageView
    }
}