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

class CategorySettingRvAdapter(
    var ctx: Context,
    var dataList: ArrayList<CategorySettingData>,
    startDragListener: OnStartDragListener
) :
    RecyclerView.Adapter<CategorySettingRvAdapter.Holder>(), CategoryItemTouchHelperCallback.OnItemMoveListener {

    //어댑터 값 인텐트로 넘기주기 위한 콜백
    private var mCallback: CallbackInterface = ctx as CallbackInterface
    var init_flag = false

    interface CallbackInterface {
        fun onHandelSelection(position: Int, name: String)
    }

    //카테고리 드래그 콜백
    interface OnStartDragListener {
        fun onStartDrag(holder: CategorySettingRvAdapter.Holder)
    }

    private var mStartDragListener: OnStartDragListener = startDragListener


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_category_setting_contents, viewGroup, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.checkbox.isChecked = dataList[position].checkbox
        holder.category_name.text = dataList[position].category_name

         holder.checkbox_btn.setOnClickListener {
             if (holder.checkbox.isChecked == false) {
                 var flag = true
                     for (idx in 0..dataList.size-1) {
                         if (dataList[idx].checkbox == true) {
                             Log.d("pos", idx.toString())
                             Log.d("in", "is in?")
                             dataList[idx].checkbox = false
                             notifyItemChanged(idx)
                         }
                     }
                 holder.checkbox.isChecked = true

                 Log.e("pos", position.toString())
                 Toast.makeText(ctx, dataList[position].category_name + " button create", Toast.LENGTH_SHORT).show()
                 dataList[position].checkbox = true

                 if(flag)
                    (ctx as SettingCategoryActivity).goneBtns()

                 if(!init_flag) {
                     (ctx as SettingCategoryActivity).visibleDeleteBtn()
                     init_flag = true
                 }




             } else if (holder.checkbox.isChecked == true) {
                 holder.checkbox.isChecked = false
                 var flag = true
                 dataList[position].checkbox = false
                 for (data in dataList) {
                     if (data.checkbox == true) {
                         flag = false
                         break
                     }
                 }

                 Toast.makeText(ctx, dataList[position].category_name + " button del", Toast.LENGTH_SHORT).show()
                 if(flag) {
                     (ctx as SettingCategoryActivity).goneDeleteBtn()
                     init_flag = false
                 }

             }
         }




        holder.edit_btn.setOnClickListener {
            ctx.startActivity<CategorySettingEditActivity>(
                "pos" to position,
                "category_idx" to dataList[position].category_idx,
                "category_name" to dataList[position].category_name
            )

             /* if (mCallback != null) {
                  mCallback.onHandelSelection(position, dataList[position].category_name)
              }*/

        }

        holder.sort_btn.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mStartDragListener.onStartDrag(holder)
                    Log.e("check", "is it ok?")
                    return@OnTouchListener true
                }

                //MotionEvent.ACTION_UP ->{
                //  (ctx as SettingCategoryActivity).dataChange()
                //}
            }

            return@OnTouchListener true
        })
    }

    override fun onItemMove(fromPos: Int, toPos: Int): Boolean {
        Collections.swap(dataList, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
        notifyItemChanged(fromPos)
        return true
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkbox = itemView.findViewById(R.id.category_cb) as RadioButton
        var category_name = itemView.findViewById(R.id.category_setting_name) as TextView
        var checkbox_btn = itemView.findViewById(R.id.rl_btn) as RelativeLayout
        var edit_btn = itemView.findViewById(R.id.category_setting_edit) as ImageView
        var sort_btn = itemView.findViewById(R.id.category_setting_sort) as ImageView
    }
}