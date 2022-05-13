package com.example.grocery.retrofit


import com.example.grocery.model.CategoryResponse
import com.example.grocery.model.ItemResponse
import com.example.grocery.model.SubCategoryResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface ApiService {

//    @GET("usersadd")
//    fun getArtist(@Query("term") term: String):retrofit2.Call<SongResponse>

    @POST("addUser")
    @FormUrlEncoded
    fun addUser(@Field("app_version") app_version: String,
                 @Field("device_name") device_name: String,
                 @Field("login_data") login_data: String,
                 @Field("login_type") login_type: String)
            : Call<JsonObject>

//    @POST("usersadd")
//    fun usersadd(@Body jsonObject: JsonObject): Call<JsonObject>

    //http://localhost:8090/groceryApp/getCategory
    @GET("getCategory")
    fun getCategory():retrofit2.Call<CategoryResponse>

    @GET("getsubCategory")
    fun getSubCategory(@Query("categorySeq") categorySeq: Int): Call<SubCategoryResponse>

    @GET("items")
    fun getSubCategorySeq(@Query("subCategorySeq") subCategorySeq:Int):Call<ItemResponse>

}