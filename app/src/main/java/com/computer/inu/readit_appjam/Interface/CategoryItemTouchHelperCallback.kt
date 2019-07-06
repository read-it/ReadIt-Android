package com.computer.inu.readit_appjam.Interface

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class CategoryItemTouchHelperCallback(listener: OnItemMoveListener) : ItemTouchHelper.Callback() {

    interface OnItemMoveListener {
        fun onItemMove(fromPos: Int, toPos: Int): Boolean
    }

    private var mItemMoveListener: OnItemMoveListener = listener

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags: Int = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mItemMoveListener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        return
    }

}