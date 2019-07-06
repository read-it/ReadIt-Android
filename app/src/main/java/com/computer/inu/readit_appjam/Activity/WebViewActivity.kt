package com.computer.inu.readit_appjam.Activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.view.*
import android.webkit.*
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.computer.inu.readit_appjam.Interface.WebViewJavaScriptInterface
import com.computer.inu.readit_appjam.Interface.copy
import com.computer.inu.readit_appjam.Interface.highlight
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.PutScrapTrashResponse
import com.computer.inu.readit_appjam.R
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_web_view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebViewActivity : AppCompatActivity(), WebViewJavaScriptInterface {
    var js_i = 0
    companion object {
        var handler : Handler? = Handler()
        private var myClipboard: ClipboardManager? = null
        private var myClip: ClipData? = null
    }

    val networkService: NetworkService by lazy{
        ApplicationController.instance.networkService
    }

    /*
    private var actionMode : ActionMode? = null
        private fun mainInterface() : Boolean {
            if (actionMode == null) actionMode = startActionMode(ActionModeCallBack())
            else actionMode?.finish()
            return false
        }
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val intent = getIntent()
        var shareText = "Readit에서 링크를 공유합니다!\n"
        var link = intent.getStringExtra("url")

        //val contents_idx = 1

        //버튼 초기화
        wv_scrap.setOnClickListener {
            Toast.makeText(this, "scrap request to server", Toast.LENGTH_SHORT).show()
            //putScrapTrashResponse(contents_idx)
        }

        wv_trash.setOnClickListener {
            Toast.makeText(this, "deletion request to server", Toast.LENGTH_SHORT).show()
            //putScrapTrashResponse(contents_idx)
        }

        wv_share.setOnClickListener {
            val intent = Intent(android.content.Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_SUBJECT, shareText)
            intent.putExtra(Intent.EXTRA_TEXT, link)
            val chooser = Intent.createChooser(intent, "공유하기")
            startActivity(chooser)

        }


        //클립보드 초기화
        myClipboard  = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        //자바스크립트 연동
        val wv = findViewById<View>(R.id.wv_main) as WebView
        wv.settings.javaScriptEnabled = true
        wv.webViewClient = WebViewClient()
        wv.webChromeClient = object: WebChromeClient(){
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        }

        wv.addJavascriptInterface(AndroidBridge(), "android") //자바스크립트 --> 안드로이드 연동
        wv.loadUrl(link)


    }

    //웹뷰 글 클릭 시 이벤트
    override fun onActionModeStarted(mode: android.view.ActionMode) {
        mode.menu.clear()
        var menus: Menu = mode.menu
        mode.menuInflater.inflate(R.menu.custom_menu, menus)
        var highlight: String = highlight()
        wv_main.loadUrl(highlight)
        menus.findItem(R.id.menu_color1).setOnMenuItemClickListener {
            var color_flag = 1
            wv_main.loadUrl("javascript:highlightSelection('$color_flag')")
            return@setOnMenuItemClickListener true
        }

        menus.findItem(R.id.menu_color2).setOnMenuItemClickListener {
            var color_flag = 2
            wv_main.loadUrl("javascript:highlightSelection('$color_flag')")
            return@setOnMenuItemClickListener true
        }

        menus.findItem(R.id.menu_delete).setOnMenuItemClickListener {
            var color_flag = 3
            wv_main.loadUrl("javascript:highlightSelection('$color_flag')")
            return@setOnMenuItemClickListener true
        }

        menus.findItem(R.id.ContextualActionMode_copy).setOnMenuItemClickListener {
            var copy : String = copy()
            Log.d("check", copy)
            wv_main.loadUrl(copy)
            wv_main.loadUrl("javascript:copySelection()")
            Toast.makeText(this, "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
            mode.finish()
            return@setOnMenuItemClickListener true
        }


        super.onActionModeStarted(mode)
    }

    override fun onActionModeFinished(mode: ActionMode?) {
        super.onActionModeFinished(mode)
    }

    //자바스크립트 -> 안드로이드 통신 브릿지
    class AndroidBridge{
        @JavascriptInterface
        fun setMessage(arg : String){
            handler?.post {
                //클립보드 복사
                myClip = ClipData.newPlainText("text", arg)
                myClipboard?.setPrimaryClip(myClip)
            }
        }
    }


    //스크랩/휴지통 서버 통신
    fun putScrapTrashResponse(contents_idx : Int){
        var jsonObject = JSONObject()
        jsonObject.put("contents_idx", contents_idx)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val putScrapTrashResponse: Call<PutScrapTrashResponse> =
            networkService.putScrapTrashResponse("application/json", gsonObject)
        putScrapTrashResponse.enqueue(object: Callback<PutScrapTrashResponse>{
            override fun onFailure(call: Call<PutScrapTrashResponse>, t: Throwable) {
                Log.e("scrap_trash failed", t.toString())
            }

            override fun onResponse(call: Call<PutScrapTrashResponse>, response: Response<PutScrapTrashResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.status == 200){
                        Toast.makeText(this@WebViewActivity, "스크랩 성공", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

    }
/*
    inner class ActionModeCallBack : ActionMode.Callback{
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            Log.e("check", "come?")
            val inflater = mode?.getMenuInflater()
            inflater?.inflate(R.menu.custom_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when(item?.getItemId()){
                R.id.ContextualActionMode_pen -> {
                    var highlight : String = highlight()
                    Log.d("check", highlight)
                    wvtest.loadUrl(highlight)
                    wvtest.loadUrl("javascript:highlightSelection()")
                }
                R.id.ContextualActionMode_copy -> {
                    var temp : String = highlight()
                    wvtest.loadUrl(temp)
                    wvtest.loadUrl("javascript:show_alert()")
                }
            }
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }


    }
*/
}
