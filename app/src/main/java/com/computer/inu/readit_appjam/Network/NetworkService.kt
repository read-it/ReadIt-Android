package com.computer.inu.readit_appjam.Network

import com.computer.inu.readit_appjam.Network.Get.GetMainStorage
import com.computer.inu.readit_appjam.Network.Post.PostSigninResponse
import com.computer.inu.readit_appjam.Network.Post.PostSignupResponse
import com.computer.inu.readit_appjam.Network.Put.PutScrapTrashResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    /*    @GET("/signup/email")
        fun getEmailRedundancyResponse(
            @Query("email") email: String
        ): Call<GetEmailRedundancyResponse>*/

    @PUT("/api/contents/scrap/:contents_idx")
    fun putScrapTrashResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PutScrapTrashResponse>

    @POST("/user/signup")
    fun postSignupResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostSignupResponse>

    @POST("/user/signin")
    fun postSigninResponse(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Call<PostSigninResponse>

    @GET("/storage/main")
    fun getMainStorageResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String
    ): Call<GetMainStorage>
}