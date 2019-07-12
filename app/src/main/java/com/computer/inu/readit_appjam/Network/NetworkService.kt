package com.computer.inu.readit_appjam.Network

import com.computer.inu.readit_appjam.Data.CategoryOrderDto
import com.computer.inu.readit_appjam.Data.Contentsidxlist
import com.computer.inu.readit_appjam.Network.Delete.DeleteCategoryResponse
import com.computer.inu.readit_appjam.Network.Delete.DeleteTrashCan
import com.computer.inu.readit_appjam.Network.Get.*
import com.computer.inu.readit_appjam.Network.Post.*
import com.computer.inu.readit_appjam.Network.Put.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("/contents/{contents_idx}")
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

    @PUT("/mypage/editPassword")
    fun putEditPasswordResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Body() body: JsonObject
    ): Call<Put_Edit_Password_Response>

    @GET("/contents/search/{default_idx}/{category_idx}")
    fun getSearchResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("default_idx") default_idx: Int,
        @Path("category_idx") category_idx: Int,
        @Query("keyword") keyword: String
    ): Call<GetSearchResponse>

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

    @PUT("/category/modify/{category_idx}")
    fun putChangeCategoryNameResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("category_idx") category_idx: Int,
        @Body() body: JsonObject
    ): Call<PutCategoryNameChange>

    @GET("/mypage/")
    fun getMypageResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String
    ): Call<GetMyPageResponse>

    @Multipart
    @PUT("/user/setprofile")
    fun ChangeMyProfileResponse(
        @Header("accesstoken") accesstoken: String,
        @Part profile_img: MultipartBody.Part?, // String
        @Part("nickname") nickname: RequestBody? // String
    ): Call<PutMyprofileResponse>


    @PUT("/category/delete/{category_idx}/{delete_flag}")
    fun deleteCategoryResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("category_idx") category_idx: Int,
        @Path("delete_flag") delete_flag: Int,
        @Body() body: JsonObject
    ): Call<DeleteCategoryResponse>

    @PUT("/category/order")
    fun putCategorySortResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Body category_orders : CategoryOrderDto
    ): Call<PutCategorySortResponse>

    @GET("/mypage/highlight/highlightlist")
    fun getMyHilightResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String
    ): Call<GetRealHilightList>

    @PUT("/contents/change/{contents_idx}/{category_idx}")
    fun putChangeCategoryResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("contents_idx") contents_idx: Int,
        @Path("category_idx") category_idx : Int
    ): Call<PutChangeCategoryResponse>

    @PUT("/mypage/pushAlarm/{alarm_flag}")
    fun putPushAlermResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("alarm_flag") alarm_flag: Int
    ): Call<PutPushAlermResponse>

    @PUT("/mypage/setReaditTime/{readittime_flag}")
    fun putReadItTimeResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("readittime_flag") readittime_flag: Int,
        @Body() body: JsonObject
    ): Call<PutReadItTimeResponse>

    @HTTP(method = "DELETE", path = "/mypage/trashcan", hasBody = true)
    fun deleteFavoriteResponse(
        @Header("Content-Type") content_type: String,
        @Header("x-access-accesstoken") accesstoken: String,
        @Body contents_idx_list: Contentsidxlist
    ): Call<DeleteTrashCan>

    @GET("/contents/{contents_idx}")
    fun getContentsReadResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("contents_idx") contents_idx: Int
    ): Call<GetContentsReadResponse>

    @POST("/contents/highlight/add/{contents_idx}")
    fun postHighlightAddResponse(
        @Header("Content-Type") content_type: String,
        @Header("accesstoken") accesstoken: String,
        @Path("contents_idx") contents_idx: Int,
        @Body() body: JsonObject
    ): Call<HilightDataResponse>
}