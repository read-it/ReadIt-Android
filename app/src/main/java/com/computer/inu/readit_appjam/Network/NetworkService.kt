package com.computer.inu.readit_appjam.Network

import com.computer.inu.readit_appjam.Data.PostSigninResponse
import com.computer.inu.readit_appjam.Data.PostSignupResponse
import com.computer.inu.readit_appjam.Network.Put.PutScrapTrashResponse
import com.computer.inu.readit_appjam.Network.Put.PutSignOutResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

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
    @PUT("/user/signout")
    fun putSignoutResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken")  accesstoken :String
    ): Call<PutSignOutResponse>
}