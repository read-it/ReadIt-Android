package com.computer.inu.readit_appjam.Activity

import android.arch.core.util.Function
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.computer.inu.readit_appjam.Adapter.ContentsRecyclerViewAdapter
import com.computer.inu.readit_appjam.Adapter.TrashCanRecyclerViewAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.DataXXXX
import com.computer.inu.readit_appjam.Network.Get.GetTrashCantResponse
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_trash_can.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrashCanActivity : AppCompatActivity() {
    lateinit var trashCanRecyclerViewAdapter: TrashCanRecyclerViewAdapter
    private val MAXIMUM_SELECTION = 5
    private lateinit var selectionTracker: SelectionTracker<Long>
    lateinit var selectionFun: android.arch.core.util.Function<Long, Boolean>
    var data = ArrayList<DataXXXX>()
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    private val itemDetailsLookup = object : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = rl_trashcan.findChildViewUnder(e.x, e.y)
            if (view != null) {
                val holder = rl_trashcan.getChildViewHolder(view)
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
                toast("최대 선택 갯수 입니다.")
                false
            } else {
                true
            }
        }

        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
            return true
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trash_can)
        getTrashCanPost()
        iv_mypage_trashcan_back_btn.setOnClickListener {
            finish()
        }
    }

    private fun getTrashCanPost() {
        val getTrashCanPostResponse: Call<GetTrashCantResponse> = networkService.getTrashCanResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(this)
        )
        getTrashCanPostResponse.enqueue(object : Callback<GetTrashCantResponse> {
            override fun onFailure(call: Call<GetTrashCantResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetTrashCantResponse>, response: Response<GetTrashCantResponse>) {
                if (response.isSuccessful) {

                    data.clear()
                    data = response.body()!!.data!!
                    if (data.isEmpty()) {
                        rl_empty_trashcan_nocontents.visibility = View.VISIBLE
                    } else
                        rl_empty_trashcan_nocontents.visibility = View.GONE
                    trashCanRecyclerViewAdapter = TrashCanRecyclerViewAdapter(this@TrashCanActivity, data)
                    trashCanRecyclerViewAdapter.notifyDataSetChanged()
                    rl_trashcan.adapter = trashCanRecyclerViewAdapter
                    rl_trashcan.layoutManager = LinearLayoutManager(this@TrashCanActivity)
                    trashCanRecyclerViewAdapter.apply {
                        selectionFun = Function { key ->
                            selectionTracker.isSelected(key)
                        }
                    }
                    selectionTracker = SelectionTracker.Builder(
                        "selection-demo",
                        rl_trashcan,
                        StableIdKeyProvider(rl_trashcan),
                        itemDetailsLookup,
                        StorageStrategy.createLongStorage()
                    )
                        .withSelectionPredicate(selectionPredicate)
                        .build()
                }
            }
        })

    }
}
