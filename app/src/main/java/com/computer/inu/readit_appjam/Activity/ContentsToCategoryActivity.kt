package com.computer.inu.readit_appjam.Activity

import android.arch.core.util.Function
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.computer.inu.readit_appjam.Adapter.ContentsRecyclerViewAdapter
import com.computer.inu.readit_appjam.Adapter.UndefinedRecyclerViewAdapter
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.Get.GetUndefinedResponse
import com.computer.inu.readit_appjam.Network.Get.UndefinedContent
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Post.PostCategoryAddResponse
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_contents_to_category.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentsToCategoryActivity : AppCompatActivity() {
    lateinit var undefinedRecyclerViewAdapter: UndefinedRecyclerViewAdapter
    private val MAXIMUM_SELECTION = 5
    private lateinit var selectionTracker: SelectionTracker<Long>

    var data = ArrayList<UndefinedContent>()
    lateinit var category_name : String
    lateinit var contents_idx : ArrayList<Int>

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    private val itemDetailsLookup = object : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = rv_contents_to_category.findChildViewUnder(e.x, e.y)
            if (view != null) {
                val holder = rv_contents_to_category.getChildViewHolder(view)
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
        setContentView(R.layout.activity_contents_to_category)
        getUndefinedContents()

        val intent = getIntent()
        category_name = intent.getStringExtra("name")


        iv_add_category_complete.setOnClickListener {
            postCategoryAddResponse()
        }
    }

   override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        selectionTracker.onSaveInstanceState(outState!!)
    }

    private fun getUndefinedContents() {
        val getUndefinedResponse: Call<GetUndefinedResponse> = networkService.getUndefinedResponse(
            "application/json",
            SharedPreferenceController.getAccessToken(this)
        )
        getUndefinedResponse.enqueue(object : Callback<GetUndefinedResponse> {
            override fun onFailure(call: Call<GetUndefinedResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<GetUndefinedResponse>, response: Response<GetUndefinedResponse>) {
                if (response.isSuccessful) {
                }
                  data = response.body()!!.data!!.contents_list
                    undefinedRecyclerViewAdapter = UndefinedRecyclerViewAdapter(this@ContentsToCategoryActivity, data)
                    undefinedRecyclerViewAdapter.notifyDataSetChanged()
                    rv_contents_to_category.adapter = undefinedRecyclerViewAdapter
                    rv_contents_to_category.layoutManager = LinearLayoutManager(this@ContentsToCategoryActivity)

                    undefinedRecyclerViewAdapter.apply {
                        selectionFun = Function { key ->
                            selectionTracker.isSelected(key)
                        }
                    }
                    selectionTracker = SelectionTracker.Builder(
                        "selection-demo",
                        rv_contents_to_category,
                        StableIdKeyProvider(rv_contents_to_category),
                        itemDetailsLookup,
                        StorageStrategy.createLongStorage()
                    )
                        .withSelectionPredicate(selectionPredicate)
                        .build()

                }
        })

    }

    private fun postCategoryAddResponse() {
        val tmp_array = ArrayList<Int>()
        var jsonObject = JSONObject()
        jsonObject.put("category_name", category_name)
        jsonObject.put("contents_idx", tmp_array)
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postCategoryAddResponse: Call<PostCategoryAddResponse> =
            networkService.postCategoryAddResponse("application/json",SharedPreferenceController.getAccessToken(this),  gsonObject)
        postCategoryAddResponse.enqueue(object : Callback<PostCategoryAddResponse> {
            override fun onFailure(call: Call<PostCategoryAddResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<PostCategoryAddResponse>, response: Response<PostCategoryAddResponse>) {
                if (response.isSuccessful) {
                    toast(response.body()!!.message)
                    finish()
                }
            }
        })

    }
}
