package com.computer.inu.readit_appjam.Network

import com.computer.inu.readit_appjam.Network.Get.*
import com.computer.inu.readit_appjam.Network.Post.PostCategoryAddResponse
import com.computer.inu.readit_appjam.Network.Post.PostContentsAddResponse
import com.computer.inu.readit_appjam.Network.Post.PostSigninResponse
import com.computer.inu.readit_appjam.Network.Post.PostSignupResponse
import com.computer.inu.readit_appjam.Network.Put.*
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
    ): Call<GetMainStorageResponse>

    @GET("/category")
    fun getCategoryResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String
    ): Call<GetCategoryResponse>

    @PUT("/contents/fix/{contents_idx}")
    fun putMakeFixContentResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("contents_idx") contents_idx: Int
    ): Call<PutMakeFixContentResponse>
    @PUT("/contents/scrap/{contents_idx}")
    fun putContentsScrabtResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("contents_idx") contents_idx: Int
    ): Call<PutContentsScrabResponse>
    @POST("/contents/add")
    fun postContentsAddResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Body() body: JsonObject
    ): Call<PostContentsAddResponse>
    @PUT("/contents/delete/{contents_idx}")
    fun putdeleteResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("contents_idx") contents_idx: Int
    ): Call<PutDeleteContentResponse>
    @PUT("/contents/{contents_idx}")
    fun putReadContentsResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("contents_idx") contents_idx: Int
    ): Call<PutReadContents>

    @PUT("/user/signout")
    fun putSignoutResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String
    ): Call<PutSignOutResponse>

    @PUT("/category/modify/{category_idx}")
    fun putCategoryNameResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("category_idx") category_idx: Int,
        @Body() body: JsonObject
    ): Call<PutCategoryNameResponse>

    @POST("/category")
    fun postCategoryAddResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Body() body: JsonObject
    ): Call<PostCategoryAddResponse>

    @GET("/category/unclassified")
    fun getUndefinedResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String
    ): Call<GetUndefinedResponse>

    @GET("/mypage/scrap/scraplist")
    fun getMypageScrapResponse( //해야함
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String
    ): Call<GetMypageScrapList>

    @GET("/storage/{category_idx}/{sort}")
    fun getSortCategoryResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("category_idx") category_idx: Int,
        @Path("sort") sort: Int
    ): Call<GetSortCategoryResponse>

    @GET("/mypage/trashcan")
    fun getTrashCanResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String
    ): Call<GetTrashCantResponse>
}