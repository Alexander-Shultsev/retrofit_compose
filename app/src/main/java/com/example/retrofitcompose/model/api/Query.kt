package com.example.retrofitcompose.model.api

import android.util.Log
import com.example.retrofitcompose.model.api.data.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

interface Query {
//    @Headers("Content-Type: application/json")

    @GET("posts")
    suspend fun getPosts() : Response<ArrayList<Post>>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: String) : Response<Post>

    @GET("photos")
    suspend fun getPhotos() : Response<ArrayList<Photo>>

    @GET("users")
    suspend fun getUsers() : Response<ArrayList<Users>>

    @POST("posts")
    suspend fun postPost(
        @Body body: Post2
    ) : Response<Post>

    @FormUrlEncoded
    @PUT("posts/{id}")
    suspend fun putPost(
        @Path("id") id: String,
        @Field("userId") userId: String,
        @Field("title") title: String,
        @Field("body") body: String
    ) : Response<Post>

    @FormUrlEncoded
    @PATCH("posts/{id}")
    suspend fun patchPost(
        @Path("id") id: String,
        @Field("title") title: String,
    ) : Response<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(
        @Path("id") id: String,
    ) : Response<ResponseBody>

}