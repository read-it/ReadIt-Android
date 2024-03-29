package com.computer.inu.readit_appjam.Activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.CursorLoader
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.computer.inu.readit_appjam.DB.SharedPreferenceController
import com.computer.inu.readit_appjam.Network.ApplicationController
import com.computer.inu.readit_appjam.Network.NetworkService
import com.computer.inu.readit_appjam.Network.Put.PutMyprofileResponse
import com.computer.inu.readit_appjam.R
import kotlinx.android.synthetic.main.activity_change_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.ctx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ChangeProfileActivity : AppCompatActivity() {

    val REQUEST_CODE_SELECT_IMAGE: Int = 1004
    val My_READ_STORAGE_REQUEST_CODE = 7777

    var imageURI: String? = null
    var tmp: String? = null
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile)

        tmp = intent.getStringExtra("configured_img")
        if (tmp != null) {
            Glide.with(ctx)
                .load(tmp)
                .into(civ_change_profile_pic)
        }
        btn_change_picture_comfiem.setOnClickListener {
            putChangeMyprofileResponse()
            finish()
        }
        civ_change_profile_btn
            .setOnClickListener {
                requestReadExternalStoragePermission()
            }
        iv_changeProfile_back_btn.setOnClickListener {
            finish()
        }
    }

    private fun requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//사용자에게 권한을 왜 허용해야되는지에 메시지를 주기 위한 대한 로직을 추가하려면 이 블락에서 하면됩니다!!
//하지만 우리는 그냥 비워놓습니다!! 딱히 줄말 없으면 비워놔도 무관해요!!! 굳이 뭐 안넣어도됩니다!
            } else {
//아래 코드는 권한을 요청하는 메시지를 띄우는 기능을 합니다! 요청에 대한 결과는 callback으로 onRequestPermissionsResult 메소드에서 받습니다!!!
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    My_READ_STORAGE_REQUEST_CODE
                )
            }
        } else {
//첫번째 if 문의 else 로써, 기존에 이미 권한 메시지를 통해 권한을 허용했다면 아래와 같은 곧바로 앨범을 여는 메소드를 호출 해주면됩니다!!
            showAlbum()
        }
    }

    //외부저장소(앨범과 같은)에 접근 관련 요청에 대해 OK를 했는지 거부했는지를 callback으로 받는 메소드입니다!
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//onActivityResult와 같은 개념입니다. requestCode로 어떤 권한에 대한 callback인지를 체크합니다.
        if (requestCode == My_READ_STORAGE_REQUEST_CODE) {
//결과에 대해 허용을 눌렀는지 체크하는 조건문이구요!
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//이곳은 외부저장소 접근을 허용했을 때에 대한 로직을 쓰시면됩니다. 우리는 앨범을 여는 메소드를 호출해주면되겠죠?
                showAlbum()
            } else {
//이곳은 외부저장소 접근 거부를 했을때에 대한 로직을 넣어주시면 됩니다.
                finish()
            }
        }
    }

    private fun showAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//REQUEST_CODE_SELECT_IMAGE를 통해 앨범에서 보낸 요청에 대한 Callback인지를 체크!!!
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
//undefinedData.data에는 앨범에서 선택한 사진의 Uri가 들어있습니다!! 그러니까 제대로 선택됐는지 null인지 아닌지를 체크!!!
                if (data != null) {
                    val selectedImageUri: Uri = data.data
//Uri를 getRealPathFromURI라는 메소드를 통해 절대 경로를 알아내고, 인스턴스 변수 imageURI에 넣어줍니다!
                    imageURI = getRealPathFromURI(selectedImageUri)
                    Glide.with(this@ChangeProfileActivity)
                        .load(selectedImageUri)
                        .thumbnail(0.1f)
                        .into(civ_change_profile_pic)
                } else {
                    // 기본 이미지 url 등록 or src 이용 (서버에 null값 보내기)
                    /*imageURI = getRealPathFromURI(selectedImageUri)
                    Glide.with(this@ChangeProfileActivity)
                        .load(selectedImageUri)
                        .thumbnail(0.1f)
                        .into(civ_change_profile_pic)*/
                }
            }
        }
    }

    //Uri에 대한 절대 경로를 리턴하는 메소드입니다! 굳이 코드를 해석하려고 하지말고,
    //앱잼때 코드를 복붙을 통해 재사용해주세요!!
    fun getRealPathFromURI(content: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader: CursorLoader = CursorLoader(this, content, proj, null, null, null)
        val cursor: Cursor = loader.loadInBackground()!!
        val column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_idx)
        cursor.close()
        return result
    }

    private fun putChangeMyprofileResponse() {
        val input_nickname = ced_change_profile_nickname.text.toString()
        val token = SharedPreferenceController.getAccessToken(this)
        // 닉네임 변경
        if (input_nickname.isNotEmpty()) {
//Multipart 형식은 String을 RequestBody 타입으로 바꿔줘야 합니다!
            var nickname =
                RequestBody.create(
                    MediaType.parse("text/plain"),
                    ced_change_profile_nickname.text.toString()
                )

//아래 3줄의 코드가 이미지 파일을 서버로 보내기 위해 MultipartBody.Part 형식으로 만드는 로직입니다.
// imageURI는 앨범에서 선택한 이미지에 대한 절대 경로가 담겨있는 인스턴스 변수입니다.

            var profile_img: MultipartBody.Part? = null
            if (imageURI != null) {
                val file: File = File(imageURI)
                val requestfile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                profile_img = MultipartBody.Part.createFormData("profile_img", file.name, requestfile)
            }
            val putChangeMyprofileResponse =
                networkService.ChangeMyProfileResponse(token, profile_img, nickname)
            putChangeMyprofileResponse.enqueue(object : Callback<PutMyprofileResponse> {
                override fun onFailure(call: Call<PutMyprofileResponse>, t: Throwable) {

                    Log.e("write fail", t.toString())
                }

                override fun onResponse(call: Call<PutMyprofileResponse>, response: Response<PutMyprofileResponse>) {
                    if (response.isSuccessful) {

                    }
                }
            })
        }

        // 닉네임 변경 x
        else {
            var profile_img: MultipartBody.Part? = null
            if (imageURI != null) {
                val file: File = File(imageURI)
                val requestfile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                profile_img = MultipartBody.Part.createFormData("profile_img", file.name, requestfile)
            }
            val putChangeMyprofileResponse =
                networkService.ChangeMyProfileResponse(token, profile_img, null)
            putChangeMyprofileResponse.enqueue(object : Callback<PutMyprofileResponse> {
                override fun onFailure(call: Call<PutMyprofileResponse>, t: Throwable) {

                    Log.e("write fail", t.toString())
                }

                override fun onResponse(call: Call<PutMyprofileResponse>, response: Response<PutMyprofileResponse>) {
                    if (response.isSuccessful) {

                    }
                }
            })
        }
    }
}

