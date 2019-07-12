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
import kotlinx.android.synthetic.main.activity_hone_nick_name_popup.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class HoneNickNamePopupActivity : AppCompatActivity() {

    val REQUEST_CODE_SELECT_IMAGE: Int = 1004
    val My_READ_STORAGE_REQUEST_CODE = 7777

    var imageURI: String? = null
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hone_nick_name_popup)

        civ_home_nickname_change_profile_pic.setOnClickListener {
            requestReadExternalStoragePermission()
        }

        tv_home_start.setOnClickListener {
            putChangeMyprofileResponse()
        }

        tv_home_nick_next_reject.setOnClickListener {
            startActivity<MainActivity>()
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
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    My_READ_STORAGE_REQUEST_CODE
                )
            }
        } else {
            showAlbum()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == My_READ_STORAGE_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showAlbum()
            } else {
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
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val selectedImageUri: Uri = data.data
                    imageURI = getRealPathFromURI(selectedImageUri)
                    Glide.with(this)
                        .load(selectedImageUri)
                        .thumbnail(0.1f)
                        .into(civ_home_nickname_change_profile_pic)
                }
            }
        }
    }

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
        val input_nickname = ced_profile_nickname1.text.toString()
        val token = SharedPreferenceController.getAccessToken(this)
        // 닉네임 변경
        if (input_nickname.isNotEmpty()) {
//Multipart 형식은 String을 RequestBody 타입으로 바꿔줘야 합니다!
            var nickname =
                RequestBody.create(
                    MediaType.parse("text/plain"),
                    ced_profile_nickname1.text.toString()
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
                        startActivity<MainActivity>()
                        finish()
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
                        startActivity<MainActivity>()
                        finish()
                    }
                }
            })
        }
    }
}
